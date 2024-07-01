package uk.danbrown.apprenticeshipchineserestaurantbackend.utils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.AppenderBase;
import org.assertj.core.api.MapAssert;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class LoggerAssert implements BeforeEachCallback {

    private static final Logger ROOT = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    private static final ConcurrentHashMap<String, Collection<ILoggingEvent>> LOGS = new ConcurrentHashMap<>();

    private static final Appender<ILoggingEvent> APPENDER = new AppenderBase<>() {
        @Override
        protected void append(ILoggingEvent eventObject) {
            LOGS.putIfAbsent(eventObject.getThreadName(), new ConcurrentLinkedQueue<>());
            LOGS.get(eventObject.getThreadName()).add(eventObject);
        }

        @Override
        public void stop() {
            super.stop();
            this.start();
        }
    };

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        APPENDER.start();
        LOGS.put(Thread.currentThread().getName(), new ConcurrentLinkedQueue<>());
        ROOT.addAppender(APPENDER);
    }

    public LoggerAssert assertNoLogs() {
        List<String> logs = getAllLogs().map(ILoggingEvent::getFormattedMessage).toList();
        if (!logs.isEmpty()) {
            throw errorForUnexpectedLogs(logs);
        }
        return this;
    }

    public LoggerAssert assertNoLogsContaining(String content) {
        List<String> logsContainingContent = getAllLogs()
                .map(ILoggingEvent::getFormattedMessage)
                .filter(l -> l.contains(content))
                .toList();
        if (!logsContainingContent.isEmpty()) {
            throw errorForUnexpectedLogs(logsContainingContent);
        }
        return this;
    }

    private AssertionError errorForUnexpectedLogs(List<String> unexpectedLogs) {
        String firstLine = "expected no logs";
        String restOfLines = "found: " + String.join("\n       ", unexpectedLogs);
        String msg = "\n" + firstLine + "\n" + restOfLines + "\n";
        throw new AssertionError(msg);
    }

    public LoggerAssert assertInfo(String content) {
        throwIfNoLogsWith(Level.INFO, content);
        return this;
    }

    public LoggerAssert assertWarn(String content) {
        throwIfNoLogsWith(Level.WARN, content);
        return this;
    }

    public LoggerAssert assertError(String content) {
        throwIfNoLogsWith(Level.ERROR, content);
        return this;
    }

    public LoggerAssert assertError(String content, Throwable throwable) {
        assertThatExceptionFrom(content).hasMessage(throwable.getMessage()).isInstanceOf(throwable.getClass());
        return this;
    }

    public LoggerAssert assertError(String content, Class<? extends Throwable> throwableType) {
        assertThatExceptionFrom(content).isInstanceOf(throwableType);
        return this;
    }

    public ThrowableAssert<Throwable> assertThatExceptionFrom(String content) {
        List<ILoggingEvent> logs = getAllLogs().toList();

        List<ILoggingEvent> filteredLogs = logs.stream()
                .filter(log -> log.getLevel() == Level.ERROR && log.getFormattedMessage().equals(content)).toList();

        if (filteredLogs.isEmpty()) {
            List<String> messages = logs.stream().map(ILoggingEvent::getFormattedMessage).toList();
            throw new AssertionError(getAssertionErrorMessage(content, messages));
        }

        ThrowableProxy throwableProxy = (ThrowableProxy) filteredLogs.get(0).getThrowableProxy();
        return new ThrowableAssert<>(throwableProxy.getThrowable());
    }

    public MapAssert<String, String> assertMdc() {
        Map<String, String> allMdc = getThreadLogs()
                .stream()
                .map(ILoggingEvent::getMDCPropertyMap)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return assertThat(allMdc);
    }

    private void throwIfNoLogsWith(Level level, String content) {
        List<String> logsMessagesMatchingLevel = getAllLogs()
                .filter(log -> log.getLevel() == level)
                .map(ILoggingEvent::getFormattedMessage)
                .toList();
        if (logsMessagesMatchingLevel.stream().noneMatch(logMessage -> logMessage.equals(content))) {
            throw new AssertionError(getAssertionErrorMessage(content, logsMessagesMatchingLevel));
        }
    }

    private Collection<ILoggingEvent> getThreadLogs() {
        return LOGS.get(Thread.currentThread().getName());
    }

    private Stream<ILoggingEvent> getAllLogs() {
        return LOGS
                .values()
                .stream()
                .flatMap(Collection::stream);
    }

    private static String getAssertionErrorMessage(String expected, List<String> foundLogs) {
        String firstLine = "expected: " + expected;
        String restOfLines = "found:    " + String.join("\n          ", foundLogs);
        return "\n" + firstLine + "\n" + restOfLines + "\n";
    }
}
