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

import java.io.Serializable;

/**
 * A limited numerical range.
 * @author Greg Elderfield
 */
public class DoubleRange implements Serializable {
	private final double limit1, limit2, min, max;
	
	public static final DoubleRange NORMAL_RANGE = range(0.0, 1.0);
	
	public DoubleRange(double limit1, double limit2) {
		this.limit1 = limit1;
		this.limit2 = limit2;
		this.min = Math.min(limit1, limit2);
		this.max = Math.max(limit1, limit2);;
	}

	public static DoubleRange range(double limit1, double limit2) {
		return new DoubleRange(limit1, limit2);
	}
	
	public double getLimit1() {
		return limit1;
	}

	public double getLimit2() {
		return limit2;
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}
	
	public double limit(double d) {
		return MathUtils.limit(d, min, max);
	}

	@Override
	public String toString() {
		return limit1 + ".." + limit2;
	}

	private static final long serialVersionUID = 1L;
}
