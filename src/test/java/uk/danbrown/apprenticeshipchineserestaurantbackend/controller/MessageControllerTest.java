package uk.danbrown.apprenticeshipchineserestaurantbackend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Message;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.MessageService;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageControllerTest {


    @Mock
    private MessageService messageService;

    private MessageController messageController;

    @BeforeEach
    void setUp() {
        messageController = new MessageController(messageService);
    }

    @Test
    void getMessages_shouldReturnMessages() {
        Message message1 = new Message("subject", "email", "phone", "content", LocalDateTime.MAX);
        Message message2 = new Message("subject2", "email2", "phone2", "content2", LocalDateTime.MAX);

        when(messageService.getMessages()).thenReturn(List.of(message1, message2));

        ResponseEntity<List<Message>> result = messageController.getMessages();

        verify(messageService).getMessages();
        assertThat(result).isEqualTo(ResponseEntity.ok(List.of(message2, message1)));
    }

    @Test
    void insertMessage() throws FailureInsertingEntityException {
        Message message = new Message("subject", "email", "phone", "content", LocalDateTime.MAX);
        when(messageService.insertMessage(message)).thenReturn(message);

        ResponseEntity<Message> result = messageController.insertMessage(message);

        verify(messageService).insertMessage(message);
        assertThat(result).isEqualTo(ResponseEntity.status(201).body(message));
    }
}