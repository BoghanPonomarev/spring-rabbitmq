package com.rabbit.example7;

import com.rabbit.web.dto.MessageDtoSerializable;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitListenerEx7 {

    /**
     * Config from prev example is needed
     */

    @RabbitListener(containerFactory = "jsonRabbitListenerContainerFactory",
            bindings = @QueueBinding(
            value = @Queue(value = "dto.rpc.queue"),
            exchange = @Exchange(value = "dto.rpc.exchange"),
            key = "dto.rpc.routing.key"))
    public MessageDtoJson messageListenerWithDto(MessageDtoJson messagesDto) {
        System.out.println("Rpc json dto messages received " + messagesDto);
        return new MessageDtoJson("Response value", 33);
    }

    @RabbitListener(returnExceptions = "true",
            // This param try to wrap exception and throw it to client, but some conf on client side req
            bindings = @QueueBinding(
                    value = @Queue(value = "dto.rpc.queue.error"),
                    exchange = @Exchange(value = "dto.rpc.exchange.error"),
                    key = "dto.rpc.routing.key.error"))
    public MessageDtoSerializable messageListenerWithDtoException(MessageDtoSerializable messagesDto) {
        System.out.println("Soon letter will be broken " + messagesDto);
        throw new UnsupportedOperationException("System is expected broken");
    }

}
