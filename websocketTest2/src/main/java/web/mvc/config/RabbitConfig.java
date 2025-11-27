package web.mvc.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String QUEUE = "chat.queue";
    public static final String EXCHANGE = "chat.exchange";
    public static final String ROUTING_KEY = "chat.routing";



    @Bean
    public FanoutExchange chatExchange() {
        return new FanoutExchange("chat.exchange");
    }

    @Bean
    public Queue chatQueue() {
        return new Queue("chat.queue");
    }

    @Bean
    public Binding chatBinding(FanoutExchange chatExchange, Queue chatQueue) {
        return BindingBuilder.bind(chatQueue).to(chatExchange);
    }
}
