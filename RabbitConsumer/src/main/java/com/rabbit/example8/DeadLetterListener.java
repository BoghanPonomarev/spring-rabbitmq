package com.rabbit.example8;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
public class DeadLetterListener {

    /**
     * We can throw AmqpRejectAndDontRequeueException - it will immediately put message to dlq, without replying
     * Add
     *                          factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
     *                          factory.setDefaultRequeueRejected(false);
     * to RabbitConfigEx7, check https://stackoverflow.com/questions/36209605/java-spring-rabbit-gracefully-reject-a-message
     */
    @RabbitListener(containerFactory = "jsonRabbitListenerContainerFactory",
            bindings = @QueueBinding(
                    value = @Queue(value = "throwing.ex.queue", durable = "true",
                            arguments = {
                                    @Argument(name = "x-dead-letter-exchange", value = "dead.letter.exchange"),
                                    @Argument(name = "x-dead-letter-routing-key", value = "dead.letter.routing.key")
                            }
                    ),
                    exchange = @Exchange(value = "throwing.ex.exchange"),
                    key = "throwing.ex.routing.key"
            ))
    public Message throwingExceptionListener(Message message) throws Exception {
        System.out.println("Soon letter will be broken " + message);
        throw new Exception("System is expected broken");
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "dead.letter.queue", durable = "true"),
                    exchange = @Exchange(value = "dead.letter.exchange"),
                    key = "dead.letter.routing.key"))
    public void deadLetterListener(Message message) {
        System.out.println("Dead letter received " + message);
    }

}
