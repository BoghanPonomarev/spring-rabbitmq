package com.rabbit.web;


import com.rabbit.web.dto.MessageDtoJson;
import com.rabbit.web.dto.MessageDtoSerializable;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.BatchingRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ComplexRabbitController {

    @Autowired
    @Qualifier("rabbitTemplate")
    private RabbitTemplate rabbitTemplate;

    @Autowired
    @Qualifier("jsonRabbitTemplate")
    private RabbitTemplate jsonRabbitTemplate;

    @Autowired
    private BatchingRabbitTemplate batchingRabbitTemplate;

    @PostMapping(value = "/batch") // add RabbitConfig#batchingRabbitTemplate() method
    public ResponseEntity<String> postDirectMessage() {
        for (int i = 0; i < 30; i++) {
            batchingRabbitTemplate
                    .convertAndSend("batch.exchange", "batch.routing.key", "Batch message to queue " + i);
        }
        batchingRabbitTemplate.flush();
        return ResponseEntity.ok("OK");
    }

    @PostMapping(value = "/dto/serializable")
    public ResponseEntity<String> postDirectMessageWithDtoSerializable() {
        rabbitTemplate.convertAndSend("dto.exchange1", "dto.routing.key1", new MessageDtoSerializable("string val", 23));
        return ResponseEntity.ok("OK");
    }

    @PostMapping(value = "/dto/json")
    // Add RabbitConfig#producerJackson2MessageConverter() , RabbitConfig#jsonRabbitTemplate() methods
    public ResponseEntity<String> postDirectMessageWithDtoJson() {
        jsonRabbitTemplate.convertAndSend("dto.exchange2", "dto.routing.key2", new MessageDtoJson("string val", 23));
        return ResponseEntity.ok("OK");
    }

    /**
     * Spring created replyTo queue by itself, it`s called 'direct reply-to' mechanism
     */
    @PostMapping(value = "/rpc/json")
    // Add RabbitConfig#producerJackson2MessageConverter() , RabbitConfig#jsonRabbitTemplate methods
    public ResponseEntity<MessageDtoJson> postDirectRPCMessageWithDtoJson() {
        MessageDtoJson response = jsonRabbitTemplate.convertSendAndReceiveAsType("dto.rpc.exchange", "dto.rpc.routing.key",
                new MessageDtoJson("string val", 23),
                ParameterizedTypeReference.forType(MessageDtoJson.class));
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/rpc/error")
    // Add rabbitTemplate.setMessageConverter(new RemoteInvocationAwareMessageConverterAdapter());
    public ResponseEntity<Object> postDirectRPCMessageWithDtoError() {
        Object response = null; // Exception handling does not work for Json RPC, see  ->
        // https://stackoverflow.com/questions/53278894/spring-amqp-rpc-consumer-and-throw-exception
        try {
            response = rabbitTemplate.convertSendAndReceive(
                    "dto.rpc.exchange.error", "dto.rpc.routing.key.error",
                    new MessageDtoSerializable("string val", 23));
        } catch (AmqpException ex) { // Any exception on this side will be wrapped by AmqpException
            System.out.println("Catch this");
        }
        return ResponseEntity.ok(response == null ? "All correct" : "Something wrong");
    }

    @PostMapping(value = "/dlq")
    public void deadLetterQueueExample() {
        jsonRabbitTemplate.convertAndSend("throwing.ex.exchange", "throwing.ex.routing.key", "Dlq message");
    }

}
