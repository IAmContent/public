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

import static com.iamcontent.core.math.InterRangeDoubleConverter.rangeFromNormalTo;
import static com.iamcontent.core.math.InterRangeDoubleConverter.REVERSE_NORMAL_CONVERTER;
import com.google.common.base.Converter;
import com.google.common.base.Function;

/**
 * An immutable {@link ServoCalibrator}.
 * @author Greg Elderfield
 */
public class ImmutableServoCalibrator implements ServoCalibrator {

	private final Converter<Double, Double> valueConverter;
	private final Function<Double, Double> speedConverter;
	private final Function<Double, Double> accelerationConverter;
	
	public static final ImmutableServoCalibrator NULL_SERVO_CALIBRATOR = new ImmutableServoCalibrator(null);
	public static final ImmutableServoCalibrator REVERSED_SERVO_CALIBRATOR = new ImmutableServoCalibrator(REVERSE_NORMAL_CONVERTER);

	public ImmutableServoCalibrator(Converter<Double, Double> valueConverter) {
		this(valueConverter, null, null);
	}
	
	public ImmutableServoCalibrator(Converter<Double, Double> valueConverter, Function<Double, Double> speedConverter,
			Function<Double, Double> accelerationConverter) {
		this.valueConverter = valueConverter;
		this.speedConverter = speedConverter;
		this.accelerationConverter = accelerationConverter;
	}
	
	public static ImmutableServoCalibrator calibrateValueFromNormalTo(double limit1, double limit2) {
		return new ImmutableServoCalibrator(rangeFromNormalTo(limit1, limit2));
	}

	@Override
	public Converter<Double, Double> getValueConverter() {
		return valueConverter;
	}

	@Override
	public Function<Double, Double> getSpeedConverter() {
		return speedConverter;
	}

	@Override
	public Function<Double, Double> getAccelerationConverter() {
		return accelerationConverter;
	}
}
