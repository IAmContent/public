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
package com.iamcontent.io.gson;

import static com.iamcontent.io.gson.GsonUtils.JSON_FILE_EXTENSION;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.gson.Gson;
import com.iamcontent.core.LangUtils;
import com.iamcontent.io.util.AbstractResourceReader;

/**
 * Creates {@link Map}s according to JSON file resources.
 * @author Greg Elderfield
 */
public class JsonBasedMapReader<K, V> extends AbstractResourceReader<Map<K, V>> {

	private final Type mapType;
	private final Gson gson;

	public JsonBasedMapReader(String name, Class<K> keyClass, Class<V> valueClass, Gson gson) {
		super(name, JSON_FILE_EXTENSION);
		this.mapType = LangUtils.mapType(keyClass, valueClass);
		this.gson = gson;
	}

	public static <F, T> Map<F, T> map(String name, Class<F> keyClass, Class<T> valueClass) {
		return map(name, keyClass, valueClass, new Gson());
	}

	public static <F, T> Map<F, T> map(String name, Class<F> keyClass, Class<T> valueClass, Gson gson) {
		return newInstance(name, keyClass, valueClass, gson).read();
	}

	public static <F, T> Function<F, T> function(String name, Class<F> fromClass, Class<T> toClass) {
		return function(name, fromClass, toClass, new Gson());
	}

	public static <F, T> Function<F, T> function(String name, Class<F> fromClass, Class<T> toClass, Gson gson) {
		final Map<F, T> map = map(name, fromClass, toClass, gson);
		return Functions.forMap(map);
	}
	
	@Override
	protected Map<K, V> readFrom(Reader r) {
		return gson.fromJson(r, mapType);
	}

	private static <F, T> JsonBasedMapReader<F, T> newInstance(String name, Class<F> keyClass, Class<T> valueClass, Gson gson) {
		return new JsonBasedMapReader<F, T>(name, keyClass, valueClass, gson);
	}
}
