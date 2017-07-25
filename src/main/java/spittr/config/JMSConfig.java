package spittr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.AbstractMessageListenerContainer;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.jms.remoting.JmsInvokerProxyFactoryBean;
import org.springframework.jms.remoting.JmsInvokerServiceExporter;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import spittr.Spittle;
import spittr.alerts.AlertService;
import spittr.alerts.SpittleAlertHandler;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Queue;
import java.util.HashMap;

/**
 * Created by dell on 2017-7-13.
 */
@Configuration
@EnableJms
@ComponentScan("spittr.alerts")
public class JMSConfig {
    @Value("${broker.url}")
    private String brokerUrl;

    public static final String DESTINATION_QUEUE  = "spitter.queue";

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        return activeMQConnectionFactory;
    }

    @Bean
    @Qualifier("queue")
    public ActiveMQQueue queue() {
        return new ActiveMQQueue(DESTINATION_QUEUE);
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

    /*@Bean
    public AbstractMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory, @Qualifier("queue") Destination destination, MessageConverter messageConverter, SpittleAlertHandler spittleAlertHandler) {
        DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(connectionFactory);
        messageListenerContainer.setDestination(destination);
        messageListenerContainer.setMessageConverter(messageConverter);

        MessageListenerAdapter messageListener = new MessageListenerAdapter();
        messageListener.setDelegate(spittleAlertHandler);
        messageListener.setDefaultListenerMethod("handleSpittleAlert");
        messageListenerContainer.setMessageListener(messageListener);

        return messageListenerContainer;
    }*/

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        DefaultJmsListenerContainerFactory listenerContainerFactory = new DefaultJmsListenerContainerFactory();
        listenerContainerFactory.setConnectionFactory(connectionFactory);
        listenerContainerFactory.setConcurrency("2-4");
        listenerContainerFactory.setMessageConverter(messageConverter);
        return listenerContainerFactory;
    }

    /*@Bean
    public JmsInvokerServiceExporter alertServiceExporter(@Qualifier("mail")AlertService alertService) {
        JmsInvokerServiceExporter jmsInvokerServiceExporter = new JmsInvokerServiceExporter();
        jmsInvokerServiceExporter.setService(alertService);
        jmsInvokerServiceExporter.setServiceInterface(AlertService.class);
        return jmsInvokerServiceExporter;
    }*/

    @Bean
    public JmsInvokerProxyFactoryBean remoteAlertService(ConnectionFactory connectionFactory, Queue queue) {
        JmsInvokerProxyFactoryBean jmsInvokerProxyFactoryBean = new JmsInvokerProxyFactoryBean();
        jmsInvokerProxyFactoryBean.setConnectionFactory(connectionFactory);
        jmsInvokerProxyFactoryBean.setQueue(queue);
        jmsInvokerProxyFactoryBean.setServiceInterface(AlertService.class);
        return jmsInvokerProxyFactoryBean;
    }
}
