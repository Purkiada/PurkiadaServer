package cz.mbucek.purkiadaserver.controllers;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import cz.mbucek.purkiadaserver.utilities.ArrayUtils;
import cz.mbucek.purkiadaserver.utilities.exceptions.BadRequestException;
import cz.mbucek.purkiadaserver.utilities.exceptions.ExceptionEntity;
import cz.mbucek.purkiadaserver.utilities.exceptions.ForbiddenException;
import cz.mbucek.purkiadaserver.utilities.exceptions.InternalServerErrorException;
import cz.mbucek.purkiadaserver.utilities.exceptions.NotFoundException;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, HttpServletRequest request){
		return this.handleException(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), request, ArrayUtils.concat(ex.getErrors(), HttpStatus.NOT_FOUND.name()));
	}


	@ExceptionHandler(InternalServerErrorException.class) public
	ResponseEntity<Object> handleNotFoundException(InternalServerErrorException
			ex, HttpServletRequest request){ return
					this.handleException(HttpStatus.INTERNAL_SERVER_ERROR,
							ex.getLocalizedMessage(), request, 
							ArrayUtils.concat(ex.getErrors(), HttpStatus.INTERNAL_SERVER_ERROR.name()));
	}

	@ExceptionHandler(ForbiddenException.class) public ResponseEntity<Object>
	handleForbiddenException(ForbiddenException ex, HttpServletRequest request){
		return this.handleException(HttpStatus.FORBIDDEN, ex.getLocalizedMessage(),
				request, ArrayUtils.concat(ex.getErrors(), HttpStatus.FORBIDDEN.name())); }

	@ExceptionHandler(BadRequestException.class) public ResponseEntity<Object>
	handleBadRequestException(BadRequestException ex, HttpServletRequest
			request){ return this.handleException(HttpStatus.BAD_REQUEST,
					ex.getLocalizedMessage(), request, ArrayUtils.concat(ex.getErrors(), HttpStatus.BAD_REQUEST.name())); }

	public ResponseEntity<Object> handleException(HttpStatus status, String message, HttpServletRequest request, String... error){
		ExceptionEntity ee = new ExceptionEntity(status, message, Arrays.asList(error), request);
		return new ResponseEntity<Object>(ee, status);
	}
}
