package web.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import web.mvc.dto.ChatMessage;

@Service
@RequiredArgsConstructor
public class MessagePublisher {
    private final RabbitTemplate rabbitTemplate;

    public void publish(ChatMessage message){
        rabbitTemplate.convertAndSend("chat.exchange","", message);
    }
}
