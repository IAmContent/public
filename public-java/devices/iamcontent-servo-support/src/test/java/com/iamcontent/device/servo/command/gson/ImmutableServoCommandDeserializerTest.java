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
package com.iamcontent.device.servo.command.gson;

import static com.iamcontent.device.servo.command.gson.ImmutableServoCommandDeserializer.customGsonBuilder;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.iamcontent.device.servo.command.ImmutableServoCommand;
import com.iamcontent.device.servo.command.ServoCommand;

public class ImmutableServoCommandDeserializerTest {

	private enum TestChannel {ONE, TWO, THREE};
	
	private static final String JSON_VALUES_POS = "{"
			+ "'channel':ONE,"
			+ "'position':4.4"
			+ "}";

	private static final String JSON_VALUES_NO_POS = "{"
			+ "'channel':TWO,"
			+ "'speed':5.5,"
			+ "'acceleration':6.6"
			+ "}";

	private static final String JSON_VALUES_ALL = "{"
			+ "'channel':THREE,"
			+ "'position':1.1,"
			+ "'speed':2.2,"
			+ "'acceleration':3.3"
			+ "}";

	private Gson gson;

	@Before
	public void setUp() throws Exception {
		gson = customGsonBuilder(TestChannel.class).create();
	}

	@Test
	public void testDeserialize() {
		checkCommand(JSON_VALUES_POS, TestChannel.ONE, 4.4, null, null);
		checkCommand(JSON_VALUES_NO_POS, TestChannel.TWO, null, 5.5, 6.6);
		checkCommand(JSON_VALUES_ALL, TestChannel.THREE, 1.1, 2.2, 3.3);
	}

	private void checkCommand(String json, TestChannel channel, Double position, Double speed, Double acceleration) {
		final ImmutableServoCommand<?> actual = gson.fromJson(json, ImmutableServoCommand.class);
		checkCommand(actual, channel, position, speed, acceleration);
	}

	public static void checkCommand(ServoCommand<?> actual, Object channel, Double position, Double speed, Double acceleration) {
		assertEquals(channel, actual.getChannel());
		assertEquals(position, actual.getPosition());
		assertEquals(speed, actual.getSpeed());
		assertEquals(acceleration, actual.getAcceleration());
	}
}
