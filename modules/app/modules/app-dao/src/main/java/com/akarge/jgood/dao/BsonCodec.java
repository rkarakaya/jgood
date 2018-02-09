package com.akarge.jgood.dao;

import java.util.Date;

import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.types.ObjectId;

import com.akarge.jgood.common.model.NumericEnum;
import com.akarge.jgood.dao.mongo.MongoConstants;

/**
 * @author Ramazan Karakaya
 */
public abstract class BsonCodec<T> implements Codec<T> {

	protected void writeIfNotNull(BsonWriter writer, String field, String value) {
		if (value != null) {
			writer.writeString(field, value);
		}
	}

	protected void writeObjectIdIfNotNull(BsonWriter writer, String hexString) {
		if (hexString != null) {
			writer.writeObjectId(MongoConstants.ID, new ObjectId(hexString));
		}
	}


	protected void writeIfNotNull(BsonWriter writer, String field, Date value) {
		if (value != null) {
			writer.writeDateTime(field, value.getTime());
		}
	}

	protected void writeIfNotNull(BsonWriter writer, String field, Integer value) {
		if (value != null) {
			writer.writeInt32(field, value);
		}
	}

	protected void writeIfNotNull(BsonWriter writer, String field, Enum<?> enumValue) {
		if (enumValue != null) {
			if (enumValue instanceof NumericEnum) {
				writer.writeInt32(field, ((NumericEnum) enumValue).shortValue());
			} else {
				writer.writeString(field, enumValue.name());
			}
		}
	}

	




}
