package com.akarge.jgood.dao.mongo;

import com.mongodb.client.MongoCollection;

/**
 * @author Ramazan Karakaya 
 *
 */
public interface CollectionInitializer {

	void initCollection(MongoCollection<?> collection);

}
