package com.akarge.jgood.rest.controller.informing.response;

import java.util.Date;

import com.akarge.jgood.common.model.Informing;

/**
 * @author Ramazan Karakaya
 * 
 */
public class InformingModel {
	public String id;
	public String informedBy;
	public String phone;
	public String address;
	public String text;
	public String status;
	public Date createDate;

	public InformingModel(Informing inf) {
		this.id = inf.getId();
		this.informedBy = inf.getInformedBy();
		this.text = inf.getText();
		this.phone = inf.getPhoneNumber();
		this.address = inf.getAddress();
		this.status = inf.getStatus().name();
		this.createDate = inf.getCreatedAt();
	}

}