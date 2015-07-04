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

import static com.iamcontent.device.servo.calibrate.gson.ProportionalServoCalibratorDeserializer.proportionalServoCalibratorGsonBuilder;
import static com.iamcontent.io.gson.GsonUtils.getMemberAsObject;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.iamcontent.core.LangUtils;
import com.iamcontent.device.servo.Servo;
import com.iamcontent.device.servo.calibrate.DefaultingServoSourceCalibrator;
import com.iamcontent.device.servo.calibrate.ProportionalServoCalibrator;
import com.iamcontent.device.servo.calibrate.ServoCalibrator;

/**
 * A Gson {@link JsonDeserializer} for {@link DefaultingServoSourceCalibrator} objects.
 * @author Greg Elderfield
 * 
 * @param <C> The type used to identify the channel of a servo.
 * @param <S> The type of the {@link ServoCalibrator} used for each {@link Servo}.
 */
public class DefaultingServoSourceCalibratorDeserializer<C, S extends ServoCalibrator> implements JsonDeserializer<DefaultingServoSourceCalibrator<C>> {

	private final Type mapType;
	
	public DefaultingServoSourceCalibratorDeserializer(Class<C> channelClass, Class<S> servoCalibratorClass) {
		this.mapType = LangUtils.mapType(channelClass, servoCalibratorClass);
	}

	@Override
	public DefaultingServoSourceCalibrator<C> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		final ServoCalibrator defaultCalibrator = getMemberAsObject(json, "defaultCalibrator", context, ProportionalServoCalibrator.class);
		final Map<C, ServoCalibrator> perServoCalibrators = getMemberAsObject(json, "perServoCalibrators", context, mapType, emptyMap());
		return new DefaultingServoSourceCalibrator<C>(defaultCalibrator, perServoCalibrators);
	}

	private Map<C, ServoCalibrator> emptyMap() {
		return Collections.<C, ServoCalibrator>emptyMap();
	}

	public static <C, S extends ServoCalibrator> GsonBuilder defaultingServoSourceCalibratorGsonBuilder(Class<C> channelClass, Class<S> servoCalibratorClass) {
		return register(proportionalServoCalibratorGsonBuilder(), channelClass, servoCalibratorClass);
	}
	
	public static <C, S extends ServoCalibrator> GsonBuilder register(GsonBuilder builder, Class<C> channelClass, Class<S> servoCalibratorClass) {
		return builder.registerTypeAdapter(DefaultingServoSourceCalibrator.class, newInstance(channelClass, servoCalibratorClass));
	}

	private static <C, S extends ServoCalibrator> DefaultingServoSourceCalibratorDeserializer<C, S> newInstance(Class<C> channelClass, Class<S> servoCalibratorClass) {
		return new DefaultingServoSourceCalibratorDeserializer<C, S>(channelClass, servoCalibratorClass);
	}
}
