package com.rabbit.example1;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import static com.rabbit.example1.RabbitConfigEx1.*;

@Component
public class RabbitListenerEx1 {

    @RabbitListener(queues = {DIRECT_QUEUE1})
    public void directMessageListener1(String message) {
        System.out.println("Direct message for queue " + DIRECT_QUEUE1 + " received: " + message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = DIRECT_QUEUE2),
            exchange = @Exchange(value = DIRECT_EXCHANGE, type = ExchangeTypes.DIRECT),
            key = DIRECT_ROUTING_KEY2))
    public void directMessageListener2(Message message) {
        System.out.println("Direct message for queue " + DIRECT_QUEUE2 + " received: " + message);
    }

}
