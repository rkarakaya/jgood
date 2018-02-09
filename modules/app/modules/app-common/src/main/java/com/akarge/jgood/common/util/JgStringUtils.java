package com.akarge.jgood.common.util;

import org.apache.commons.lang3.StringUtils;


/**
 * @author Ramazan Karakaya 
 */
public abstract class JgStringUtils {

	public static String prependIfNotStarts(String str, String prepend) {
		if (str != null && str.startsWith(prepend) == false) {
			return prepend + str;
		}
		return str;
	}
	
	public static StrMatcher getMatcherFor(String value) {
		if ("*".equals(value)) {
			return new AnyMatcher();
		} else if (value.endsWith("*")) {
			return new PrefixMatcher(StringUtils.substringBefore(value, "*"));
		} else {
			return new ExactMatcher(value);
		}

	}
	
	public static abstract class StrMatcher {
		protected String myValue;

		public StrMatcher(String val) {
			myValue = val;
		}

		public abstract boolean matches(String s);
	}

	public static class ExactMatcher extends StrMatcher {

		public ExactMatcher(String val) {
			super(val);
		}

		@Override
		public boolean matches(String s) {
			return myValue.equals(s);
		}
	}

	public static class PrefixMatcher extends StrMatcher {

		public PrefixMatcher(String val) {
			super(val);
		}

		@Override
		public boolean matches(String s) {
			return s.startsWith(myValue);
		}
	}

	public static class AnyMatcher extends StrMatcher {

		public AnyMatcher() {
			super(null);
		}

		@Override
		public boolean matches(String s) {
			return true;
		}
	}


	
}
