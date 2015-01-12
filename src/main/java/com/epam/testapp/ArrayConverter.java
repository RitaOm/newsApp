package com.epam.testapp;

import java.util.ArrayList;
import java.util.List;

/**
 * ArrayConverter --- a class with static methods which allow to convert int
 * array to string or to List
 * 
 * @author Marharyta_Amelyanchuk
 */
final public class ArrayConverter {

	private ArrayConverter() {
	}

	/**
	 * Converts int array to one string
	 * 
	 * @param array
	 *            int array to be converted
	 * 
	 * @return String which contains numbers from array separated by commas
	 */
	public static String convertIntArrayToString(Integer[] array) {
		int length;
		if (array == null || (length = array.length) == 0) {
			return "";
		}
		if (length == 1) {
			return String.valueOf(array[0]);
		}
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < length - 1; i++) {
			str.append(array[i] + ", ");
		}
		str.append(array[length - 1]);
		return str.toString();
	}

	/**
	 * Converts int array to List of Integers
	 * 
	 * @param array
	 *            int array to be converted
	 * 
	 * @return List of Integers which contains numbers from array
	 */
	public static List<Integer> convertIntArrayToList(Integer[] array) {
		List<Integer> list = new ArrayList<Integer>();
		if (array == null || array.length == 0) {
			return list;
		}
		for (Integer id : array) {
			list.add(id);
		}
		return list;
	}

}
