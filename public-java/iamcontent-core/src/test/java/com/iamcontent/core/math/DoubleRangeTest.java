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

import static com.iamcontent.core.math.DoubleRange.range;
import static com.iamcontent.core.math.MathUtilsTest.assertExactlyEquals;

import org.junit.Test;

public class DoubleRangeTest {
	
	private static final DoubleRange RANGE_1_TO_10 = range(1.0, 10.0);
	private static final DoubleRange RANGE_7_TO_3 = range(7.0, 3.0);

	@Test
	public void testGetters() {
		checkGetters(RANGE_1_TO_10, 1.0, 10.0, 1.0, 10.0);
		checkGetters(RANGE_7_TO_3, 7.0, 3.0, 3.0, 7.0);
	}

	private void checkGetters(DoubleRange range, double limit1, double limit2, double min, double max) {
		assertExactlyEquals(limit1, range.getLimit1());
		assertExactlyEquals(limit2, range.getLimit2());
		assertExactlyEquals(min, range.getMin());
		assertExactlyEquals(max, range.getMax());
	}

	@Test
	public void testLimit() {
		assertExactlyEquals(1.0, RANGE_1_TO_10.limit(0.0));
		assertExactlyEquals(1.0, RANGE_1_TO_10.limit(1.0));
		assertExactlyEquals(4.0, RANGE_1_TO_10.limit(4.0));
		assertExactlyEquals(10.0, RANGE_1_TO_10.limit(10.0));
		assertExactlyEquals(10.0, RANGE_1_TO_10.limit(11.0));
		
		assertExactlyEquals(3.0, RANGE_7_TO_3.limit(2.0));
		assertExactlyEquals(3.0, RANGE_7_TO_3.limit(3.0));
		assertExactlyEquals(6.0, RANGE_7_TO_3.limit(6.0));
		assertExactlyEquals(7.0, RANGE_7_TO_3.limit(7.0));
		assertExactlyEquals(7.0, RANGE_7_TO_3.limit(8.0));
	}
}
