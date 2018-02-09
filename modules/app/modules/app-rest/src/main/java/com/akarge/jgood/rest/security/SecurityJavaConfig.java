package com.akarge.jgood.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

/**
 * @author Ramazan Karakaya
 * 
 */
@Configuration
@EnableWebSecurity
@ComponentScan("com.akarge.jgood")
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Autowired
	private JgSavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()//
				.withUser("admin@jgood.com").password("12345").roles("ADMIN").and()//
				.withUser("user@jgood.com").password("12345").roles("USER");//
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http//
		.csrf().disable()//
				.exceptionHandling()//
				.authenticationEntryPoint(restAuthenticationEntryPoint)//
				.and()//
				.authorizeRequests()//
				.antMatchers("/informing").hasRole("ADMIN")//
				.antMatchers("/user/profile").authenticated()//
				.and()//
				.formLogin()//
				.successHandler(authenticationSuccessHandler)//
				.failureHandler(new SimpleUrlAuthenticationFailureHandler())//
				.and()//
				.logout();
	}

	@Bean
	public JgSavedRequestAwareAuthenticationSuccessHandler mySuccessHandler() {
		return new JgSavedRequestAwareAuthenticationSuccessHandler();
	}

	@Bean
	public SimpleUrlAuthenticationFailureHandler myFailureHandler() {
		return new SimpleUrlAuthenticationFailureHandler();
	}
}