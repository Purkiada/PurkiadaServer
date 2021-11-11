package cz.mbucek.purkiadaserver.utilities.exceptions;

import java.util.Map;

public class NotFoundException extends LocalizedRuntimeException{
	private static final long serialVersionUID = 4371415027066011970L;

	public NotFoundException() {
		super();
		this.messageKey = "exceptions.internal_server_error";
	}
	
	public NotFoundException(String... errors) {
		this();
		this.errors = errors;
	}
	
	public NotFoundException(Map<String, Object> data, String... errors) {
		this(errors);
		this.data = data;
	}
	
	public NotFoundException(String message, Map<String, Object> data, String... errors) {
		this(data, errors);
		this.messageKey = message;
	}
}
