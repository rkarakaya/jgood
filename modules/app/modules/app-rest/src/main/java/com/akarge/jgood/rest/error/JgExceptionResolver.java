package com.akarge.jgood.rest.error;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * @author Ramazan Karakaya
 *
 */
@ControllerAdvice
public class JgExceptionResolver extends ResponseEntityExceptionHandler {

	private static final String RESPONSE_ERROR = "error";
	private static final String RESPONSE_ERROR_CODE = "code";
	private static final String RESPONSE_ERROR_CODE_DETAIL = "detailCode";
	private static final String UNEXPECTED_ERR_MSG = "An unexpected error occurred. Retry later.";

	private Logger logger = LoggerFactory.getLogger(getClass());


	@ExceptionHandler(value = { Exception.class })
	public final ResponseEntity<Map<String, Object>> handleGlobalExceptions(Exception ex, WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();

		// Never use exception.getMessage() for internal server errors.
		responseBody.put(RESPONSE_ERROR, UNEXPECTED_ERR_MSG);
		responseBody.put(RESPONSE_ERROR_CODE, 500);
		responseBody.put(RESPONSE_ERROR_CODE_DETAIL, UNEXPECTED_ERR_MSG);

		logger.error("An unexpected error ocurred during processing request", ex);
		return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}