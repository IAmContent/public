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
import static com.iamcontent.core.gson.GsonUtils.getMember;
import static com.iamcontent.core.gson.GsonUtils.getMemberAsObject;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.iamcontent.device.servo.calibrate.DefaultingServoSourceCalibrator;
import com.iamcontent.device.servo.calibrate.ProportionalServoCalibrator;
import com.iamcontent.device.servo.calibrate.ServoCalibrator;

/**
 * An abstract Gson {@link JsonDeserializer} for {@link DefaultingServoSourceCalibrator} objects.
 * @author Greg Elderfield
 * 
 * @param C The type used to identify the channel of a servo. 
 */
public abstract class DefaultingServoSourceCalibratorDeserializer<C> implements JsonDeserializer<DefaultingServoSourceCalibrator<C>> {

	@Override
	public DefaultingServoSourceCalibrator<C> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		final ServoCalibrator defaultCalibrator = getMemberAsObject(json, "defaultCalibrator", context, ProportionalServoCalibrator.class);
		final Map<C, ServoCalibrator> perServoCalibrators = asMap(getArrayMember(json, "perServoCalibrators"), context);
		return new DefaultingServoSourceCalibrator<C>(defaultCalibrator, perServoCalibrators);
	}

	private Map<C, ServoCalibrator> asMap(JsonArray jsonArray, JsonDeserializationContext context) {
		if (jsonArray==null) 
			return Collections.emptyMap();
		
		final Map<C, ServoCalibrator> result = new HashMap<C, ServoCalibrator>(jsonArray.size());
		for (JsonElement e : jsonArray)
			addCalibrator(result, e, context);
		return result;
	}

	private void addCalibrator(Map<C, ServoCalibrator> result, JsonElement json, JsonDeserializationContext context) {
		final JsonElement member = getMember(json, "channel");
		final C channel = asChannel(member);
		final ServoCalibrator calibrator = getMemberAsObject(json, "calibrator", context, ProportionalServoCalibrator.class);
		result.put(channel, calibrator);
	}

	/**
	 * Extracts a channel identifier from the given Json element.
	 */
	protected abstract C asChannel(JsonElement e);
}
