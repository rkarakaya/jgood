package com.akarge.jgood.common.util;

import org.junit.Test;

import com.akarge.jgood.common.util.JgStringUtils;
import com.akarge.jgood.common.util.JgStringUtils.StrMatcher;

import static org.junit.Assert.*;

/**
 * @author Ramazan Karakaya
 *
 */


public class JgStringUtilTests {

	@Test
	public void exactMatching(){
		StrMatcher matcher = JgStringUtils.getMatcherFor("aaa");
		assertEquals(true, matcher.matches("aaa"));
	}
	
	@Test
	public void anyMatching(){
		StrMatcher matcher = JgStringUtils.getMatcherFor("*");
		assertEquals(true, matcher.matches("aaa"));
	}

	@Test
	public void prefixMatching(){
		StrMatcher matcher = JgStringUtils.getMatcherFor("aaa*");
		assertEquals(true, matcher.matches("aaa"));
	}
	
	@Test
	public void shouldNotPrepend(){
		assertEquals("ab", JgStringUtils.prependIfNotStarts("ab", "a"));
	}
	
	@Test
	public void shouldPrepend(){
		assertEquals("ab", JgStringUtils.prependIfNotStarts("b", "a"));
	}
	

}
