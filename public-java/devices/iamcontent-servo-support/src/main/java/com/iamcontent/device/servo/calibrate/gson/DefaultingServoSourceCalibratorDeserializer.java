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

import static com.iamcontent.core.gson.GsonUtils.getArrayMember;
import static com.iamcontent.core.gson.GsonUtils.getMemberAsInt;
import static com.iamcontent.core.gson.GsonUtils.getMemberAsObject;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.iamcontent.device.servo.calibrate.DefaultingServoSourceCalibrator;
import com.iamcontent.device.servo.calibrate.ProportionalServoCalibrator;
import com.iamcontent.device.servo.calibrate.ServoCalibrator;

/**
 * A Gson {@link JsonDeserializer} for {@link DefaultingServoSourceCalibrator} objects.
 * @author Greg Elderfield
 */
public class DefaultingServoSourceCalibratorDeserializer implements JsonDeserializer<DefaultingServoSourceCalibrator> {

	private static final DefaultingServoSourceCalibratorDeserializer INSTANCE = new DefaultingServoSourceCalibratorDeserializer();

	@Override
	public DefaultingServoSourceCalibrator deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		final ServoCalibrator defaultCalibrator = getMemberAsObject(json, "defaultCalibrator", context, ProportionalServoCalibrator.class);
		final Map<Object, ServoCalibrator> perServoCalibrators = asMap(getArrayMember(json, "perServoCalibrators"), context);
		return new DefaultingServoSourceCalibrator(defaultCalibrator, perServoCalibrators);
	}

	private Map<Object, ServoCalibrator> asMap(JsonArray jsonArray, JsonDeserializationContext context) {
		if (jsonArray==null) 
			return Collections.emptyMap();
		
		final Map<Object, ServoCalibrator> result = new HashMap<Object, ServoCalibrator>(jsonArray.size());
		for (JsonElement e : jsonArray)
			addCalibrator(result, e, context);
		return result;
	}

	private void addCalibrator(Map<Object, ServoCalibrator> result, JsonElement json, JsonDeserializationContext context) {
		final int channel = getMemberAsInt(json, "channel");
		final ServoCalibrator calibrator = getMemberAsObject(json, "calibrator", context, ProportionalServoCalibrator.class);
		result.put(channel, calibrator);
	}

	public static GsonBuilder register(GsonBuilder builder) {
		builder.registerTypeAdapter(DefaultingServoSourceCalibrator.class, INSTANCE);
		return builder;
	}
	
	public static GsonBuilder customGsonBuilder() {
		return register(ProportionalServoCalibratorDeserializer.customGsonBuilder());
	}
}
