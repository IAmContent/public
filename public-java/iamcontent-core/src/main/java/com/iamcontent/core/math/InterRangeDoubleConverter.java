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
	public static final ClampingMode DEFAULT_MODE = ClampingMode.CLAMPED;
	public static final InterRangeDoubleConverter REVERSE_NORMAL_CONVERTER = rangeFromNormalTo(REVERSE_NORMAL_RANGE);
	
	/**
	 * Indicates whether a converted value should be clamped to its target range or not.
	 */
	public static enum ClampingMode {
		CLAMPED {
			@Override
			double apply(double d, DoubleRange r) {
				return r.clamp(d);
			}
		}, 
		UNCLAMPED {
			@Override
			double apply(double d, DoubleRange r) {
				return d;
			}
		};
		abstract double apply(double d, DoubleRange r);
	};
	
	private final DoubleRange fromRange, toRange;
	private final ClampingMode clampingMode;

	public InterRangeDoubleConverter(DoubleRange fromRange, DoubleRange toRange) {
		this(fromRange, toRange, DEFAULT_MODE);
	}
	
	public InterRangeDoubleConverter(DoubleRange fromRange, DoubleRange toRange, ClampingMode clampingMode) {
		this.fromRange = fromRange;
		this.toRange = toRange;
		this.clampingMode = clampingMode;
	}

	public static InterRangeDoubleConverter rangeFromNormalTo(double toLimit1, double toLimit2) {
		return rangeFromNormalTo(range(toLimit1, toLimit2));
	}
	
	public static InterRangeDoubleConverter rangeFromNormalTo(DoubleRange toRange) {
		return interRangeConverter(NORMAL_RANGE, toRange, DEFAULT_MODE);
	}

	public static InterRangeDoubleConverter rangeFromNormalTo(DoubleRange toRange, ClampingMode clampingMode) {
		return interRangeConverter(NORMAL_RANGE, toRange, clampingMode);
	}

	public static InterRangeDoubleConverter interRangeConverter(DoubleRange fromRange, DoubleRange toRange) {
		return interRangeConverter(fromRange, toRange, DEFAULT_MODE);
	}

	public static InterRangeDoubleConverter interRangeConverter(DoubleRange fromRange, DoubleRange toRange, ClampingMode clampingMode) {
		return new InterRangeDoubleConverter(fromRange, toRange, clampingMode);
	}
	
	@Override
	protected Double doForward(Double v) {
		return convertAndClamp(v, fromRange, toRange);
	}

	@Override
	protected Double doBackward(Double v) {
		return convertAndClamp(v, toRange, fromRange);
	}

	private double convertAndClamp(double d, DoubleRange fromRange, DoubleRange toRange) {
		final double convertedButUnclamped = linearConvert(d, fromRange, toRange);
		return clampingMode.apply(convertedButUnclamped, toRange);
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

	public ClampingMode getClampingMode() {
		return clampingMode;
	}
}
