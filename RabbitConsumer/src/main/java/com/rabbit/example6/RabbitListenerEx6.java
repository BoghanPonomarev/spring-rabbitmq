package com.rabbit.example6;

import com.rabbit.example7.MessageDtoJson;
import com.rabbit.web.dto.MessageDtoSerializable;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class RabbitListenerEx6 {

    /**
     * MessageDtoSerializable should be in same package that MessageDtoSerializable
     * in Producer because of package if first variant, so check com.rabbit.web.dto.MessageDtoSerializable
     */

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "dto.queue1"),
            exchange = @Exchange(value = "dto.exchange1"),
            key = "dto.routing.key1"))
    public void messageListenerWithDto(MessageDtoSerializable messagesDto) {
        System.out.println("Dto messages received " + messagesDto);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "dto.queue2"),
            exchange = @Exchange(value = "dto.exchange2"),
            key = "dto.routing.key2"))
    public void messageListenerWithDto(MessageDtoJson messagesDto) {
        System.out.println("Dto messages received " + messagesDto);
    }

}
