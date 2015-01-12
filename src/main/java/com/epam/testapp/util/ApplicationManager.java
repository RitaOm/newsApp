package com.epam.testapp.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

final public class ApplicationManager {
	private static final Logger LOG = Logger
			.getLogger(ApplicationManager.class);
	private final static ResourceBundle resourceBundle = ResourceBundle
			.getBundle("application");

	private ApplicationManager() {
	}

	public static String getProperty(String key) {
		try {
			return resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			LOG.error(e.getMessage());
			return null;
		}
	}
}
