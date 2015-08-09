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
package com.iamcontent.device.servo.config.jaxb;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.iamcontent.device.servo.command.ServoCommand;

public class JaxbServoCommandTest {

	@Test
	public void testUnmarshalling() throws Exception {
        final List<ServoCommand<TestServoChannel>> commands = JaxbServoCommand.servoCommandsFromFile("test-jaxb-servo-commands.xml", TestServoChannel.class);
        
        assertNotNull(commands);
        assertEquals(2, commands.size());
        
        checkCommand(commands.get(0), TestServoChannel.WRIST, 1.1, 2.2, 3.3);
        checkCommand(commands.get(1), TestServoChannel.ANKLE, 4.4, null, null);
	}

	private void checkCommand(ServoCommand<TestServoChannel> command, TestServoChannel expectedChannel, Double expectedValue,
			Double expectedSpeed, Double expectedAcceleration) {
		assertEquals(expectedChannel, command.getChannel());
		assertEquals(expectedValue, command.getValue());
		assertEquals(expectedSpeed, command.getSpeed());
		assertEquals(expectedAcceleration, command.getAcceleration());
	}
}

enum TestServoChannel {ELBOW, WRIST, KNEE, ANKLE}
