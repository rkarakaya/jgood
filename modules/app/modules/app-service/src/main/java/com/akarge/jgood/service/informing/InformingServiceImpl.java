package com.akarge.jgood.service.informing;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.akarge.jgood.common.model.Informing;
import com.akarge.jgood.common.model.InformingStatus;
import com.akarge.jgood.dao.informing.InformingMongoDao;
import com.akarge.jgood.service.BaseJgoodService;
import com.google.common.collect.Iterables;

/**
 * @author Ramazan Karakaya
 * 
 */
public class InformingServiceImpl extends BaseJgoodService implements InformingService {

	@Autowired
	private InformingMongoDao dao;

	@PostConstruct
	public void init() {
		// initialize db with dummy data
		String text = "Suspicous people living at our bottom. The adress: Studio 103. The Business Centre 61 Wellfield Roa";
		String infBy = "Ramazan Karakaya";
		dao.upsert(Informing.createNew(infBy, text,InformingStatus.NEW));
		dao.upsert(Informing.createNew(infBy, text,InformingStatus.NEW));
		dao.upsert(Informing.createNew(infBy, text,InformingStatus.CLOSED));
		dao.upsert(Informing.createNew(infBy, text,InformingStatus.NEW));
		dao.upsert(Informing.createNew(infBy, text,InformingStatus.NEW));
		dao.upsert(Informing.createNew(infBy, text,InformingStatus.CLOSED));
		dao.upsert(Informing.createNew(infBy, text,InformingStatus.NEW));
		dao.upsert(Informing.createNew(infBy, text,InformingStatus.NEW));
	}

	@Override
	public List<Informing> listRecentInformings(int size) {
		int k = Math.min(1000, size);
		List<Informing> result = new ArrayList<Informing>(k);
		Iterables.addAll(result, dao.findInformingsByStatusDescending(k, InformingStatus.NEW, InformingStatus.CLOSED));
		return result;
	}

	@Override
	public Informing addOrUpdate(Informing informing) {
		dao.upsert(informing);
		return informing;
	}

	@Override
	public Informing getInformingById(String id) {
		return dao.findInformingById(id);
	}

	@Override
	public boolean deleteInformingById(String id) {
		Informing informing = dao.findInformingById(id);
		if (informing != null) {
			// TODO check status, throw exception if it is can't be deleted
			// etc..
			dao.deleteInformingById(id);
		}
		return informing != null;
	}

	@Override
	public Informing closeInformingById(String id) {
		Informing informing = dao.findInformingById(id);
		if (informing != null) {
			// TODO check status, throw exception if it is can't be closed at
			// the moment..
			informing.setStatus(InformingStatus.CLOSED);
			dao.upsert(informing);
		}
		return informing;
	}

}
