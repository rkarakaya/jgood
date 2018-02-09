package com.akarge.jgood.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.akarge.jgood.common.model.NumericEnum;
import com.akarge.jgood.dao.mongo.JgMongoClient;
import com.mongodb.client.model.UpdateOptions;

/**
 * @author Ramazan Karakaya
 * 
 */
public class JgBaseMongoDao implements JgMongoDao {


	@Value("${main.db.name:'jgood'}")
	protected String mainDbName; 

	@Autowired
	protected JgMongoClient client;
	protected final static UpdateOptions UPSERT_OPTION = createUpdateOptions(true);

	private static UpdateOptions createUpdateOptions(boolean upsert) {
		UpdateOptions opt = new UpdateOptions();
		opt.upsert(upsert);
		return opt;
	}

	protected List<Object> enumToValueList(Enum<?>... enumValues) {
		List<Object> sl = new ArrayList<>(enumValues.length);

		for (Enum<?> enm : enumValues) {
			if (enm instanceof NumericEnum) {
				sl.add(((NumericEnum) enm).shortValue());
			} else {
				sl.add(enm.name());
			}
		}
		return sl;
	}

}
