package com.iamcontent.device.servo.impl;

import static com.iamcontent.core.math.InterRangeDoubleConverter.REVERSE_NORMAL_CONVERTER;

import com.iamcontent.core.math.DoubleConverter;
import com.iamcontent.core.math.InterRangeDoubleConverter;
import com.iamcontent.device.servo.ServoCalibration;

/**
 * Defines an immutable {@link ServoCalibration}.
 * @author Greg Elderfield
 */
public class ImmutableServoCalibration implements ServoCalibration {

	private final DoubleConverter value, speed, acceleration;

	public static final ServoCalibration REVERSE_NORMAL_SERVO_CALIBRATION = new ImmutableServoCalibration(REVERSE_NORMAL_CONVERTER);

	public ImmutableServoCalibration(DoubleConverter value, DoubleConverter speed, DoubleConverter acceleration) {
		this.value = value;
		this.speed = speed;
		this.acceleration = acceleration;
	}

	public ImmutableServoCalibration(DoubleConverter value, DoubleConverter speed) {
		this(value, speed, DoubleConverter.IDENTITY);
	}

	public ImmutableServoCalibration(DoubleConverter value) {
		this(value, DoubleConverter.IDENTITY);
	}

	@Override
	public DoubleConverter value() {
		return value;
	}

	@Override
	public DoubleConverter speed() {
		return speed;
	}

	@Override
	public DoubleConverter acceleration() {
		return acceleration;
	}

	public static ImmutableServoCalibration calibrateValueFromNormalTo(double limit1, double limit2) {
		return new ImmutableServoCalibration(InterRangeDoubleConverter.converterFromNormalTo(limit1, limit2));
	}
}
