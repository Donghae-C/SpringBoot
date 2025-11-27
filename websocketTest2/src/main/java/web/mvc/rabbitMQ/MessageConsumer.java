package web.mvc.rabbitMQ;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import web.mvc.dto.ChatMessage;
import web.mvc.handler.MyHandler;

@Component
@RequiredArgsConstructor
public class MessageConsumer {
    private final MyHandler myHandler;

    @RabbitListener(queues = "chat.queue")
    public void receive(ChatMessage message) throws Exception {
        myHandler.broadcastToRoom(message);
    }
}
