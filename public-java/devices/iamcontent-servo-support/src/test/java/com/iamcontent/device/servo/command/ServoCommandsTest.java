package com.iamcontent.device.servo.command;

import static com.iamcontent.device.servo.command.gson.ImmutableServoCommandDeserializerTest.checkCommand;
import static com.iamcontent.device.servo.command.ServoCommands.servoCommands;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ServoCommandsTest {

	private enum TestChannel {ALPHA, BETA, GAMMA}
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testServoCommands() {
		final List<ServoCommand<TestChannel>> actual = servoCommands("servo/test-servo-commands", TestChannel.class);
		assertEquals(3, actual.size());
		checkCommand(actual.get(0), TestChannel.ALPHA, 3.3, 2.2, 1.1);
		checkCommand(actual.get(1), TestChannel.GAMMA, 6.6, 5.5, null);
		checkCommand(actual.get(2), TestChannel.BETA, 8.8, null, 7.7);
	}

}
