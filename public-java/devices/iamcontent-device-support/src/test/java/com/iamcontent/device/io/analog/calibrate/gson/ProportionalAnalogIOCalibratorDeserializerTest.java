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
package com.iamcontent.device.io.analog.calibrate.gson;

import static com.iamcontent.device.io.analog.calibrate.gson.ProportionalAnalogIOCalibratorDeserializer.proportionalAnalogIOCalibratorGsonBuilder;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.gson.Gson;
import com.iamcontent.core.math.DoubleRange;
import com.iamcontent.core.math.InterRangeDoubleConverter;
import com.iamcontent.device.io.analog.calibrate.ProportionalAnalogIOCalibrator;

public class ProportionalAnalogIOCalibratorDeserializerTest {

	private static final String JSON_VALUES = "{"
			+ converterJson("valueConverter", "1.1", "2.2")
			+ "}";

	private Gson gson;

	@Before
	public void setUp() throws Exception {
		gson = proportionalAnalogIOCalibratorGsonBuilder().create();
	}

	@Test
	public void testDeserialize() {
		final ProportionalAnalogIOCalibrator actual = gson.fromJson(JSON_VALUES, ProportionalAnalogIOCalibrator.class);
		final DoubleRange valueRange = targetRange(actual.getValueConverter());
		assertExactlyEquals(1.1, valueRange.getLimit1());
		assertExactlyEquals(2.2, valueRange.getLimit2());
	}

	public static DoubleRange sourceRange(Function<Double, Double> converter) {
		return ((InterRangeDoubleConverter)converter).getFromRange();
	}

	public static DoubleRange targetRange(Function<Double, Double> converter) {
		return ((InterRangeDoubleConverter)converter).getToRange();
	}

	public static String converterJson(String fieldName, String toRangeLimit1, String toRangeLimit2) {
		return fieldName +":{" + toRangeJson(toRangeLimit1, toRangeLimit2) + "}";
	}

	public static String toRangeJson(String limit1, String limit2) {
		return "'toRange':" + rangeJson(limit1, limit2);
	}

	public static String rangeJson(String limit1, String limit2) {
		return "{'limit1':" + limit1 + ",'limit2':" + limit2 + "}";
	}
	
	public static void assertExactlyEquals(double expected, double actual) {
		assertEquals(expected, actual, 0.0);
	}
}
