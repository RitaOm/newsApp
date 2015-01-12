package com.epam.testapp.util;

import java.sql.Date;

import com.epam.testapp.DateConverter;

/**
 * DateConverterCaller --- a class with static methods which allow to convert
 * java.sql.Date to String and on the contrary with any locale
 * 
 * @author Marharyta_Amelyanchuk
 */
public class DateConverterCaller {

	private DateConverterCaller() {
	}

	/**
	 * Depending on needed languages define array of date formats and array of
	 * date patterns which values are stored in the property file. Then calls
	 * method stringToDate(..) of class DateConverter where transfers these
	 * parameters
	 * 
	 * @param str
	 *            the String date to be converted
	 * @param lang1
	 *            value from enum LocaleLang - first language
	 * @param lang2
	 *            value from enum LocaleLang - second language
	 * 
	 * @return Date which is get from string by parsing or current date if
	 *         string doesn't match date patterns
	 */
	public static Date stringToDate(String str, LocaleLang lang) {
		int size = 0;
		switch (lang) {
		case EN_RU:
			size = 2;
			break;
		case EN:
			size = 1;
			break;
		case RU:
			size = 1;
			break;
		}
		String[] format = new String[size];
		String[] pattern = new String[size];
		switch (lang) {
		case EN_RU:
			format[0] = getEnglishFormat();
			pattern[0] = getEnglishPattern();
			format[1] = getRussianFormat();
			pattern[1] = getRussianPattern();
			break;
		case EN:
			format[0] = getEnglishFormat();
			pattern[0] = getEnglishPattern();
			break;
		case RU:
			format[0] = getRussianFormat();
			pattern[0] = getRussianPattern();
			break;
		}
		Date date = DateConverter.stringToDate(str, pattern, format);
		return date;
	}

	public static String dateToString(Date date, LocaleLang lang) {
		String format = null;
		switch (lang) {
		case EN_RU:			
			format = getEnglishFormat();
			break;
		case EN:
			format = getEnglishFormat();
			break;
		case RU:
			format = getRussianFormat();
			break;
		}
		String str = DateConverter.dateToString(date, format);
		return str;
	}

	private static String getRussianFormat() {
		return ApplicationManager.getProperty("russian.date.format");
	}

	private static String getEnglishFormat() {
		return ApplicationManager.getProperty("english.date.format");

	}
	
	private static String getRussianPattern() {
		return ApplicationManager.getProperty("russian.date.pattern");
	}

	private static String getEnglishPattern() {
		return ApplicationManager.getProperty("english.date.pattern");

	}

}
