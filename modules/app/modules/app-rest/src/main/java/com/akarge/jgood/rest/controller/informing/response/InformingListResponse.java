package com.akarge.jgood.rest.controller.informing.response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.akarge.jgood.common.model.Informing;

/**
 * @author Ramazan Karakaya
 * 
 */
public class InformingListResponse {
	private List<InformingModel> list = new ArrayList<>(5);

	public InformingListResponse(Collection<Informing> items) {
		for (Informing inf : items) {
			list.add(new InformingModel(inf));
		}
	}

	public List<InformingModel> getList() {
		return list;
	}
}
