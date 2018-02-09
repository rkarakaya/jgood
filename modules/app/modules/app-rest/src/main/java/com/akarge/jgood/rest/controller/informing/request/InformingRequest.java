package com.akarge.jgood.rest.controller.informing.request;


/**
 * @author Ramazan Karakaya (akarge)
 * 
 */
public class InformingRequest {

	private String id;
	private String informedBy;
	private String text;
	private String gsm;
	private String address;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInformedBy() {
		return informedBy;
	}

	public void setInformedBy(String informedBy) {
		this.informedBy = informedBy;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getGsm() {
		return gsm;
	}

	public String getAddress() {
		return address;
	}

}
