package com.akarge.jgood.rest.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ramazan Karakaya
 * 
 */

public class RestControllerBase {
	protected final Logger log = LoggerFactory.getLogger(getClass());

	protected URI builURISilent(HttpServletRequest req, String path) {
		try {
			return new URI(req.getRequestURI() + path);
		} catch (URISyntaxException e) {
			log.error("Unexpected error", e);
			return null;
		}
	}

}
