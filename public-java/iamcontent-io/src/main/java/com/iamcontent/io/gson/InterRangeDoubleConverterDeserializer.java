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

import static com.iamcontent.io.gson.DoubleRangeDeserializer.doubleRangeGsonBuilder;
import static com.iamcontent.io.gson.GsonUtils.getMemberAsObject;
import static com.iamcontent.io.gson.GsonUtils.getMemberAsString;

import java.lang.reflect.Type;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.iamcontent.core.math.DoubleRange;
import com.iamcontent.core.math.InterRangeDoubleConverter;
import com.iamcontent.core.math.InterRangeDoubleConverter.Mode;

/**
 * A Gson {@link JsonDeserializer} for {@link InterRangeDoubleConverter} objects.
 * @author Greg Elderfield
 */
public class InterRangeDoubleConverterDeserializer implements JsonDeserializer<InterRangeDoubleConverter> {

	private static final InterRangeDoubleConverterDeserializer INSTANCE = new InterRangeDoubleConverterDeserializer();

	@Override
	public InterRangeDoubleConverter deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		final DoubleRange fromRange = getMemberAsObject(json, "fromRange", context, DoubleRange.class, DoubleRange.NORMAL_RANGE);
		final DoubleRange toRange = getMemberAsObject(json, "toRange", context, DoubleRange.class, DoubleRange.NORMAL_RANGE);
		final Mode mode = Mode.valueOf(getMemberAsString(json, "mode", Mode.LIMIT_RESULT_TO_RANGE.name()));
		return new InterRangeDoubleConverter(fromRange, toRange, mode);
	}

	public static GsonBuilder register(GsonBuilder builder) {
		return builder.registerTypeAdapter(InterRangeDoubleConverter.class, INSTANCE);
	}

	public static GsonBuilder interRangeDoubleConverterGsonBuilder() {
		return register(doubleRangeGsonBuilder());
	}
}
