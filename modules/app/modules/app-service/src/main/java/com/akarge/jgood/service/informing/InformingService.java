package com.akarge.jgood.service.informing;

import java.util.List;

import com.akarge.jgood.common.model.Informing;
import com.akarge.jgood.service.JgService;

/**
 * @author Ramazan Karakaya
 * 
 */
public interface InformingService extends JgService {

	List<Informing> listRecentInformings(int size);

	Informing addOrUpdate(Informing informing);

	Informing getInformingById(String id);

	boolean deleteInformingById(String id);

	Informing closeInformingById(String id);

}
