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

import static com.iamcontent.core.math.InterRangeDoubleConverter.fromNormalRangeConverter;

import com.google.common.base.Converter;
import com.google.common.base.Function;
import com.iamcontent.core.math.DoubleRange;
import com.iamcontent.core.math.InterRangeDoubleConverter.Mode;

/**
 * A {@link ServoCalibrator} that proportionally scales its input values to create its output values.
 * 
 * @author Greg Elderfield
 */
public class ProportionalServoCalibrator extends ImmutableServoCalibrator {

	public ProportionalServoCalibrator(DoubleRange positionOutputRange, DoubleRange speedOutputRange, DoubleRange accelerationOutputRange) {
		super(normalConverter(positionOutputRange), normalConverter(speedOutputRange), normalConverter(accelerationOutputRange));
	}

	public ProportionalServoCalibrator(Converter<Double, Double> positionConverter, Function<Double, Double> speedConverter,
			Function<Double, Double> accelerationConverter) {
		super(positionConverter, speedConverter, accelerationConverter);
	}

	private static Converter<Double, Double> normalConverter(DoubleRange r) {
		return fromNormalRangeConverter(r, Mode.LIMIT_RESULT_TO_RANGE);
	}
}
