package com.rabbit.example2;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.rabbit.example2.RabbitConfigEx2.*;

@Component
public class RabbitListenerEx2 {

    @RabbitListener(queues = {FANOUT_QUEUE1})
    public void fanoutMessageListener1(String message) {
        System.out.println("Fanout message for queue " + FANOUT_QUEUE1 + " received: " + message);
    }

    @RabbitListener(queues = {FANOUT_QUEUE2})
    public void fanoutMessageListener2(Message message) {
        System.out.println("Fanout message for queue " + FANOUT_QUEUE1 + " received: " + message);
    }

}
