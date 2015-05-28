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
package com.iamcontent.device.servo.calibrate;

import static com.iamcontent.device.servo.calibrate.Calibrators.CALIBRATION_PROPERTY_KEY;
import static com.iamcontent.device.servo.calibrate.Calibrators.DEFAULT_CALIBRATION_NAME;
import static com.iamcontent.device.servo.calibrate.Calibrators.numberedChannelCalibrator;
import static com.iamcontent.device.servo.calibrate.Calibrators.defaultNumberedChannelCalibrator;
import static com.iamcontent.device.servo.calibrate.gson.JsonBasedCalibratorReaderTest.PER_SERVO_EXAMPLE_CALIBRATION_NAME;
import static com.iamcontent.device.servo.calibrate.gson.JsonBasedCalibratorReaderTest.checkDefaultCalibrator;
import static com.iamcontent.device.servo.calibrate.gson.JsonBasedCalibratorReaderTest.checkPerServoExampleCalibrator;

import org.junit.Test;

public class CalibratorsTest {

	@Test
	public void testDefaultCalibrator() {
		checkDefaultCalibrator(defaultNumberedChannelCalibrator());
		
		System.setProperty(CALIBRATION_PROPERTY_KEY, PER_SERVO_EXAMPLE_CALIBRATION_NAME);
		checkPerServoExampleCalibrator(defaultNumberedChannelCalibrator());
		
		System.setProperty(CALIBRATION_PROPERTY_KEY, DEFAULT_CALIBRATION_NAME);
		checkDefaultCalibrator(defaultNumberedChannelCalibrator());
	}

	@Test
	public void testCalibrator() {
		checkPerServoExampleCalibrator(numberedChannelCalibrator(PER_SERVO_EXAMPLE_CALIBRATION_NAME));
	}
}
