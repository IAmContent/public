/**
  IAmContent Public Libraries.
  Copyright (C) 2015 Greg Elderfield
  @author Greg Elderfield, iamcontent@jarchitect.co.uk
 
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
package com.iamcontent.core.gson;

import static com.iamcontent.core.gson.GsonUtils.getMemberAsDouble;

import java.lang.reflect.Type;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.iamcontent.core.math.DoubleRange;

/**
 * A Gson {@link JsonDeserializer} for {@link DoubleRange} objects.
 * @author Greg Elderfield
 */
public class DoubleRangeDeserializer implements JsonDeserializer<DoubleRange> {
	
	private static final DoubleRangeDeserializer INSTANCE = new DoubleRangeDeserializer();

	public DoubleRange deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		final double limit1 = getMemberAsDouble(json, "limit1");
		final double limit2 = getMemberAsDouble(json, "limit2");
		return new DoubleRange(limit1, limit2);
	}

	public static GsonBuilder register(GsonBuilder builder) {
		builder.registerTypeAdapter(DoubleRange.class, INSTANCE);
		return builder;
	}
}
