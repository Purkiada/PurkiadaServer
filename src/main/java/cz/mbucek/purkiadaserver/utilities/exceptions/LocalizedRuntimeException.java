package cz.mbucek.purkiadaserver.utilities.exceptions;

import java.util.Locale;
import java.util.Map;

import org.springframework.context.i18n.LocaleContextHolder;

import cz.mbucek.purkiadaserver.utilities.Messages;


public class LocalizedRuntimeException extends RuntimeException{
	private static final long serialVersionUID = -383535420921991696L;
	
	protected String messageKey;
	protected Locale locale;
	protected String[] errors;
	protected Map<String, Object> data;
	
	public LocalizedRuntimeException(String messageKey, Locale locale) {
		super();
		this.messageKey = messageKey;
		this.locale = locale;
	}
	
	
	public LocalizedRuntimeException() {
		super();
		this.messageKey = "exceptions.generic";
		this.locale = LocaleContextHolder.getLocale();
	}
	
	@Override
	public String getLocalizedMessage() {
		if(data != null) {
			return Messages.applyData(Messages.getMessageForLocale(messageKey, locale), data);
		}
		return Messages.getMessageForLocale(messageKey, locale);
	}
	
	public String[] getErrors() {
		return this.errors;
	}
}
