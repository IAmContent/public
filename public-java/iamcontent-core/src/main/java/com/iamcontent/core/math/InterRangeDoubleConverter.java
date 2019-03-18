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
package com.iamcontent.core.math;

import static com.iamcontent.core.math.DoubleRange.NORMAL_RANGE;
import static com.iamcontent.core.math.DoubleRange.REVERSE_NORMAL_RANGE;
import static com.iamcontent.core.math.DoubleRange.range;

import com.google.common.base.Converter;
import com.google.common.base.Function;
import com.google.common.base.Functions;

/**
 * Converts Double objects linearly according to two corresponding {@link Range} objects.
 * @author Greg Elderfield
 */
public class InterRangeDoubleConverter extends Converter<Double, Double> {

	public static final Function<Double, Double> IDENTITY_FUNCTION = Functions.identity();
	public static final Mode DEFAULT_MODE = Mode.LIMIT_RESULT_TO_RANGE;
	public static final InterRangeDoubleConverter REVERSE_NORMAL_CONVERTER = rangeFromNormalTo(REVERSE_NORMAL_RANGE);
	
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

	public InterRangeDoubleConverter(DoubleRange fromRange, DoubleRange toRange) {
		this(fromRange, toRange, DEFAULT_MODE);
	}
	
	public InterRangeDoubleConverter(DoubleRange fromRange, DoubleRange toRange, Mode mode) {
		this.fromRange = fromRange;
		this.toRange = toRange;
		this.mode = mode;
	}

	public static InterRangeDoubleConverter rangeFromNormalTo(double toLimit1, double toLimit2) {
		return rangeFromNormalTo(range(toLimit1, toLimit2));
	}
	
	public static InterRangeDoubleConverter rangeFromNormalTo(DoubleRange toRange) {
		return interRangeConverter(NORMAL_RANGE, toRange, DEFAULT_MODE);
	}

	public static InterRangeDoubleConverter rangeFromNormalTo(DoubleRange toRange, Mode mode) {
		return interRangeConverter(NORMAL_RANGE, toRange, mode);
	}

	public static InterRangeDoubleConverter interRangeConverter(DoubleRange fromRange, DoubleRange toRange) {
		return interRangeConverter(fromRange, toRange, DEFAULT_MODE);
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

	public DoubleRange getFromRange() {
		return fromRange;
	}

	public DoubleRange getToRange() {
		return toRange;
	}

	public Mode getMode() {
		return mode;
	}
}
