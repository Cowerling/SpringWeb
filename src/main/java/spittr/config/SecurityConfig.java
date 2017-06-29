package spittr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import spittr.data.SpitterRepository;
import spittr.security.SpitterUserService;

/**
 * Created by dell on 2017-3-14.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    @Qualifier("default")
    private SpitterRepository spitterRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*auth.inMemoryAuthentication()
                .withUser("user").password("123").roles("USER")
                .and()
                .withUser("admin").password("123").roles("USER", "ADMIN");*/
        /*auth.ldapAuthentication()
                .userSearchBase("ou=people")
                .userSearchFilter("(uid={0})")
                .groupSearchBase("ou=groups")
                .groupSearchFilter("member={0}")
                .contextSource()
                .root("dc=habuma,dc=com")
                .ldif("classpath*:users.ldif");*/
        auth.userDetailsService(new SpitterUserService(spitterRepository));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .formLogin().loginPage("/login")
            .and()
            .logout().logoutSuccessUrl("/")
            .and()
            .httpBasic().realmName("Spitter")
            .and()
            .rememberMe().tokenValiditySeconds(60 * 60 * 28).key("spitterKey")
            .and()
            .authorizeRequests()
            .antMatchers("/spittr/cowerling").access("isAuthenticated() and hasRole('SPITTER') and principal.username == 'cowerling'")
            .antMatchers(HttpMethod.POST,"/spittles").hasRole("SPITTER")
            .anyRequest().permitAll()
            .and()
            .requiresChannel()
            .antMatchers("/spittr/register").requiresSecure()
            .antMatchers("/").requiresInsecure();
    }
}
