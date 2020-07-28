package com.rabbit.example4;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
public class RabbitListenerEx4 {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "headers.queue1"),
            exchange = @Exchange(value = "headers.exchange", type = ExchangeTypes.HEADERS),
            arguments = {
                    @Argument(name = "x-match", value = "all"),
                    @Argument(name = "header-key", value = "header-value1"),
            })
    )
    public void headerMessageListener1(String message) {
        System.out.println("Header message for queue 1 received: " + message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "headers.queue2"),
            exchange = @Exchange(value = "headers.exchange", type = ExchangeTypes.HEADERS),
            arguments = {
                    @Argument(name = "x-match", value = "all"),
                    @Argument(name = "header-key", value = "header-value2"),
            })
    )
    public void headerMessageListener2(String message) {
        System.out.println("Header message for queue 2 received: " + message);
    }

}
