package com.rabbit.web;

import com.rabbit.web.dto.SimpleMessageDto;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class SimpleRabbitController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping(value = "/direct")
    public ResponseEntity<String> postDirectMessage() {
        SimpleMessageDto simpleMessageDto1 = new SimpleMessageDto("direct.exchange",
                "direct.routing.key1", "Direct message to queue 1", 1);
        SimpleMessageDto simpleMessageDto2 = new SimpleMessageDto("direct.exchange",
                "direct.routing.key2", "Direct message to queue 2", 1);

        sendSimpleMessage(simpleMessageDto1);
        sendSimpleMessage(simpleMessageDto2);
        return ResponseEntity.ok("OK");
    }

    @PostMapping(value = "/fanout")
    public ResponseEntity<String> postFanoutMessage() {
        SimpleMessageDto simpleMessageDto1 = new SimpleMessageDto("fanout.exchange",
                null, "Fanout message to queue 1 and 2", 1);

        sendSimpleMessage(simpleMessageDto1);
        return ResponseEntity.ok("OK");
    }

    @PostMapping(value = "/topic")
    public ResponseEntity<String> postTopicMessage() {
        SimpleMessageDto simpleMessageDto1 = new SimpleMessageDto("topic.exchange",
                "topic.routing.key.only.1", "Topic message to queue 1", 1);
        SimpleMessageDto simpleMessageDto2 = new SimpleMessageDto("topic.exchange",
                "topic.routing.key.special.1.and.", "Topic message to 1 and 2", 1);

        sendSimpleMessage(simpleMessageDto1);
        sendSimpleMessage(simpleMessageDto2);
        return ResponseEntity.ok("OK");
    }

    private void sendSimpleMessage(SimpleMessageDto simpleMessageDto) {
        for(int i=0;i<simpleMessageDto.getCount();i++) {
            rabbitTemplate.convertAndSend(simpleMessageDto.getExchange(), simpleMessageDto.getRoutingKey(),
                    simpleMessageDto.getMessage());
        }
    }

    @PostMapping(value = "/headers")
    public ResponseEntity<String> postHeadersMessage() {
        SimpleMessageDto simpleMessageDto1 = new SimpleMessageDto("headers.exchange",
                null, "Headers message to queue 1", 1);
        SimpleMessageDto simpleMessageDto2 = new SimpleMessageDto("headers.exchange",
                null, "Headers message to queue 1", 1);

        sendHeaderMessage(simpleMessageDto1,"header-value1");
        sendHeaderMessage(simpleMessageDto2,"header-value2");
        return ResponseEntity.ok("OK");
    }

    private void sendHeaderMessage(SimpleMessageDto simpleMessageDto, String routingHeaderValue) {
        for(int i=0;i<simpleMessageDto.getCount();i++) {
            rabbitTemplate.convertAndSend(simpleMessageDto.getExchange(), null,
                    simpleMessageDto.getMessage(), m -> addHeadersToMessage(routingHeaderValue, m));
        }
    }

    private Message addHeadersToMessage(String headerValue, Message message) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        headers.put("header-key", headerValue);
        return message;
    }

}
