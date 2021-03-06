package com.akarge.jgood.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.akarge.jgood.dao.informing.InformingMongoDaoImpl;
import com.akarge.jgood.dao.mongo.JgMongoClient;

/**
 * @author Ramazan Karakaya 
 *
 */
@ComponentScan( //
basePackages = "com.akarge.jgood", //
excludeFilters = { //
		@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class) //
} //
) //
@EnableSpringConfigured
@Configuration
@EnableScheduling
@EnableAsync

@PropertySources(value = { //
		@PropertySource(value = "classpath:/test_database.properties") //
})

public class AppDaoTestConfiguration {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean(name = "nmMongo")
	public JgMongoClient jgMongo() {
		return new JgMongoClient();
	}
	
	
	@Bean(name = "informingMongoDaoImpl")
	public InformingMongoDaoImpl informingMongoDaoImpl() {
		return new InformingMongoDaoImpl();
	}
	
}
