package com.twistlet.scrapecimb.model.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.reflect.ConstructorUtils;

public class ToNumberServiceImpl implements ToNumberService {

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Number> T toNumber(final String value, final Class<T> c) {
		Constructor<? extends Number> ctor = (ConstructorUtils
				.getMatchingAccessibleConstructor(c, String.class));
		try {
			return (T) ctor.newInstance(value);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			return null;
		}
	}

}
