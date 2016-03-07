package za.co.standardbank.sbg.cda.mvc.portlet.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class ContactMeUtil {

	private static final Logger log = Logger.getLogger(ContactMeUtil.class);

	public static boolean validateRequired(String value) {
		if (value == null || value.isEmpty())
			return false;
		return true;
	}
	
	public static boolean validateMailId(String mailId) {
		Pattern pattern = Pattern.compile("^((([a-zA-Z-]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+(\\.([a-zA-Z-]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-zA-Z-]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-zA-Z-]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-zA-Z-]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-zA-Z-]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-zA-Z-]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-zA-Z-]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-zA-Z-]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-zA-Z-]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?$");
		Matcher matcher = pattern.matcher(mailId);
		return (matcher.matches());
	}

	public static boolean validateChars(String name, String charSet) {
		if (charSet == null) {
			charSet = "[a-zA-Z- ]+";
		}
		Pattern pattern = Pattern.compile(charSet);
		Matcher matcher = pattern.matcher(name);
		return (matcher.matches());
	}

	public static boolean validateNotEqual(String name, String notEqual) {
		if (!name.equals(notEqual))
			return true;
		return false;
	}

	public static boolean validateMaxLength(String value, int maxLength) {
		if (value.length() <= maxLength)
			return true;
		return false;
	}

	public static boolean validateMinLength(String value, int minLength) {
		if (value.length() >= minLength)
			return true;
		return false;
	}
}
