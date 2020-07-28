package com.rabbit.example3;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.rabbit.example3.RabbitConfigEx3.TOPIC_QUEUE1;
import static com.rabbit.example3.RabbitConfigEx3.TOPIC_QUEUE2;

@Component
public class RabbitListenerEx3 {

    @RabbitListener(queues = {TOPIC_QUEUE1})
    public void fanoutMessageListener1(String message) {
        System.out.println("Topic message for queue " + TOPIC_QUEUE1 + " received: " + message);
    }

    @RabbitListener(queues = {TOPIC_QUEUE2})
    public void fanoutMessageListener2(Message message) {
        System.out.println("Topic message for special queue " + TOPIC_QUEUE2 + " received: " + message);
    }
}
