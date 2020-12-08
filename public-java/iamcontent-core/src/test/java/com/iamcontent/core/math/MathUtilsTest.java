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

import static com.iamcontent.core.math.MathUtils.clamp;
import static com.iamcontent.core.math.MathUtils.linearConvert;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MathUtilsTest {

	private static final double TOLERANCE = 0.000001;
	
	@Test
	public void testClamp_int() {
		checkIntClamp1to3(1, 0);
		checkIntClamp1to3(1, 1);
		checkIntClamp1to3(2, 2);
		checkIntClamp1to3(3, 3);
		checkIntClamp1to3(3, 4);
	}

	private void checkIntClamp1to3(int expected, int in) {
		assertEquals(expected, clamp(in, 1, 3));
	}

	@Test
	public void testClamp_double() {
		checkDoubleClamp1to3(1, 0);
		checkDoubleClamp1to3(1, 1);
		checkDoubleClamp1to3(2, 2);
		checkDoubleClamp1to3(3, 3);
		checkDoubleClamp1to3(3, 4);
	}

	private void checkDoubleClamp1to3(double expected, double in) {
		assertExactlyEquals(expected, clamp(in, 1.0, 3.0));
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
