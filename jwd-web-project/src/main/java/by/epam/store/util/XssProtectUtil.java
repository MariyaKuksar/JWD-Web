package by.epam.store.util;

import org.apache.commons.lang3.StringUtils;

public final class XssProtectUtil {

	private static final String START_TAG = "<";
	private static final String END_TAG = ">";
	private static final String LESS_THAN_CHARACTER = "&lt";
	private static final String GREATER_THAN_CHARACTER = "&gt";

	private XssProtectUtil() {
	}

	public static String correctText(String text) {
		if(text == null) {
			return StringUtils.EMPTY;
		}
		return text.replaceAll(START_TAG, LESS_THAN_CHARACTER).replaceAll(END_TAG, GREATER_THAN_CHARACTER);
	}
}
