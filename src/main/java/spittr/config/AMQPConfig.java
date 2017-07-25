package spittr.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import spittr.Spittle;

/**
 * Created by dell on 2017-7-18.
 */
//@Configuration
//@EnableRabbit
//@ComponentScan("spittr.alerts")
public class AMQPConfig {
    @Value("${rabbitmq.host}")
    private String host;

    @Value("${rabbitmq.port}")
    private int port;

    @Value("${rabbitmq.username}")
    private String username;

    @Value("${rabbitmq.password}")
    private String password;

    @Bean("amqpConnectionFactory")
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean
    public Queue spittleAlertQueue() {
        return new Queue("spittle.alert.queue");
    }

    @Bean
    public Exchange spittleAlertExchange() {
        return new DirectExchange("spittle.alert.exchange");
    }

    @Bean
    public RabbitAdmin admin(ConnectionFactory connectionFactory, Queue queue, Exchange exchange) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.declareQueue(queue);
        admin.declareExchange(exchange);
        admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with("*").noargs());
        return admin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange("spittle.alert.exchange");
        rabbitTemplate.setRoutingKey("spittle.alert.queue");
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public MessageConverter amqpMessageConverter() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setDefaultType(Spittle.class);
        Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter();
        messageConverter.setClassMapper(classMapper);
        return messageConverter;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        rabbitListenerContainerFactory.setConnectionFactory(connectionFactory);
        rabbitListenerContainerFactory.setMessageConverter(messageConverter);
        return rabbitListenerContainerFactory;
    }
}
