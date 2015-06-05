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

import static com.iamcontent.core.gson.GsonUtils.JSON_FILE_EXTENSION;
import static com.iamcontent.device.servo.calibrate.gson.NumberedServoSourceCalibratorDeserializer.customGsonBuilder;

import java.io.Reader;

import com.google.gson.Gson;
import com.iamcontent.device.servo.calibrate.DefaultingServoSourceCalibrator;
import com.iamcontent.device.servo.calibrate.ServoSourceCalibrator;
import com.iamcontent.io.util.AbstractResourceReader;

/**
 * Creates {@link ServoSourceCalibrator} objects according to JSON file resources.
 * @author Greg Elderfield
 */
public class JsonBasedCalibratorReader extends AbstractResourceReader<ServoSourceCalibrator<Integer>> {

	private static final String CALIBRATION_FOLDER = "servo/calibration/";

	public JsonBasedCalibratorReader(String calibratorName) {
		super(CALIBRATION_FOLDER, calibratorName, JSON_FILE_EXTENSION);
	}

	public static ServoSourceCalibrator<Integer> numberedChannelCalibrator(String calibratorName) {
		return newInstance(calibratorName).read();
	}

	@Override
	@SuppressWarnings("unchecked")
	protected ServoSourceCalibrator<Integer> readFrom(Reader r) {
		return gson().fromJson(r, DefaultingServoSourceCalibrator.class);
	}

	private static JsonBasedCalibratorReader newInstance(String calibratorName) {
		return new JsonBasedCalibratorReader(calibratorName);
	}
	
	private static Gson gson() {
		return customGsonBuilder().create();
	}
}
