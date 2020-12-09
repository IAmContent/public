/**
  IAmContent Public Libraries.
  Copyright (C) 2015-2021 Greg Elderfield
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

import static com.iamcontent.core.math.InterRangeDoubleConverter.ClampingMode.UNCLAMPED;
import static com.iamcontent.core.math.InterRangeDoubleConverter.ClampingMode.CLAMPED;
import static com.iamcontent.core.math.MathUtilsTest.assertCloseEnough;

import org.junit.Test;

public class InterRangeDoubleConverterTest {

	private static final DoubleRange RANGE_1 = new DoubleRange(2, 3);
	private static final DoubleRange RANGE_2 = new DoubleRange(-20, -30);
	
	@Test
	public void testDoForward_clamped() {
		final InterRangeDoubleConverter c = new InterRangeDoubleConverter(RANGE_1, RANGE_2, CLAMPED);
		assertCloseEnough(-20.0, c.convert(1.0));
		assertCloseEnough(-20.0, c.convert(2.0));
		assertCloseEnough(-25.0, c.convert(2.5));
		assertCloseEnough(-30.0, c.convert(3.0));
		assertCloseEnough(-30.0, c.convert(4.0));
	}
	
	@Test
	public void testDoForward_unclamped() {
		final InterRangeDoubleConverter c = new InterRangeDoubleConverter(RANGE_1, RANGE_2, UNCLAMPED);
		assertCloseEnough(-10.0, c.convert(1.0));
		assertCloseEnough(-20.0, c.convert(2.0));
		assertCloseEnough(-25.0, c.convert(2.5));
		assertCloseEnough(-30.0, c.convert(3.0));
		assertCloseEnough(-40.0, c.convert(4.0));
	}
	
	@Test
	public void testDoBackward_clamped() {
		final InterRangeDoubleConverter c = new InterRangeDoubleConverter(RANGE_1, RANGE_2, CLAMPED);
		assertCloseEnough(2.0, c.doBackward(-10.0));
		assertCloseEnough(2.0, c.doBackward(-20.0));
		assertCloseEnough(2.5, c.doBackward(-25.0));
		assertCloseEnough(3.0, c.doBackward(-30.0));
		assertCloseEnough(3.0, c.doBackward(-40.0));
	}
	
	@Test
	public void testDoBackward_unclamped() {
		final InterRangeDoubleConverter c = new InterRangeDoubleConverter(RANGE_1, RANGE_2, UNCLAMPED);
		assertCloseEnough(-1.0, c.doBackward(10.0));
		assertCloseEnough(-2.0, c.doBackward(20.0));
		assertCloseEnough(-2.5, c.doBackward(25.0));
		assertCloseEnough(-3.0, c.doBackward(30.0));
		assertCloseEnough(-4.0, c.doBackward(40.0));
	}
}
