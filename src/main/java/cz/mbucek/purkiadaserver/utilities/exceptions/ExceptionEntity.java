package cz.mbucek.purkiadaserver.utilities.exceptions;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

public class ExceptionEntity {

	private int status;
	private ZonedDateTime timestamp;
	String message;
	List<String> errors;
	private String path;
	
	public ExceptionEntity(HttpStatus status, String message, String error, HttpServletRequest request) {
		this.status = status.value();
		this.message = message;
		this.errors = Arrays.asList(error);
		this.timestamp = ZonedDateTime.now();
		if(request != null)
		this.path = request.getRequestURI();
	}
	
	public ExceptionEntity(HttpStatus status, String message, List<String> errors, HttpServletRequest request) {
		this.status = status.value();
		this.message = message;
		this.errors = errors;
		this.timestamp = ZonedDateTime.now();
		if(request != null)
		this.path = request.getRequestURI();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ZonedDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(ZonedDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
