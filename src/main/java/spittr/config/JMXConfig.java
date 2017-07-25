package spittr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.annotation.AnnotationMBeanExporter;
import org.springframework.jmx.support.ConnectorServerFactoryBean;
import org.springframework.jmx.support.MBeanServerConnectionFactoryBean;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.remoting.rmi.RmiRegistryFactoryBean;

import java.net.MalformedURLException;

@Configuration
public class JMXConfig {
    @Bean
    public AnnotationMBeanExporter mbeanExporter() {
        AnnotationMBeanExporter mbeanExporter = new AnnotationMBeanExporter();
        mbeanExporter.setRegistrationPolicy(RegistrationPolicy.IGNORE_EXISTING);
        return mbeanExporter;
    }

    /*@Bean
    public RmiRegistryFactoryBean rmiRegistryFactoryBean() {
        RmiRegistryFactoryBean rmiRegistryFactoryBean = new RmiRegistryFactoryBean();
        rmiRegistryFactoryBean.setPort(1099);
        //rmiRegistryFactoryBean.setAlwaysCreate(true);
        return rmiRegistryFactoryBean;
    }*/

    @Bean
    public ConnectorServerFactoryBean connectorServerFactoryBean() {
        ConnectorServerFactoryBean connectorServerFactoryBean = new ConnectorServerFactoryBean();
        connectorServerFactoryBean.setServiceUrl("service:jmx:jmxmp://localhost:9875");
        return new ConnectorServerFactoryBean();
    }

    @Bean
    public MBeanServerConnectionFactoryBean mbeanServerConnectionFactoryBean() throws MalformedURLException {
        MBeanServerConnectionFactoryBean mbeanServerConnectionFactoryBean = new MBeanServerConnectionFactoryBean();
        mbeanServerConnectionFactoryBean.setServiceUrl("service:jmx:jmxmp://localhost:9875");
        return mbeanServerConnectionFactoryBean;
    }
}
