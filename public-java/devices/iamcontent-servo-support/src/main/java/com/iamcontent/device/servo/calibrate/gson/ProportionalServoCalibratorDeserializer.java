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
package com.iamcontent.device.servo.calibrate.gson;

import static com.iamcontent.core.gson.GsonUtils.getMemberAsObject;

import java.lang.reflect.Type;

import com.google.common.base.Converter;
import com.google.common.base.Function;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.iamcontent.core.gson.InterRangeDoubleConverterDeserializer;
import com.iamcontent.core.math.InterRangeDoubleConverter;
import com.iamcontent.device.servo.calibrate.ProportionalServoCalibrator;

/**
 * A Gson {@link JsonDeserializer} for {@link ProportionalServoCalibrator} objects.
 * @author Greg Elderfield
 */
public class ProportionalServoCalibratorDeserializer implements JsonDeserializer<ProportionalServoCalibrator> {

	private static final ProportionalServoCalibratorDeserializer INSTANCE = new ProportionalServoCalibratorDeserializer();

	@Override
	public ProportionalServoCalibrator deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		final Converter<Double, Double> positionConverter = getMemberAsObject(json, "positionConverter", context, InterRangeDoubleConverter.class);
		final Function<Double, Double> speedConverter = getMemberAsObject(json, "speedConverter", context, InterRangeDoubleConverter.class);
		final Function<Double, Double> accelerationConverter = getMemberAsObject(json, "accelerationConverter", context, InterRangeDoubleConverter.class);
		return new ProportionalServoCalibrator(positionConverter, speedConverter, accelerationConverter);
	}

	public static GsonBuilder register(GsonBuilder builder) {
		builder.registerTypeAdapter(ProportionalServoCalibrator.class, INSTANCE);
		return builder;
	}
	
	public static GsonBuilder customGsonBuilder() {
		return register(InterRangeDoubleConverterDeserializer.customGsonBuilder());
	}
}
