package com.akarge.jgood.service;

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
import com.akarge.jgood.service.informing.InformingService;
import com.google.common.collect.Iterables;

/**
 * @author Ramazan Karakaya
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppServiceTestConfiguration.class })
public class InformingServiceTests {

	@Autowired
	private InformingService service;

	@Test
	public void shouldInsertAndReadAndDelete() {

		Informing inf = Informing.createNew("Tom", "lorem", InformingStatus.NEW);

		Date now = new Date();
		inf.setUpdatedAt(now);
		inf.setCreatedAt(now);
		service.addOrUpdate(inf);
		assertNotNull("Save informing failed", inf.getId());
		Informing infFetched = service.getInformingById(inf.getId());
		assertNotNull("Can not find saved informing", infFetched);

		service.deleteInformingById(inf.getId());
		Informing fetchedAfterDelete = service.getInformingById(inf.getId());
		assertEquals("delete informing failed", fetchedAfterDelete, null);

	}

	@Test
	public void shouldFindByStatusOrdered() {

		Informing inf1 = Informing.createNew("Tom1", "lorem1", InformingStatus.NEW);
		service.addOrUpdate(inf1);

		Informing inf2 = Informing.createNew("Tom2", "lorem2", InformingStatus.NEW);
		service.addOrUpdate(inf2);

		List<Informing> infList = new ArrayList<Informing>(2);
		Iterables.addAll(infList, service.listRecentInformings(2));

		assertEquals(2, infList.size());
		String msg = "informings returned in wrog order";
		assertEquals(msg, infList.get(0).getId(), inf2.getId());
		assertEquals(msg, infList.get(1).getId(), inf1.getId());

	}

}
