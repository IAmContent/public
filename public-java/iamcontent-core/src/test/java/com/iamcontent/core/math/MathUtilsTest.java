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

import static com.iamcontent.core.math.MathUtils.limit;
import static com.iamcontent.core.math.MathUtils.linearConvert;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MathUtilsTest {

	private static final double TOLERANCE = 0.000001;
	
	@Test
	public void testLimit_int() {
		checkIntLimit1to3(1, 0);
		checkIntLimit1to3(1, 1);
		checkIntLimit1to3(2, 2);
		checkIntLimit1to3(3, 3);
		checkIntLimit1to3(3, 4);
	}

	private void checkIntLimit1to3(int expected, int in) {
		assertEquals(expected, limit(in, 1, 3));
	}

	@Test
	public void testLimit_double() {
		checkDoubleLimit1to3(1, 0);
		checkDoubleLimit1to3(1, 1);
		checkDoubleLimit1to3(2, 2);
		checkDoubleLimit1to3(3, 3);
		checkDoubleLimit1to3(3, 4);
	}

	private void checkDoubleLimit1to3(double expected, double in) {
		assertExactlyEquals(expected, limit(in, 1.0, 3.0));
	}

	@Test
	public void testLinearConvert() {
		checkLinearConvert(10.0, 0.0);
		checkLinearConvert(20.0, 1.0);
		checkLinearConvert(25.0, 1.5);
		checkLinearConvert(30.0, 2.0);
		checkLinearConvert(40.0, 3.0);
	}

	private void checkLinearConvert(double expected, double in) {
		assertCloseEnough(expected, linearConvert(in, 1.0, 2.0, 20.0, 30.0));
		assertCloseEnough(expected, linearConvert(-in, -1.0, -2.0, 20.0, 30.0));
	}

	public static void assertExactlyEquals(double expected, double actual) {
		assertEquals(expected, actual, 0.0);
	}

	public static void assertCloseEnough(double expected, double actual) {
		assertEquals(expected, actual, TOLERANCE);
	}
}
