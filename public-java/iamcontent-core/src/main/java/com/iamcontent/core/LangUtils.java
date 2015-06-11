/**
  IAmContent Public Libraries.
  Copyright (C) 2015 Greg Elderfield
  @author Greg Elderfield, support@jarchitect.co.uk
 
  This program is free software; you can redistribute it and/or modify it under the terms of the
  GNU General Public License as published by the Free Software Foundation; either version 2 of 
  the License, or (at your option) any later version.

  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
  See the GNU General Public License for more details.

  You should have received a copy of the GNU General Public License along with this program;
  if not, write to the Free Software Foundation, Inc., 
  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package com.iamcontent.core;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;

/**
 * Helper functions for Java language usage.
 * @author Greg Elderfield
 */
public class LangUtils {

	public static Type mapType(Class<?> keyClass, Class<?> valueClass) {
		return mapTypeToken(keyClass, valueClass).getType();
	}

	public static Type listType(Class<?> elementClass) {
		return listTypeToken(elementClass).getType();
	}

	private static <K, V> TypeToken<Map<K, V>> mapTypeToken(Class<K> keyClass, Class<V> valueClass) {
		return mapTypeToken(TypeToken.of(keyClass), TypeToken.of(valueClass));
	}
	
	@SuppressWarnings("serial")
	private static <K, V> TypeToken<Map<K, V>> mapTypeToken(TypeToken<K> keyType, TypeToken<V> valueType) {
		return new TypeToken<Map<K, V>>() {}
		.where(new TypeParameter<K>() {}, keyType)
		.where(new TypeParameter<V>() {}, valueType);
	}

	private static <E> TypeToken<List<E>> listTypeToken(Class<E> elementClass) {
		return listTypeToken(TypeToken.of(elementClass));
	}
	
	@SuppressWarnings("serial")
	private static <E> TypeToken<List<E>> listTypeToken(TypeToken<E> elementType) {
		return new TypeToken<List<E>>() {}
		.where(new TypeParameter<E>() {}, elementType);
	}

	private LangUtils() {
	}
}
