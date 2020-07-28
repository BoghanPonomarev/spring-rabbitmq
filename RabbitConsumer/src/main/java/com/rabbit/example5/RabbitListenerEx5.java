package com.rabbit.example5;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RabbitListenerEx5 {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "batch.queue"),
            exchange = @Exchange(value = "batch.exchange"),
            key = "batch.routing.key"))
    public void batchMessageListener(List<Message> messages) {
        System.out.println("Batch messages count = " + messages.size());
        for(int i=0;i<messages.size();i++) {
            System.out.println("Batch message " + i + " for queue received: " + messages.get(i));
        }
    }

}
