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

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.iamcontent.device.servo.calibrate.DefaultingServoSourceCalibrator;

/**
 * A Gson {@link JsonDeserializer} for {@link DefaultingServoSourceCalibrator} objects with Integer channel type.
 * @author Greg Elderfield
 */
public class NumberedServoSourceCalibratorDeserializer extends DefaultingServoSourceCalibratorDeserializer<Integer> {

	private static final NumberedServoSourceCalibratorDeserializer INSTANCE = new NumberedServoSourceCalibratorDeserializer();

	@Override
	protected Integer asChannel(JsonElement e) {
		return e.getAsInt();
	}

	public static GsonBuilder register(GsonBuilder builder) {
		builder.registerTypeAdapter(DefaultingServoSourceCalibrator.class, INSTANCE);
		return builder;
	}
	
	public static GsonBuilder customGsonBuilder() {
		return register(ProportionalServoCalibratorDeserializer.customGsonBuilder());
	}
}
