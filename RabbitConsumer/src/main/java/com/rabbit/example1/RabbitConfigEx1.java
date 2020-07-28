package com.rabbit.example1;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfigEx1 {

    public static final String DIRECT_EXCHANGE = "direct.exchange";

    public static final String DIRECT_ROUTING_KEY1 = "direct.routing.key1";
    public static final String DIRECT_QUEUE1 = "direct.queue.1";

    public static final String DIRECT_ROUTING_KEY2 = "direct.routing.key2";
    public static final String DIRECT_QUEUE2 = "direct.queue2";

    @Bean
    public Exchange directExchange() {
        return ExchangeBuilder.directExchange(DIRECT_EXCHANGE).build();
    }

    @Bean
    public Queue directQueue1() {
        return QueueBuilder.durable(DIRECT_QUEUE1).build();
    }

    @Bean
    public Binding directBinding1() {
        return BindingBuilder.bind(directQueue1()).to(directExchange())
                .with(DIRECT_ROUTING_KEY1).noargs();
    }

}
