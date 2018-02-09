package com.akarge.jgood.rest.controller.informing;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.akarge.jgood.common.model.Informing;
import com.akarge.jgood.common.model.InformingStatus;
import com.akarge.jgood.rest.controller.RestControllerBase;
import com.akarge.jgood.rest.controller.informing.request.InformingRequest;
import com.akarge.jgood.rest.controller.informing.response.InformingListResponse;
import com.akarge.jgood.rest.controller.informing.response.InformingModel;
import com.akarge.jgood.service.informing.InformingService;

/**
 * @author Ramazan Karakaya
 * 
 */
public abstract class InformingControllerBase extends RestControllerBase {

	@Autowired
	protected InformingService informingService;

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public InformingListResponse listRecentInformings(@RequestParam(value = "size", required = false) Integer size) {
		int k = size != null ? size.intValue() : 25;
		List<Informing> infList = informingService.listRecentInformings(k);
		return new InformingListResponse(infList);
	}

	@RequestMapping( method = RequestMethod.PUT)
	public ResponseEntity<?> createNewInforming(@RequestBody InformingRequest infReq, HttpServletRequest httpReq) {

		// TODO validate the model
		Informing informing = toInforming(infReq);
		informing.setCreatedAt(new Date());
		informing.setStatus(InformingStatus.NEW);
		informing = informingService.addOrUpdate(informing);

		return ResponseEntity.created(builURISilent(httpReq, "/informing/" + informing.getId())).build();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public void updateInforming(@RequestBody InformingRequest infReq) {

		// TODO validate the model

		Informing informing = toInforming(infReq);
		informing.setUpdatedAt(new Date());
		informing.setStatus(InformingStatus.NEW);
		informingService.addOrUpdate(informing);

	}
	
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Object getInforming(@PathVariable("id") String id) {
		Informing informing = informingService.getInformingById(id);
		if (informing == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(new InformingModel(informing));
		}
	}

	@ResponseBody
	@RequestMapping(value = "/{id}/close", method = RequestMethod.POST)
	public Object closeInforming(@PathVariable("id") String id) {
		Informing informing = informingService.closeInformingById(id);
		if (informing == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(new InformingModel(informing));
		}
	}

	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Object deleteInforming(@PathVariable("id") String id) {
		boolean found = informingService.deleteInformingById(id);
		if (found == false) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.noContent().build();
		}
	}



	protected Informing toInforming(InformingRequest req) {
		Informing informing = new Informing();
		informing.setInformedBy(req.getInformedBy());
		informing.setText(req.getText());
		return informing;
	}

}
