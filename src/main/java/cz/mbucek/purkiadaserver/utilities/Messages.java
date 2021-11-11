package cz.mbucek.purkiadaserver.utilities;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class Messages {
	public static String getMessageForLocale(String messageKey, Locale locale) {
		return ResourceBundle.getBundle("messages/messages", locale).getString(messageKey);
	}
	
	public static String applyData(String message, Map<String, Object> data) {
		for(var set : data.entrySet()) {
			message = message.replace(set.getKey(), set.getValue().toString());
		}
		return message;
	}
}
