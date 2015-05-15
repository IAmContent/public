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

import com.iamcontent.core.math.DoubleRange;

/**
 * A source of {@link ServoSourceCalibrator} objects.
 * @author Greg Elderfield
 */
public class Calibrators {
	public static ServoSourceCalibrator defaultCalibrator() {
		return calibrator("default");
	}
	
	public static ServoSourceCalibrator calibrator(String calibratorName) {
		return new DefaultingServoSourceCalibrator(defaultServoCalibrator()); // TODO: Read from JSON resource
	}
	
	private static ServoCalibrator defaultServoCalibrator() {
		return new ProportionalServoCalibrator(defaultPositionOutputRange(), defaultSpeedOutputRange(), defaultAccelerationOutputRange());
	}

	private static DoubleRange defaultPositionOutputRange() {
		return new DoubleRange(4000, 8000); // TODO: Remove Pololu-specific values and add configuration mechanism.
	}
	
	private static DoubleRange defaultSpeedOutputRange() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static DoubleRange defaultAccelerationOutputRange() {
		// TODO Auto-generated method stub
		return null;
	}
}
