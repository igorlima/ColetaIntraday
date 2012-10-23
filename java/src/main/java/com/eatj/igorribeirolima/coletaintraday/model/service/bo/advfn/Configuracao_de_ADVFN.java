package com.eatj.igorribeirolima.coletaintraday.model.service.bo.advfn;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Configuracao_de_ADVFN {
	private static final String BUNDLE_NAME = "com.eatj.igorribeirolima.coletaintraday.model.service.bo.advfn.messages";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private Configuracao_de_ADVFN() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
