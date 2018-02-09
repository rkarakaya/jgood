package com.akarge.jgood.dao.informing;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.akarge.jgood.common.model.Informing;
import com.akarge.jgood.common.model.InformingStatus;
import com.akarge.jgood.dao.DaoConstants;
import com.akarge.jgood.dao.JgBaseMongoDao;
import com.akarge.jgood.dao.mongo.MongoConstants;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

/**
 * @author Ramazan Karakaya
 * 
 */
public class InformingMongoDaoImpl extends JgBaseMongoDao implements InformingMongoDao {

	@Override
	public Iterable<Informing> findInformingsByStatusDescending(int size, InformingStatus... status) {
		MongoCollection<Informing> collection = getInformingsCollection();
		Bson query = Filters.in(InformingCodec.FIELD_STATUS, enumToValueList(status));
		return collection.find(query).sort(Sorts.descending(InformingCodec.FIELD_CREATED_AT)).limit(size);
	}

	@Override
	public Informing findInformingById(String id) {
		MongoCollection<Informing> collection = getInformingsCollection();
		Bson query = Filters.eq(MongoConstants.ID, new ObjectId(id));
		return collection.find(query).first();
	}

	@Override
	public void upsert(Informing informing) {
		MongoCollection<Informing> collection = getInformingsCollection();
		ObjectId id = null;
		if (StringUtils.isNotBlank(informing.getId())) {
			id = new ObjectId(informing.getId());
		} else {
			id = new ObjectId();
			informing.setId(id.toHexString());
		}
		Bson query = Filters.eq(MongoConstants.ID, id);
		collection.updateOne(query, new Document(MongoConstants.SET, informing), UPSERT_OPTION);

	}

	@Override
	public void deleteInformingById(String id) {
		MongoCollection<Informing> collection = getInformingsCollection();
		collection.deleteOne(Filters.eq(MongoConstants.ID, new ObjectId(id)));
	}

	private MongoCollection<Informing> getInformingsCollection() {
		return client.getCollection(mainDbName, DaoConstants.COLLECTION_INFORMINGS, Informing.class);
	}

}
