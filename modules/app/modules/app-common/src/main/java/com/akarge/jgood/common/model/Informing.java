package com.akarge.jgood.common.model;

import java.util.Date;

/**
 * @author Ramazan Karakaya
 * 
 */
public class Informing {
	private String id;
	private String informedBy;
	private String phoneNumber;
	private String text;
	private String address;
	private InformingStatus status;
	private Date createdAt;
	private Date updatedAt;

	public Informing() {
	}

	public static Informing createNew(String informedBy, String text, InformingStatus status) {
		Informing inf = new Informing();
		inf.setInformedBy(informedBy);
		inf.setText(text);
		inf.setStatus(status);
		inf.setCreatedAt(new Date());
		return inf;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

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

	public InformingStatus getStatus() {
		return status;
	}

	public void setStatus(InformingStatus status) {
		this.status = status;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Informing other = (Informing) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Informing [id=");
		builder.append(id);
		builder.append(", informedBy=");
		builder.append(informedBy);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

}
