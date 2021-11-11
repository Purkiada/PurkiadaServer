package cz.mbucek.purkiadaserver.utilities.exceptions;

import java.util.Map;

public class InternalServerErrorException extends LocalizedRuntimeException{
	private static final long serialVersionUID = -8763841977887307128L;
	
	public InternalServerErrorException() {
		super();
		this.messageKey = "exceptions.internal_server_error";
	}
	
	public InternalServerErrorException(String... errors) {
		this();
		this.errors = errors;
	}
	
	public InternalServerErrorException(Map<String, Object> data, String... errors) {
		this(errors);
		this.data = data;
	}
	
	public InternalServerErrorException(String message, Map<String, Object> data, String... errors) {
		this(data, errors);
		this.messageKey = message;
	}
}
