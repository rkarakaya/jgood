package com.akarge.jgood.dao.informing;

import com.akarge.jgood.common.model.Informing;
import com.akarge.jgood.common.model.InformingStatus;
import com.akarge.jgood.dao.JgMongoDao;

/**
 * @author Ramazan Karakaya 
 *
 */
public interface InformingMongoDao extends JgMongoDao {

	Iterable<Informing> findInformingsByStatusDescending(int size, InformingStatus ...status);

	void upsert(Informing informing);

	Informing findInformingById(String id);

	void deleteInformingById(String id);

}
