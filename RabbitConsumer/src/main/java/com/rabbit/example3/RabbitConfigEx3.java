package com.rabbit.example3;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfigEx3 {

    public static final String TOPIC_EXCHANGE = "topic.exchange";

    public static final String TOPIC_QUEUE1 = "topic.queue1";
    public static final String TOPIC_QUEUE2 = "topic.queue2";

    public static final String TOPIC_ROUTING_KEY1 = "topic.routing.key.#"; // topic.routing.key. fit to pattern
    public static final String TOPIC_ROUTING_KEY2 = "topic.routing.key.special.#";

    @Bean
    public TopicExchange topicExchange() {
        return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE).build();
    }

    @Bean
    public Queue topicQueue1() {
        return new Queue(TOPIC_QUEUE1);
    }

    @Bean
    public Queue topicQueue2() {
        return new Queue(TOPIC_QUEUE2);
    }

    @Bean
    public Binding topicBinding1() {
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with(TOPIC_ROUTING_KEY1);
    }

    @Bean
    public Binding topicBinding2() {
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with(TOPIC_ROUTING_KEY2);
    }
}
