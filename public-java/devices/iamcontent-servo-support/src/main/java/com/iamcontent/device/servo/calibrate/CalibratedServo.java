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

import com.google.common.base.Function;
import com.iamcontent.device.analog.inout.CalibratedAnalogIO;
import com.iamcontent.device.servo.Servo;

/**
 * A {@link Servo} that alters its parameter values according to its {@link ServoCalibrator} and invokes a delegate {@link Servo}
 * with the altered parameter values.
 * @author Greg Elderfield
 */
public class CalibratedServo extends CalibratedAnalogIO implements Servo {
	
	private final Function<Double, Double> toDelegateSpeedConverter;
	private final Function<Double, Double> toDelegateAccelerationConverter;
	
	public CalibratedServo(Servo delegateServo, ServoCalibrator calibrator) {
		super(delegateServo, calibrator.getPositionConverter());
		this.toDelegateSpeedConverter = identityIfNull(calibrator.getSpeedConverter());
		this.toDelegateAccelerationConverter = identityIfNull(calibrator.getAccelerationConverter());
	}

	@Override
	public void setSpeed(double speed) {
		servo().setSpeed(toDelegateSpeed(speed));
	}

	private double toDelegateSpeed(double speed) {
		return toDelegateSpeedConverter.apply(speed);
	}

	@Override
	public void setAcceleration(double acceleration) {
		servo().setAcceleration(toDelegateAcceleration(acceleration));
	}

	private double toDelegateAcceleration(double acceleration) {
		return toDelegateAccelerationConverter.apply(acceleration);
	}
	
	private Servo servo() {
		return (Servo) delegate();
	}
}
