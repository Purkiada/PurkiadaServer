package cz.mbucek.purkiadaserver.utilities.exceptions;

import java.util.Map;

public class ForbiddenException extends LocalizedRuntimeException{
	private static final long serialVersionUID = 2348628919705952804L;
	
	public ForbiddenException() {
		super();
		this.messageKey = "exceptions.internal_server_error";
	}
	
	public ForbiddenException(String... errors) {
		this();
		this.errors = errors;
	}
	
	public ForbiddenException(Map<String, Object> data, String... errors) {
		this(errors);
		this.data = data;
	}
	
	public ForbiddenException(String message, Map<String, Object> data, String... errors) {
		this(data, errors);
		this.messageKey = message;
	}
}
