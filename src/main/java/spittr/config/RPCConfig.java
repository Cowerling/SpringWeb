package spittr.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.remoting.jaxws.SimpleJaxWsServiceExporter;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import spittr.service.SpitterService;

import java.util.Properties;

/**
 * Created by dell on 2017-6-29.
 */
@Configuration
public class RPCConfig {
    /*@Bean
    public RmiServiceExporter rmiExporter(@Qualifier("default") SpitterService spitterService) {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setService(spitterService);
        rmiServiceExporter.setServiceName("SpitterService");
        rmiServiceExporter.setServiceInterface(SpitterService.class);
        return rmiServiceExporter;
    }*/

    /*@Bean
    public HessianServiceExporter hessianExportedSpitterService(@Qualifier("default") SpitterService spitterService) {
        HessianServiceExporter hessianServiceExporter = new HessianServiceExporter();
        hessianServiceExporter.setService(spitterService);
        hessianServiceExporter.setServiceInterface(SpitterService.class);
        return hessianServiceExporter;
    }*/

    @Bean
    public HttpInvokerServiceExporter httpExportedSpitterService(@Qualifier("default") SpitterService spitterService) {
        HttpInvokerServiceExporter httpInvokerServiceExporter = new HttpInvokerServiceExporter();
        httpInvokerServiceExporter.setService(spitterService);
        httpInvokerServiceExporter.setServiceInterface(SpitterService.class);
        return httpInvokerServiceExporter;
    }

    @Bean
    public HandlerMapping httpInvokerMapping() {
        SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
        Properties mappings = new Properties();
        mappings.setProperty("/spitter.service", "httpExportedSpitterService");
        simpleUrlHandlerMapping.setMappings(mappings);
        return simpleUrlHandlerMapping;
    }

    @Bean
    public SimpleJaxWsServiceExporter jaxWsServiceExporter() {
        SimpleJaxWsServiceExporter simpleJaxWsServiceExporter = new SimpleJaxWsServiceExporter();
        simpleJaxWsServiceExporter.setBaseAddress("http://localhost:8888/services/");
        return simpleJaxWsServiceExporter;
    }
}
