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
package com.iamcontent.io.gson;

import static com.iamcontent.io.gson.DoubleRangeDeserializerTest.assertExactlyEquals;
import static com.iamcontent.io.gson.DoubleRangeDeserializerTest.rangeJson;
import static com.iamcontent.io.gson.InterRangeDoubleConverterDeserializer.customGsonBuilder;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.iamcontent.core.math.InterRangeDoubleConverter;
import com.iamcontent.core.math.InterRangeDoubleConverter.Mode;

public class InterRangeDoubleConverterDeserializerTest {

	private static final String JSON_INPUT_RANGE = fromRangeJson("1.1", "2.2");
	private static final String JSON_OUTPUT_RANGE = toRangeJson("3.3", "4.4");
	private static final String JSON_VALUES = JSON_INPUT_RANGE + "," + JSON_OUTPUT_RANGE;
	
	private Gson gson;

	@Before
	public void setUp() throws Exception {
		gson = customGsonBuilder().create();
	}

	@Test
	public void testDeserialize_explicitInputRange() {
		final InterRangeDoubleConverter actual = gson.fromJson("{" + JSON_VALUES + "}", InterRangeDoubleConverter.class);
		assertExactlyEquals(1.1, actual.getFromRange().getLimit1());
		assertExactlyEquals(2.2, actual.getFromRange().getLimit2());
		assertExactlyEquals(3.3, actual.getToRange().getLimit1());
		assertExactlyEquals(4.4, actual.getToRange().getLimit2());
	}

	@Test
	public void testDeserialize_defaultInputRange() {
		final InterRangeDoubleConverter actual = gson.fromJson("{" + JSON_OUTPUT_RANGE + "}", InterRangeDoubleConverter.class);
		assertExactlyEquals(0.0, actual.getFromRange().getLimit1());
		assertExactlyEquals(1.0, actual.getFromRange().getLimit2());
		assertExactlyEquals(3.3, actual.getToRange().getLimit1());
		assertExactlyEquals(4.4, actual.getToRange().getLimit2());
	}

	@Test
	public void testDeserialize_defaultOutputRange() {
		final InterRangeDoubleConverter actual = gson.fromJson("{" + JSON_INPUT_RANGE + "}", InterRangeDoubleConverter.class);
		assertExactlyEquals(1.1, actual.getFromRange().getLimit1());
		assertExactlyEquals(2.2, actual.getFromRange().getLimit2());
		assertExactlyEquals(0.0, actual.getToRange().getLimit1());
		assertExactlyEquals(1.0, actual.getToRange().getLimit2());
	}

	@Test
	public void testDeserialize_Mode() {
		final InterRangeDoubleConverter limitedMode = gson.fromJson("{" + JSON_VALUES + ",'mode':'LIMIT_RESULT_TO_RANGE'}", InterRangeDoubleConverter.class);
		assertEquals(limitedMode.getMode(), Mode.LIMIT_RESULT_TO_RANGE);
		
		final InterRangeDoubleConverter unlimitedMode = gson.fromJson("{" + JSON_VALUES + ",'mode':'DO_NOT_LIMIT_RESULT_TO_RANGE'}", InterRangeDoubleConverter.class);
		assertEquals(unlimitedMode.getMode(), Mode.DO_NOT_LIMIT_RESULT_TO_RANGE);
		
		final InterRangeDoubleConverter defaultMode = gson.fromJson("{" + JSON_VALUES + "}", InterRangeDoubleConverter.class);
		assertEquals(defaultMode.getMode(), Mode.LIMIT_RESULT_TO_RANGE);
	}

	public static String fromRangeJson(String limit1, String limit2) {
		return "'fromRange':" + rangeJson(limit1, limit2);
	}

	public static String toRangeJson(String limit1, String limit2) {
		return "'toRange':" + rangeJson(limit1, limit2);
	}
}
