package uk.danbrown.apprenticeshipchineserestaurantbackend.repository;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import uk.co.autotrader.generated.tables.Messages;
import uk.co.autotrader.generated.tables.pojos.MessagesEntity;
import uk.danbrown.apprenticeshipchineserestaurantbackend.context.RequestContextManager;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Message;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static uk.co.autotrader.generated.tables.Messages.MESSAGES;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Message.Builder.aMessage;

@Repository
public class MessageRepository {

    private final DSLContext db;
    private final RequestContextManager requestContextManager;
    private final Clock clock;

    public MessageRepository(DSLContext db, RequestContextManager requestContextManager, Clock clock) {
        this.db = db;
        this.requestContextManager = requestContextManager;
        this.clock = clock;
    }

    public Message insertMessage(Message message) throws FailureInsertingEntityException {
        Optional<MessagesEntity> insertedMessage =
                db.insertInto(MESSAGES)
                        .set(MESSAGES.ORGANIZATION_ID, requestContextManager.getRequestContext().currentId())
                        .set(MESSAGES.NAME, message.name())
                        .set(MESSAGES.CONTENT, message.content())
                        .set(MESSAGES.EMAIL, message.email())
                        .set(MESSAGES.PHONE_NUMBER, message.phoneNumber())
                        .set(MESSAGES.TIME, LocalDateTime.now(clock))
                        .returningResult()
                        .fetchOptionalInto(MessagesEntity.class);
        return insertedMessage.map(this::mapMessageEntityToMessage)
                .orElseThrow(() -> new FailureInsertingEntityException(message));
    }

    public List<Message> getMessages() {
        List<MessagesEntity> messagesEntities =
                db.selectFrom(MESSAGES)
                        .where(MESSAGES.ORGANIZATION_ID.eq(requestContextManager.getRequestContext().currentId()))
                        .fetchInto(MessagesEntity.class);
        return messagesEntities.stream().map(this::mapMessageEntityToMessage).toList();
    }

    private Message mapMessageEntityToMessage(MessagesEntity messagesEntity) {
        return aMessage()
                .withName(messagesEntity.getName())
                .withContent(messagesEntity.getContent())
                .withEmail(messagesEntity.getEmail())
                .withPhoneNumber(messagesEntity.getPhoneNumber())
                .withTime(messagesEntity.getTime())
                .build();
    }
}
