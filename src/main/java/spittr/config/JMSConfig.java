package spittr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import spittr.Spittle;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import java.util.HashMap;

/**
 * Created by dell on 2017-7-13.
 */
@Configuration
public class JMSConfig {
    @Value("${broker.url}")
    private String brokerUrl;

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        return activeMQConnectionFactory;
    }

    @Bean
    @Qualifier("queue")
    public ActiveMQQueue queue() {
        return new ActiveMQQueue("spitter.queue");
    }

    @Bean
    @Qualifier("topic")
    public ActiveMQTopic topic() {
        return new ActiveMQTopic("spitter.tipic");
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setObjectMapper(objectMapper);
        messageConverter.setTargetType(MessageType.TEXT);
        messageConverter.setTypeIdPropertyName("spittr.spittle.classname");
        HashMap<String, Class<?>> idMappig = new HashMap<>();
        idMappig.put(Spittle.class.getName(), Spittle.class);
        messageConverter.setTypeIdMappings(idMappig);
        return messageConverter;
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, @Qualifier("queue") Destination destination, MessageConverter messageConverter) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setDefaultDestination(destination);
        jmsTemplate.setMessageConverter(messageConverter);
        return jmsTemplate;
    }
}
