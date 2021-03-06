package ru.maxmorev.restful.eshop.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import ru.maxmorev.restful.eshop.rest.Constants;

/**
 * Created by maxim.morev on 05/06/19.
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${web.root}")
    private String webRoot;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        try {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        } catch (Exception e) {
            log.error("Could not configure authentication!", e);
        }
    }

    /*@Bean(name = { "userDetailsService" })
    public UserDetailsService getUserDetailsService(){ return new CustomerServiceImpl();}
*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //@Autowired
    //WebApplicationContext ctx;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().httpStrictTransportSecurity()
                .maxAgeInSeconds(0)
                .includeSubDomains(true);

        http.httpBasic().and()
                .authorizeRequests()
                .antMatchers("/adm/**").hasAuthority("ADMIN")
                .antMatchers(Constants.REST_PRIVATE_URI + "**").hasAuthority("ADMIN")
                .antMatchers("/shopping/cart/checkout/").hasAuthority("CUSTOMER")
                .antMatchers("/customer/account/update/").hasAuthority("CUSTOMER")
                .antMatchers(Constants.REST_CUSTOMER_URI + "**").hasAuthority("CUSTOMER")
                .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl(webRoot + "/login")
                .loginPage(webRoot + "/security/in/")
                .defaultSuccessUrl("/", false)
                .and()
                .csrf().disable();
    }

    //@Bean
    public CsrfTokenRepository repo() {
        HttpSessionCsrfTokenRepository repo = new HttpSessionCsrfTokenRepository();
        repo.setParameterName("_csrf");
        repo.setHeaderName("X-CSRF-TOKEN");
        return repo;
    }
}