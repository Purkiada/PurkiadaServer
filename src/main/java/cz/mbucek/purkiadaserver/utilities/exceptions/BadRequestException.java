package cz.mbucek.purkiadaserver.utilities.exceptions;

import java.util.Map;

public class BadRequestException extends LocalizedRuntimeException {
	private static final long serialVersionUID = -1855389650720095862L;

	public BadRequestException() {
		super();
		this.messageKey = "exceptions.internal_server_error";
	}
	
	public BadRequestException(String... errors) {
		this();
		this.errors = errors;
	}
	
	public BadRequestException(Map<String, Object> data, String... errors) {
		this(errors);
		this.data = data;
	}
	
	public BadRequestException(String message, Map<String, Object> data, String... errors) {
		this(data, errors);
		this.messageKey = message;
	}
}
