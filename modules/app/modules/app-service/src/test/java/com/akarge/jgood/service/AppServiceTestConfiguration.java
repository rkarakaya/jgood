package com.akarge.jgood.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.akarge.jgood.dao.mongo.JgMongoClient;
import com.akarge.jgood.service.bootstrap.ServiceBeans;

/**
 * @author Ramazan Karakaya
 * 
 */
@ComponentScan( //
basePackages = "com.akarge.jgood", //
excludeFilters = { //
@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class) //
} //
)
//
@EnableSpringConfigured
@Configuration
@EnableScheduling
@EnableAsync
@PropertySources(value = { //
@PropertySource(value = "classpath:/test_service.properties") //
})
@Import({ //
	ServiceBeans.InformingServiceBean.class, //
})
public class AppServiceTestConfiguration {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean(name = "nmMongo")
	public JgMongoClient jgMongo() {
		return new JgMongoClient();
	}

}
