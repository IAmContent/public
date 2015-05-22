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
import static com.iamcontent.device.servo.calibrate.gson.DefaultingServoSourceCalibratorDeserializer.customGsonBuilder;
import static com.iamcontent.io.util.ResourceUtils.getStreamOrThrow;
import static com.iamcontent.io.util.Readers.inputStreamReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import com.google.gson.Gson;
import com.iamcontent.device.servo.calibrate.DefaultingServoSourceCalibrator;
import com.iamcontent.device.servo.calibrate.ServoSourceCalibrator;
import com.iamcontent.io.IORuntimeException;

/**
 * Creates {@link ServoSourceCalibrator} objects according to JSON file resources.
 * @author Greg Elderfield
 */
public class JsonBasedCalibratorReader {

	private static final String CALIBRATION_FOLDER = "servo/calibration/";

	public static ServoSourceCalibrator calibrator(String calibratorName) {
		try (final Reader r = readerFor(calibratorName) ) {
			return calibrator(r);
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}

	public static ServoSourceCalibrator calibrator(Reader r) {
		return gson().fromJson(r, DefaultingServoSourceCalibrator.class);
	}

	private static Reader readerFor(String calibratorName) {
		return inputStreamReader(streamFor(calibratorName));
	}

	private static InputStream streamFor(String calibratorName) {
		return getStreamOrThrow(resourceName(calibratorName));
	}

	private static String resourceName(String calibratorName) {
		return CALIBRATION_FOLDER + calibratorName + JSON_FILE_EXTENSION;
	}

	private static Gson gson() {
		return customGsonBuilder().create();
	}
}
