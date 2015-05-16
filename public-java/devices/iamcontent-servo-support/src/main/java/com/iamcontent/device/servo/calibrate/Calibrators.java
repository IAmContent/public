/**
  IAmContent Public Libraries.
  Copyright (C) 2015 Greg Elderfield
  @author Greg Elderfield, iamcontent@jarchitect.co.uk
 
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
package com.iamcontent.device.servo.calibrate;

import com.iamcontent.device.servo.calibrate.gson.JsonBasedCalibratorReader;

/**
 * A source of {@link ServoSourceCalibrator} objects.
 * @author Greg Elderfield
 */
public class Calibrators {
	public static final String CALIBRATION_PROPERTY_KEY = "iamcontent.servo.calibration";
	public static final String DEFAULT_CALIBRATION_NAME = "default";
	
	public static ServoSourceCalibrator defaultCalibrator() {
		return calibrator(defaultCalibrationName());
	}
	
	private static String defaultCalibrationName() {
		return System.getProperty(CALIBRATION_PROPERTY_KEY, DEFAULT_CALIBRATION_NAME);
	}

	public static ServoSourceCalibrator calibrator(String calibratorName) {
		return JsonBasedCalibratorReader.calibrator(calibratorName);
	}
}
