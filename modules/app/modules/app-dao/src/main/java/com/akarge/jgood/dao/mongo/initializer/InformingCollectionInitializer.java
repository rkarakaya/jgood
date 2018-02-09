package com.akarge.jgood.dao.mongo.initializer;

import org.bson.Document;

import com.akarge.jgood.dao.informing.InformingCodec;
import com.akarge.jgood.dao.mongo.CollectionInitializer;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;

/**
 * @author Ramazan Karakaya 
 *
 */
public class InformingCollectionInitializer implements CollectionInitializer {

	@Override
	public void initCollection(MongoCollection<?> collection) {
		createIndexCreateDate(collection);
	}
	
	private void createIndexCreateDate(MongoCollection<?> coll) {
		Document keys = new Document();
		keys.put(InformingCodec.FIELD_CREATED_AT, -1);

		IndexOptions options = new IndexOptions();
		options.background(true);
		options.unique(false);
		options.sparse(true);
		options.name("by_create_date");
		coll.createIndex(keys, options);
	}
	

}
