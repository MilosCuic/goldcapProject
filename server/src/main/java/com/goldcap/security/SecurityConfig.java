package com.goldcap.security;

import com.goldcap.service.impl.GoldcapUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.goldcap.util.Constants.SIGN_IN_URL;
import static com.goldcap.util.Constants.SIGN_UP_URLS;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private GoldcapUserDetailsService goldcapUserDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return  new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
            throws Exception {
                 authenticationManagerBuilder
                .userDetailsService(goldcapUserDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.cors().and().csrf().disable()
               .addFilterBefore(jwtAuthenticationFilter() , UsernamePasswordAuthenticationFilter.class)
               .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
               .sessionManagement()
               .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               .and()
//               .headers().frameOptions().sameOrigin() to enable H2 database
                .authorizeRequests()
               .antMatchers(
                       "/",
                       "/favicon.ico",
                       "/**/*.png",
                       "/**/*.gif",
                       "/**/*.svg",
                       "/**/*.jpg",
                       "/**/*.html",
                       "/**/*.css",
                       "/**/*.js"
               ).permitAll()
               .antMatchers(SIGN_UP_URLS).permitAll()
               .antMatchers(SIGN_IN_URL).permitAll()
               .anyRequest().authenticated();
    }
}
