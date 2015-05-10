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
package com.iamcontent.core.math;

import static com.iamcontent.core.math.DoubleRange.NORMAL_RANGE;

import com.google.common.base.Converter;

/**
 * Converts Double objects linearly according to two corresponding {@link Range} objects.
 * @author Greg Elderfield
 */
public final class InterRangeDoubleConverter extends Converter<Double, Double> {

	/**
	 * Indicates whether a converted value should be limited to its target range or not.
	 */
	public static enum Mode {
		LIMIT_RESULT_TO_RANGE {
			@Override
			double limit(double d, DoubleRange r) {
				return r.limit(d);
			}
		}, 
		DO_NOT_LIMIT_RESULT_TO_RANGE {
			@Override
			double limit(double d, DoubleRange r) {
				return d;
			}
		};
		abstract double limit(double d, DoubleRange r);
	};
	
	private final DoubleRange fromRange, toRange;
	private final Mode mode;
	
	public InterRangeDoubleConverter(DoubleRange fromRange, DoubleRange toRange, Mode mode) {
		this.fromRange = fromRange;
		this.toRange = toRange;
		this.mode = mode;
	}

	public static InterRangeDoubleConverter fromNormalRangeConverter(DoubleRange toRange, Mode mode) {
		return interRangeConverter(NORMAL_RANGE, toRange, mode);
	}

	public static InterRangeDoubleConverter interRangeConverter(DoubleRange fromRange, DoubleRange toRange, Mode mode) {
		return new InterRangeDoubleConverter(fromRange, toRange, mode);
	}
	
	@Override
	protected Double doForward(Double v) {
		return convertAndLimit(v, fromRange, toRange);
	}

	@Override
	protected Double doBackward(Double v) {
		return convertAndLimit(v, toRange, fromRange);
	}

	private double convertAndLimit(double d, DoubleRange fromRange, DoubleRange toRange) {
		final double convertedButUnlimited = linearConvert(d, fromRange, toRange);
		return mode.limit(convertedButUnlimited, toRange);
	}

	private static double linearConvert(double v, DoubleRange inRange, DoubleRange outRange) {
		return MathUtils.linearConvert(v, inRange.getLimit1(), inRange.getLimit2(), outRange.getLimit1(), outRange.getLimit2());
	}
}
