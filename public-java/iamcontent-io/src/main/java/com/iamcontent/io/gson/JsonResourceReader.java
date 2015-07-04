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

import com.google.gson.Gson;
import com.iamcontent.io.util.AbstractResourceReader;

/**
 * An {@link AbstractResourceReader} for JSON file resources.
 * @author Greg Elderfield
 */
public class JsonResourceReader<T> extends AbstractResourceReader<T> {

	private final Type type;
	private final Gson gson;
	
	public JsonResourceReader(String name, Type type, Gson gson) {
		super(name, JSON_FILE_EXTENSION);
		this.type = type;
		this.gson = gson;
	}

	@Override
	protected T readFrom(Reader r) {
		return gson.fromJson(r, type);
	}
}
