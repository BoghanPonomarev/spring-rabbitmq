package com.rabbit.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.batch.BatchingStrategy;
import org.springframework.amqp.rabbit.batch.SimpleBatchingStrategy;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.BatchingRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.RemoteInvocationAwareMessageConverterAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

@Configuration
public class RabbitConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory("localhost");
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(new RemoteInvocationAwareMessageConverterAdapter());
        return rabbitTemplate;
    }

    // ---------------------------------------------------------------------------------------------------------------------

    @Bean
    public BatchingRabbitTemplate batchingRabbitTemplate(ConnectionFactory connectionFactory) {
        BatchingStrategy strategy = new SimpleBatchingStrategy(500, 25_000, 3_000);
        TaskScheduler scheduler = new ConcurrentTaskScheduler();
        BatchingRabbitTemplate template = new BatchingRabbitTemplate(strategy, scheduler);
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    // ---------------------------------------------------------------------------------------------------------------------

    @Bean
    @Qualifier("jsonRabbitTemplate")
    public RabbitTemplate jsonRabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // ---------------------------------------------------------------------------------------------------------------------

}
