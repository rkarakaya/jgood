package com.akarge.jgood.service.bootstrap;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.akarge.jgood.dao.bootstrap.AppDaoConfiguration;

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

@Import({ //
		AppDaoConfiguration.class, //
})

public class AppServiceConfiguration {

}
