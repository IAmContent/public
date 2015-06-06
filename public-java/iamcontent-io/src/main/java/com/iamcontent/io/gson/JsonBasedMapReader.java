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

import static com.iamcontent.core.gson.GsonUtils.JSON_FILE_EXTENSION;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.iamcontent.core.LangUtils;
import com.iamcontent.io.util.AbstractResourceReader;

/**
 * Creates {@link Map}s according to JSON file resources.
 * @author Greg Elderfield
 */
public class JsonBasedMapReader<K, V> extends AbstractResourceReader<Map<K, V>> {

	private final Type mapType;
	
	public JsonBasedMapReader(String folder, String name, Class<K> keyClass, Class<V> valueClass) {
		super(folder, name, JSON_FILE_EXTENSION);
		this.mapType = LangUtils.mapType(keyClass, valueClass);
	}

	public static <F, T> Map<F, T> map(String folder, String name, Class<F> keyClass, Class<T> valueClass) {
		return newInstance(folder, name, keyClass, valueClass).read();
	}

	@Override
	protected Map<K, V> readFrom(Reader r) {
		return gson().fromJson(r, mapType);
	}

	private static <F, T> JsonBasedMapReader<F, T> newInstance(String folder, String name, Class<F> keyClass, Class<T> valueClass) {
		return new JsonBasedMapReader<F, T>(folder, name, keyClass, valueClass);
	}
	
	private static Gson gson() {
		return new Gson();
	}
}
