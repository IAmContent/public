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

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.iamcontent.device.servo.Servo;

/**
 * A {@link Servo} that alters its parameter values according to its {@link ServoCalibrator} and invokes a delegate {@link Servo}
 * with the altered parameter values.
 * @author Greg Elderfield
 * 
 * @param <C> The type used to identify the channel of a servo. 
 */
public class CalibratedServo<C> implements Servo<C> {
	
	private static final Function<Double, Double> IDENTITY_FUNCTION = Functions.identity();
	
	private final Servo<C> delegateServo;
	private final Function<Double, Double> toDelegatePositionConverter;
	private final Function<Double, Double> fromDelegatePositionConverter;
	private final Function<Double, Double> toDelegateSpeedConverter;
	private final Function<Double, Double> toDelegateAccelerationConverter;
	
	public CalibratedServo(Servo<C> delegateServo, ServoCalibrator calibrator) {
		checkArguments(delegateServo, calibrator);
		this.delegateServo = delegateServo;
		this.toDelegatePositionConverter = identityIfNull(calibrator.getPositionConverter());
		this.fromDelegatePositionConverter = identityIfNull(calibrator.getPositionConverter().reverse());
		this.toDelegateSpeedConverter = identityIfNull(calibrator.getSpeedConverter());
		this.toDelegateAccelerationConverter = identityIfNull(calibrator.getAccelerationConverter());
	}

	@Override
	public C getChannelId() {
		return delegateServo.getChannelId();
	}
	
	@Override
	public void setPosition(double position) {
		delegateServo.setPosition(toDelegatePosition(position));
	}

	private double toDelegatePosition(double position) {
		return toDelegatePositionConverter.apply(position);
	}

	@Override
	public double getPosition() {
		return fromDelegatePosition(delegateServo.getPosition());
	}
	
	private double fromDelegatePosition(double position) {
		return fromDelegatePositionConverter.apply(position);
	}

	@Override
	public void setSpeed(double speed) {
		if (toDelegateSpeedConverter!=null)
			delegateServo.setSpeed(toDelegateSpeed(speed));
	}

	private double toDelegateSpeed(double speed) {
		return toDelegateSpeedConverter.apply(speed);
	}

	@Override
	public void setAcceleration(double acceleration) {
		if (toDelegateAccelerationConverter!=null)
			delegateServo.setAcceleration(toDelegateAcceleration(acceleration));
	}
	
	private double toDelegateAcceleration(double acceleration) {
		return toDelegateAccelerationConverter.apply(acceleration);
	}

	private static void checkArguments(Servo<?> delegateServo, ServoCalibrator calibrator) {
		checkNotNull(delegateServo, "Delegate servo cannot be null.");
		checkNotNull(calibrator, "Calibrator cannot be null.");
		checkNotNull(calibrator.getPositionConverter(), "PositionConverter of calibrator cannot be null.");
	}

	private static Function<Double, Double> identityIfNull(Function<Double, Double> f) {
		return f==null ? IDENTITY_FUNCTION : f;
	}
}
