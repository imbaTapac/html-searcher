package com.agileengine.document.html.searcher.constant;

import java.util.HashSet;
import java.util.Set;

public class Constants {
	private Constants() {
		throw new IllegalStateException("Constant class");
	}
	/**
	 * Charset constants
	 */
	public static final String UTF_8 = "UTF-8";
	public static final String ASCII = "ASCII";
	public static final String WINDOWS_1251 = "WINDOWS_1251";
	public static final String ISO_8859_1 = "ISO-8859-1";

	public static final String DELIMITER = "->";

	public static final Set<String> CHARSETS = new HashSet<>();

	static {
		CHARSETS.add(UTF_8);
		CHARSETS.add(ASCII);
		CHARSETS.add(WINDOWS_1251);
		CHARSETS.add(ISO_8859_1);
	}
}
