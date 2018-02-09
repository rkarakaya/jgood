package com.akarge.jgood.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.akarge.jgood.common.model.Informing;
import com.akarge.jgood.common.model.InformingStatus;
import com.akarge.jgood.dao.informing.InformingMongoDaoImpl;
import com.google.common.collect.Iterables;

/**
 * @author Ramazan Karakaya
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppDaoTestConfiguration.class })
public class InformingMongoDaoTests {

	@Autowired
	private InformingMongoDaoImpl dao;

	@Test
	public void shouldInsertAndReadAndDelete() {

		Informing inf = Informing.createNew("Tom", "lorem", InformingStatus.NEW);

		Date now = new Date();
		inf.setUpdatedAt(now);
		inf.setCreatedAt(now);
		dao.upsert(inf);

		assertNotNull("Save informing failed", inf.getId());
		Informing infFetched = dao.findInformingById(inf.getId());
		assertNotNull("Can not find saved informing", infFetched);

		assertEquals("field informedBy not saved or read", inf.getInformedBy(), infFetched.getInformedBy());
		assertEquals("field text not saved or read", inf.getText(), infFetched.getText());
		assertEquals("field status not saved or read", inf.getStatus(), infFetched.getStatus());
		assertEquals("field create date not saved or read", inf.getCreatedAt(), infFetched.getCreatedAt());
		assertEquals("field update date not saved or read", inf.getUpdatedAt(), infFetched.getUpdatedAt());

		dao.deleteInformingById(inf.getId());

		Informing fetchedAfterDelete = dao.findInformingById(inf.getId());
		assertEquals("delete informing failed", fetchedAfterDelete, null);

	}

	@Test
	public void shouldFindByStatusOrdered() {

		Informing inf1 = Informing.createNew("Tom1", "lorem1", InformingStatus.NEW);
		dao.upsert(inf1);

		Informing inf2 = Informing.createNew("Tom2", "lorem2", InformingStatus.NEW);
		dao.upsert(inf2);

		List<Informing> infList = new ArrayList<Informing>(2);
		Iterables.addAll(infList, dao.findInformingsByStatusDescending(2, InformingStatus.NEW));
		
		try {
			assertEquals(2, infList.size());
			String msg = "informings returned in wrog order";
			assertEquals(msg, infList.get(0).getId(), inf2.getId());
			assertEquals(msg, infList.get(1).getId(), inf1.getId());
		} finally {
			dao.deleteInformingById(inf1.getId());
			dao.deleteInformingById(inf2.getId());
		}
		
		


	}

}
