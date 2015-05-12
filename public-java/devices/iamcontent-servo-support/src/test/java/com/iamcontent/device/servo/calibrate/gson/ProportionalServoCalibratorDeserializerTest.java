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
package com.iamcontent.device.servo.calibrate.gson;

import static com.iamcontent.device.servo.calibrate.gson.ProportionalServoCalibratorDeserializer.customGson;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.gson.Gson;
import com.iamcontent.core.math.DoubleRange;
import com.iamcontent.core.math.InterRangeDoubleConverter;
import com.iamcontent.device.servo.calibrate.ProportionalServoCalibrator;

public class ProportionalServoCalibratorDeserializerTest {

	private static final String JSON_VALUES = "{"
			+ converterJson("positionConverter", "1.1", "2.2") + ","
			+ converterJson("speedConverter", "3.3", "4.4") + ","
			+ converterJson("accelerationConverter", "5.5", "6.6")
			+ "}";

	private static final String SOME_JSON_VALUES = "{"
			+ converterJson("positionConverter", "1.1", "2.2")
			+ "}";

	private Gson gson;

	@Before
	public void setUp() throws Exception {
		gson = customGson();
	}

	@Test
	public void testDeserialize() {
		final ProportionalServoCalibrator actual = gson.fromJson(JSON_VALUES, ProportionalServoCalibrator.class);
		final DoubleRange positionRange = targetRange(actual.getPositionConverter());
		final DoubleRange speedRange = targetRange(actual.getSpeedConverter());
		final DoubleRange accelerationRange = targetRange(actual.getAccelerationConverter());
		assertExactlyEquals(1.1, positionRange.getLimit1());
		assertExactlyEquals(2.2, positionRange.getLimit2());
		assertExactlyEquals(3.3, speedRange.getLimit1());
		assertExactlyEquals(4.4, speedRange.getLimit2());
		assertExactlyEquals(5.5, accelerationRange.getLimit1());
		assertExactlyEquals(6.6, accelerationRange.getLimit2());
	}

	@Test
	public void testDeserialize_nullArguments() {
		final ProportionalServoCalibrator actual = gson.fromJson(SOME_JSON_VALUES, ProportionalServoCalibrator.class);
		final DoubleRange positionRange = targetRange(actual.getPositionConverter());
		assertExactlyEquals(1.1, positionRange.getLimit1());
		assertExactlyEquals(2.2, positionRange.getLimit2());
		assertNull(actual.getSpeedConverter());
		assertNull(actual.getAccelerationConverter());
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
