package com.iamcontent.core;

/**
 * General Java language helper functions.
 * @author Greg Elderfield
 */
public class LangUtils {
	
	public static <T> T newInstance(String className, Class<T> castTo) {
		return castTo.cast(newInstance(forName(className)));
	}

	public static <T> T newInstance(Class<T> clazz) {
		try {
			return clazz.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
	
	public static Class<?> forName(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException | NoClassDefFoundError e) {
			throw new IllegalStateException(e);
		}
	}

	public static String packageNameOf(Class<?> clazz) {
		return clazz.getPackage().getName();
	}
}
