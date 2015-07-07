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
package com.iamcontent.device.io.analog.calibrate.gson;

import static com.iamcontent.device.io.analog.calibrate.gson.ProportionalAnalogIOCalibratorDeserializer.proportionalAnalogIOCalibratorGsonBuilder;
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
import com.iamcontent.device.io.analog.AnalogIO;
import com.iamcontent.device.io.analog.calibrate.DefaultingAnalogIOSourceCalibrator;
import com.iamcontent.device.io.analog.calibrate.ProportionalAnalogIOCalibrator;
import com.iamcontent.device.io.analog.calibrate.AnalogIOCalibrator;

/**
 * A Gson {@link JsonDeserializer} for {@link DefaultingAnalogIOSourceCalibrator} objects.
 * @author Greg Elderfield
 * 
 * @param <C> The type used to identify the channel of a AnalogIO.
 * @param <S> The type of the {@link AnalogIOCalibrator} used for each {@link AnalogIO}.
 */
public class DefaultingAnalogIOSourceCalibratorDeserializer<C, S extends AnalogIOCalibrator> implements JsonDeserializer<DefaultingAnalogIOSourceCalibrator<C>> {

	private final Type mapType;
	
	public DefaultingAnalogIOSourceCalibratorDeserializer(Class<C> channelClass, Class<S> AnalogIOCalibratorClass) {
		this.mapType = LangUtils.mapType(channelClass, AnalogIOCalibratorClass);
	}

	@Override
	public DefaultingAnalogIOSourceCalibrator<C> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		final AnalogIOCalibrator defaultCalibrator = getMemberAsObject(json, "defaultCalibrator", context, ProportionalAnalogIOCalibrator.class);
		final Map<C, AnalogIOCalibrator> perChannelCalibrators = getMemberAsObject(json, "perChannelCalibrators", context, mapType, emptyMap());
		return new DefaultingAnalogIOSourceCalibrator<C>(defaultCalibrator, perChannelCalibrators);
	}

	private Map<C, AnalogIOCalibrator> emptyMap() {
		return Collections.<C, AnalogIOCalibrator>emptyMap();
	}

	public static <C, S extends AnalogIOCalibrator> GsonBuilder defaultingAnalogIOSourceCalibratorGsonBuilder(Class<C> channelClass, Class<S> AnalogIOCalibratorClass) {
		return register(proportionalAnalogIOCalibratorGsonBuilder(), channelClass, AnalogIOCalibratorClass);
	}
	
	public static <C, S extends AnalogIOCalibrator> GsonBuilder register(GsonBuilder builder, Class<C> channelClass, Class<S> AnalogIOCalibratorClass) {
		return builder.registerTypeAdapter(DefaultingAnalogIOSourceCalibrator.class, newInstance(channelClass, AnalogIOCalibratorClass));
	}

	private static <C, S extends AnalogIOCalibrator> DefaultingAnalogIOSourceCalibratorDeserializer<C, S> newInstance(Class<C> channelClass, Class<S> AnalogIOCalibratorClass) {
		return new DefaultingAnalogIOSourceCalibratorDeserializer<C, S>(channelClass, AnalogIOCalibratorClass);
	}
}
