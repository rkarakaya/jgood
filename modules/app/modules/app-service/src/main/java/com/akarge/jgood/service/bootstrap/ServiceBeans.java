package com.akarge.jgood.service.bootstrap;

import org.springframework.context.annotation.Bean;

import com.akarge.jgood.dao.informing.InformingMongoDao;
import com.akarge.jgood.dao.informing.InformingMongoDaoImpl;
import com.akarge.jgood.service.informing.InformingService;
import com.akarge.jgood.service.informing.InformingServiceImpl;

/**
 * @author Ramazan Karakaya 
 *
 */
public class ServiceBeans {

	
	public static class InformingServiceBean {
		@Bean(name = "informingMongoDao")
		public InformingMongoDao informingMongoDao() {
			return new InformingMongoDaoImpl();
		}

		@Bean(name = "informingService")
		public InformingService informingService() {
			return new InformingServiceImpl();
		}
	}
}
