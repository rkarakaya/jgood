package com.akarge.jgood.common.model;

/**
 * @author Ramazan Karakaya
 * 
 */
public enum InformingStatus implements NumericEnum {
	NEW(0), //
	CLOSED(1) //
	;

	private int shortValue;

	private InformingStatus(int shortValue) {
		this.shortValue = shortValue;
	}

	public static InformingStatus fromShortValue(int val) {
		for (InformingStatus e : values()) {
			if (e.shortValue == val) {
				return e;
			}
		}
		throw new IllegalArgumentException("No such enum by this short value --> " + val);
	}

	@Override
	public int shortValue() {
		return shortValue;
	}

}
