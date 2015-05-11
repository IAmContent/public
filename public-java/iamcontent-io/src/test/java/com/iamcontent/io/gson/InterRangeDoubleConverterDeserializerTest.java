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
package com.iamcontent.io.gson;

import static com.iamcontent.io.gson.GsonUtils.customGson;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.iamcontent.core.math.InterRangeDoubleConverter;
import com.iamcontent.core.math.InterRangeDoubleConverter.Mode;

public class InterRangeDoubleConverterDeserializerTest {

	private Gson gson;

	@Before
	public void setUp() throws Exception {
		gson = customGson();
	}

	@Test
	public void testDeserialize_defaultMode() {
		final InterRangeDoubleConverter actual = gson.fromJson("{'fromRange':{'limit1':1.0,'limit2':2.0},'toRange':{'limit1':3.0,'limit2':4.0}}", InterRangeDoubleConverter.class);
		assertExactlyEquals(1.0, actual.getFromRange().getLimit1());
		assertExactlyEquals(2.0, actual.getFromRange().getLimit2());
		assertExactlyEquals(3.0, actual.getToRange().getLimit1());
		assertExactlyEquals(4.0, actual.getToRange().getLimit2());
		assertEquals(actual.getMode(), Mode.LIMIT_RESULT_TO_RANGE);
	}

	@Test
	public void testDeserialize_specificMode() {
		final InterRangeDoubleConverter actual1 = gson.fromJson("{'fromRange':{'limit1':1.0,'limit2':2.0},'toRange':{'limit1':3.0,'limit2':4.0},'mode':'LIMIT_RESULT_TO_RANGE'}", InterRangeDoubleConverter.class);
		assertEquals(actual1.getMode(), Mode.LIMIT_RESULT_TO_RANGE);
		
		final InterRangeDoubleConverter actual2 = gson.fromJson("{'fromRange':{'limit1':1.0,'limit2':2.0},'toRange':{'limit1':3.0,'limit2':4.0},'mode':'DO_NOT_LIMIT_RESULT_TO_RANGE'}", InterRangeDoubleConverter.class);
		assertEquals(actual2.getMode(), Mode.DO_NOT_LIMIT_RESULT_TO_RANGE);
	}

	private void assertExactlyEquals(double expected, double actual) {
		assertEquals(expected, actual, 0.0);
	}
}
