package com.akarge.jgood.dao.mongo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import com.akarge.jgood.common.util.ConcurrentHashSet;
import com.akarge.jgood.common.util.JgStringUtils;
import com.akarge.jgood.common.util.JgStringUtils.StrMatcher;
import com.akarge.jgood.dao.DaoConstants;
import com.akarge.jgood.dao.mongo.initializer.InformingCollectionInitializer;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;

/**
 * @author Ramazan Karakaya 
 */
public class JgMongoClient{

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${mongo.hosts:'127.0.0.1'}")
	private String mongoHosts;

	@Value("${mongo.connection.timeout:10000}")
	private int connectionTimeout;

	@Value("${mongo.socket.timeout:10000}")
	private int socketTimeout;

	@Value("${mongo.max.wait:120000}")
	private int maxWaitTime;

	
	private HashMap<String, Set<String>> initializedDbMap = new HashMap<>();
	private Map<StrMatcher, CollectionInitializer> collectionInitializers = new HashMap<>();
	private MongoClient mongoClient;

	public JgMongoClient() {
		collectionInitializers.put(JgStringUtils.getMatcherFor(DaoConstants.COLLECTION_INFORMINGS), new InformingCollectionInitializer());
		
	}

	

	
	@PostConstruct
	public void onComponentStart() {

		List<Codec<?>> codecList = new ArrayList<>();
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);

		provider.addIncludeFilter(new AssignableTypeFilter(Codec.class));
		Set<BeanDefinition> codecBeans = provider.findCandidateComponents("com.akarge");
		for (BeanDefinition bd : codecBeans) {
			try {
				Codec<?> newInstance = (Codec<?>) Class.forName(bd.getBeanClassName()).newInstance();
				codecList.add(newInstance);
			} catch (Exception e) {
				logger.error("Error instantiating bson codec.{}", bd.getBeanClassName(), e);
			}
		}

		mongoClient = createMongoClient(codecList);
	}
	
	

	private List<ServerAddress> createServerAddress(String[] hostArray) {
		List<ServerAddress> serverAddress = new ArrayList<>();

		for (String host : hostArray) {
			try {
				serverAddress.add(new ServerAddress(host));
			} catch (Exception e) {
				logger.error(ExceptionUtils.getStackTrace(e));
			}
		}

		return serverAddress;
	}

	private MongoClient createMongoClient(List<Codec<?>> codecs) {
		String[] hostArray = StringUtils.split(mongoHosts,',');
		List<ServerAddress> seeds = createServerAddress(hostArray);

		Builder optionsBuilder = MongoClientOptions.builder();
		optionsBuilder.connectTimeout(connectionTimeout);
		optionsBuilder.maxWaitTime(maxWaitTime);
		optionsBuilder.socketTimeout(socketTimeout);

		CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(), CodecRegistries.fromCodecs(codecs));
		optionsBuilder.codecRegistry(codecRegistry);

		MongoClient mongoClient = new MongoClient(seeds, optionsBuilder.build());
		return mongoClient;
	}

	

	public MongoCollection<Document> getCollection(String dbName, String collectionName) {
		return getCollection(dbName, collectionName, Document.class);
	}

	public <DocCls> MongoCollection<DocCls> getCollection(String dbName, String collectionName, Class<DocCls> docCls) {

		MongoCollection<DocCls> collection = mongoClient.getDatabase(dbName).getCollection(collectionName, docCls);


		Set<String> initCollSet = initializedDbMap.get(dbName);
		if (initCollSet == null) {
			synchronized (this) {
				initCollSet = initializedDbMap.get(dbName);
				if (initCollSet == null) {
					initCollSet = new ConcurrentHashSet<>();
					initializedDbMap.put(dbName, initCollSet);
				}
			}
		}

		if (initCollSet.contains(collectionName) == false) {
			synchronized (initCollSet) {
				if (initCollSet.contains(collectionName) == false) {
					initCollSet.add(collectionName);
					for (Entry<StrMatcher, CollectionInitializer> entry : collectionInitializers.entrySet()) {
						if (entry.getKey().matches(collectionName)) {
							entry.getValue().initCollection(collection);
						}
					}
				}
			}
		}

		return collection;
	}

	


}
