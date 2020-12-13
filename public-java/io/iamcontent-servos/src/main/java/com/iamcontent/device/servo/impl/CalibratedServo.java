/**
  IAmContent Public Libraries.
  Copyright (C) 2015-2021 Greg Elderfield
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
package com.iamcontent.device.servo.impl;

import com.iamcontent.core.math.MutableDouble;
import com.iamcontent.device.io.analog.impl.CalibratedAnalogIO;
import com.iamcontent.device.servo.Servo;
import com.iamcontent.device.servo.ServoCalibration;

/**
 * A MutableDouble that is a calibrated proxy for another (target) MutableDouble.
 * @author Greg Elderfield
 */
public class CalibratedServo extends CalibratedAnalogIO implements Servo {
	
	public CalibratedServo(Servo target, ServoCalibration calibration) {
		super(target, calibration);
	}
	
	@Override
	public MutableDouble value() {
		return delegate().value().calibrated(calibration().value());
	}

	@Override
	public MutableDouble speed() {
		return delegate().speed().calibrated(calibration().speed());
	}

	@Override
	public MutableDouble acceleration() {
		return delegate().acceleration().calibrated(calibration().acceleration());
	}

	@Override
	protected ServoCalibration calibration() {
		return (ServoCalibration) super.calibration();
	}

	@Override
	protected Servo delegate() {
		return (Servo) super.delegate();
	}
}
