package com.epam.testapp;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

/**
 * DateConverter --- a class with static methods which allow to convert
 * java.sql.Date to String and on the contrary with different date formats
 * 
 * @author Marharyta_Amelyanchuk
 */
final public class DateConverter {

	private static final Logger LOG = Logger.getLogger(DateConverter.class);

	private DateConverter() {
	}

	/**
	 * Converts string to java.util.Date using unlimited number of date formats.
	 * Method parameters format and pattern are string arrays and contain needed
	 * date formats and patterns, so values in the both arrays must be
	 * corresponding to each other. String is checked with every date pattern:
	 * if match is found, it will be parsed to java.sql.Date and returned, if
	 * string doesn't match any date format the current date will be returned.
	 * 
	 * 
	 * @param str
	 *            the String date to be converted
	 * @param format
	 *            string array of date formats
	 * @param pattern
	 *            string array of date patterns for corresponding formats
	 * 
	 * @return Date which is get from string by parsing or the current date if
	 *         string doesn't match date patterns
	 */
	public static Date stringToDate(String str, String[] pattern,
			String[] format) {
		if (str == null) {
			LOG.error("Date string can't be parsed to java.sql.Date: it's null. Current date will be returned");
			return new Date(System.currentTimeMillis());
		}
		Date newDate;
		try {
			int size = pattern.length;
			if (size != format.length) {
				LOG.error("Parsing string to java.sql.Date: size of format and pattern arrays must be equal");
				return new Date(System.currentTimeMillis());
			}
			java.util.Date utilDate = null;
			for (int i = 0; i < size; i++) {
				if (str.matches(pattern[i])) {
					utilDate = parseDate(str, format[i]);
					break;
				}
			}
			newDate = new Date(utilDate.getTime());
		} catch (ParseException | NullPointerException e) {
			LOG.error("Date string can't be parsed to java.sql.Date: "
					+ e.getMessage() + ". Current date will be returned");
			newDate = new Date(System.currentTimeMillis());
		}
		return newDate;
	}

	/**
	 * Overloading method for converting string to java.sql.Date with one date
	 * format. If string doesn't match this format (it is checked with date
	 * pattern) current date will be returned. Initializes 2 arrays, filling
	 * them values of method parameters format and pattern with a view to call
	 * method (String str, String[] pattern, String[] format) transferring there
	 * these arrays.
	 * 
	 * @param str
	 *            the String date to be converted
	 * @param format
	 *            string which contains date format
	 * @param pattern
	 *            string which contains date pattern
	 * 
	 * @return Date which is get from string by parsing or current date if
	 *         string doesn't match date patterns
	 */
	public static Date stringToDate(String str, String pattern, String format) {
		String[] patternArray = { pattern };
		String[] formatArray = { format };
		return stringToDate(str, patternArray, formatArray);
	}

	/**
	 * Method for use inside the class. Parses string value to java.util.Date
	 * using date format.
	 * 
	 * @param date
	 *            the String date to be parsed
	 * @param format
	 *            date format
	 * 
	 * @return java.util.Date which is get from string by parsing by parsing it
	 *         with date format
	 * @exception ParseException
	 *                if parsing string with set format will be impossible
	 */
	private static java.util.Date parseDate(String date, String format)
			throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		java.util.Date utilDate = dateFormat.parse(date);
		return utilDate;
	}

	/**
	 * Converts java.sql.Date to String using any format
	 * 
	 * @param date
	 *            the Date to be converted
	 * @param format
	 *            the String - date format
	 * 
	 * @return String
	 */
	public static String dateToString(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		if(date == null){
			Date today = new Date(System.currentTimeMillis());
			return dateFormat.format(today);
		}		
		String dateString = dateFormat.format(date);
		return dateString;
	}

}
