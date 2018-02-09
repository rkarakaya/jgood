package com.akarge.jgood.dao.informing;

import java.util.Date;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import com.akarge.jgood.common.model.Informing;
import com.akarge.jgood.common.model.InformingStatus;
import com.akarge.jgood.dao.BsonCodec;
import com.akarge.jgood.dao.mongo.MongoConstants;

/**
 * @author Ramazan Karakaya 
 */
public class InformingCodec extends BsonCodec<Informing> {

	public static String FIELD_INFORMED_BY = "informer"; 
	public static String FIELD_PHONE_NUMBER = "phone";
	public static String FIELD_ADDRESS = "address";
	public static String FIELD_TEXT = "text";
	public static String FIELD_STATUS= "status";
	public static String FIELD_CREATED_AT= "cdt";
	public static String FIELD_UPDATED_AT= "udt";
	

	
	@Override
	public Class<Informing> getEncoderClass() {
		return Informing.class;
	}

	@Override
	public void encode(BsonWriter writer, Informing model, EncoderContext encoderContext) {
		writer.writeStartDocument();
		writeObjectIdIfNotNull(writer, model.getId());
		writeIfNotNull(writer, FIELD_INFORMED_BY, model.getInformedBy());
		writeIfNotNull(writer, FIELD_TEXT, model.getText());
		writeIfNotNull(writer, FIELD_PHONE_NUMBER, model.getText());
		writeIfNotNull(writer, FIELD_ADDRESS, model.getText());
		writeIfNotNull(writer, FIELD_STATUS, model.getStatus());
		writeIfNotNull(writer, FIELD_CREATED_AT, model.getCreatedAt());
		writeIfNotNull(writer, FIELD_UPDATED_AT, model.getUpdatedAt());
		writer.writeEndDocument();
	}

	@Override
	public Informing decode(BsonReader reader, DecoderContext decoderContext) {
		Informing model = new Informing();

		reader.readStartDocument();
		while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
			String fieldName = reader.readName();
			if (MongoConstants.ID.equals(fieldName)) {
				model.setId(reader.readObjectId().toHexString());
			} else if (FIELD_INFORMED_BY.equals(fieldName)) {
				model.setInformedBy(reader.readString());
			} else if (FIELD_TEXT.equals(fieldName)) {
				model.setText(reader.readString());
			} else if (FIELD_PHONE_NUMBER.equals(fieldName)) {
				model.setPhoneNumber(reader.readString());
			} else if (FIELD_ADDRESS.equals(fieldName)) {
				model.setAddress(reader.readString());
			} else if (FIELD_STATUS.equals(fieldName)) {
				model.setStatus(InformingStatus.fromShortValue(reader.readInt32()));
			} else if (FIELD_CREATED_AT.equals(fieldName)) {
				model.setCreatedAt(new Date(reader.readDateTime()));
			} else if (FIELD_UPDATED_AT.equals(fieldName)) {
				model.setUpdatedAt(new Date(reader.readDateTime()));
			} else {
				reader.skipValue();
			}
		}

		reader.readEndDocument();
		return model;
	}

}
