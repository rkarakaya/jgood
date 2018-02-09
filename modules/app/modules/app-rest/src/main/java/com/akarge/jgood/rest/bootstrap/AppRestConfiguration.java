package com.akarge.jgood.rest.bootstrap;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.akarge.jgood.dao.bootstrap.AppDaoConfiguration;
import com.akarge.jgood.service.bootstrap.AppServiceConfiguration;
import com.akarge.jgood.service.bootstrap.ServiceBeans;

/**
 * @author Ramazan Karakaya 
 *
 */

@ComponentScan( basePackages = "com.akarge.jgood")
@EnableSpringConfigured
@Configuration
@EnableWebMvc
@PropertySources(value = { //
		@PropertySource(value = "classpath:/app.properties"), //
})
@Import({ //
		AppDaoConfiguration.class, //
		AppServiceConfiguration.class, //
		ServiceBeans.InformingServiceBean.class,//
})
public class AppRestConfiguration {

}
