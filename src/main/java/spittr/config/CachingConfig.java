package spittr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017-6-26.
 */
@Configuration
@EnableCaching
public class CachingConfig {
    @Bean
    public CacheManager cacheManager(net.sf.ehcache.CacheManager ehCacheManager) {
        CompositeCacheManager compositeCacheManager = new CompositeCacheManager();
        List<CacheManager> cacheManagers = new ArrayList<CacheManager>();
        cacheManagers.add(new EhCacheCacheManager(ehCacheManager));
        compositeCacheManager.setCacheManagers(cacheManagers);
        return compositeCacheManager;
    }

    @Bean
    public EhCacheManagerFactoryBean ehcache() {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("cache/ehcache.xml"));
        return ehCacheManagerFactoryBean;
    }
}
