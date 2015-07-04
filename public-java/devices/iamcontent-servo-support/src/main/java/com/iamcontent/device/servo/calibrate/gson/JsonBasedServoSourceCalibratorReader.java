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

import static com.iamcontent.device.servo.calibrate.gson.DefaultingServoSourceCalibratorDeserializer.defaultingServoSourceCalibratorGsonBuilder;

import com.google.gson.Gson;
import com.iamcontent.device.servo.Servo;
import com.iamcontent.device.servo.calibrate.DefaultingServoSourceCalibrator;
import com.iamcontent.device.servo.calibrate.ProportionalServoCalibrator;
import com.iamcontent.device.servo.calibrate.ServoCalibrator;
import com.iamcontent.device.servo.calibrate.ServoSourceCalibrator;
import com.iamcontent.io.gson.JsonResourceReader;

/**
 * Creates {@link ServoSourceCalibrator} objects according to JSON file resources.
 * @author Greg Elderfield
 * 
 * @param <C> The type used to identify the channel of a servo.
 * @param <S> The type of the {@link ServoCalibrator} used for each {@link Servo}.
 */
public class JsonBasedServoSourceCalibratorReader<C, S extends ServoCalibrator>  extends JsonResourceReader<ServoSourceCalibrator<C>> {

	public JsonBasedServoSourceCalibratorReader(String calibratorName, Class<C> channelClass, Class<S> servoCalibratorClass) {
		super(calibratorName, DefaultingServoSourceCalibrator.class, gson(channelClass, servoCalibratorClass));
	}

	public static ServoSourceCalibrator<Integer> numberedChannelCalibrator(String calibratorName) {
		return read(calibratorName, Integer.class);
	}

	public static <C> ServoSourceCalibrator<C> read(String calibratorName, Class<C> channelClass) {
		return read(calibratorName, channelClass, ProportionalServoCalibrator.class);
	}

	public static <C, S extends ServoCalibrator> ServoSourceCalibrator<C> read(String calibratorName, Class<C> channelClass, Class<S> servoCalibratorClass) {
		return newInstance(calibratorName, channelClass, servoCalibratorClass).read();
	}

	private static <C, S extends ServoCalibrator> JsonBasedServoSourceCalibratorReader<C, S> newInstance(String calibratorName, Class<C> channelClass, Class<S> servoCalibratorClass) {
		return new JsonBasedServoSourceCalibratorReader<C, S>(calibratorName, channelClass, servoCalibratorClass);
	}
	
	private static <C, S extends ServoCalibrator> Gson gson(Class<C> channelClass, Class<S> servoCalibratorClass) {
		return defaultingServoSourceCalibratorGsonBuilder(channelClass, servoCalibratorClass).create();
	}
}
