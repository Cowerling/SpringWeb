package spittr.config;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

/**
 * Created by cowerling on 17-1-7.
 */
public class SpittrWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    private static final int MEGA_BYTE_SIZE = 1024 * 1024;
    private static final String ACTIVE_PROFILES  = "development";

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/", "*.service" };
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { RootConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebConfig.class };
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setMultipartConfig(new MultipartConfigElement("E:\\Temp\\spittr\\uploads", 2 * MEGA_BYTE_SIZE, 4 * MEGA_BYTE_SIZE, 0));
    }

    @Override
    protected WebApplicationContext createRootApplicationContext() {
        WebApplicationContext context = super.createRootApplicationContext();
        ConfigurableEnvironment environment = (ConfigurableEnvironment) context.getEnvironment();
        environment.setActiveProfiles(ACTIVE_PROFILES);
        return context;
    }
}
