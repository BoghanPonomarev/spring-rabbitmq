package com.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class RabbitApplication {


    public static void main(String[] args) {
        SpringApplication.run(RabbitApplication.class, args);
    }


    /**
     * 1. Message with direct exchange V
     * 2. Message with fanout exchange
     * 3. Message with topic exchange
     * 4. Header exchange
     *
     * 5. Batch messages
     * 6. Consume Dto
     * 6. RPC json
     * 7. Exception on client side in RPC
     *
     * 6. Dead Letter
     *
     * 7. ack - nack - reject
     * 7. Time out
     * 7. Delayed message
     */

}
