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
package com.iamcontent.device.controller.pololu.maestro.usb;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.iamcontent.core.Threads;
import com.iamcontent.device.controller.pololu.maestro.MaestroCardType;
import com.iamcontent.device.controller.pololu.maestro.PololuMaestroServoCard;

/**
 * A simple integration test, which doubles as an example of how to use the maestro.usb package.
 * @author Greg Elderfield
 */
public class SimpleUsbPololuMaestroServoCardIT {

	private static final short CHANNEL = 0;
	
	private PololuMaestroServoCard card;
	
	@Before
	public void setUp() {
		card = UsbPololuMaestroServoCards.defaultUsbPololuMaestroServoCard();
		resetDynamics();
	}

	@Test
	public void testGetType() {
		final MaestroCardType cardType = card.getType();
		assertEquals(MaestroCardType.MICRO_MAESTRO_6, cardType);
	}

	@Test
	public void testRawDynamics() throws Exception {
		final short acceleration = 120;
		final short speed = 50;
		final short position = 7000;
		card.setRawAcceleration(CHANNEL, acceleration);
		card.setRawSpeed(CHANNEL, speed);
		card.setRawPosition(CHANNEL, position);
		
		waitForServoPosition(CHANNEL, position);
		
		final short measuredPosition = card.getRawPosition(CHANNEL);
		
		assertEquals(position, measuredPosition);
	}

	private void waitForServoPosition(final short channel, final short position) {
		for (int i=0; i < 50; i++) {
			final short pos = card.getRawPosition(channel);
			System.out.println("Servo " + channel + " is at position " + pos);
			if (pos==position)
				break;
			Threads.sleepQuietly(20);
		}
		System.out.println("-----");
	}

	private void resetDynamics() {
		final short midPosition = 6000;
		card.setRawAcceleration(CHANNEL, (short) 0);
		card.setRawSpeed(CHANNEL, (short) 0);
		card.setRawPosition(CHANNEL, midPosition);
		waitForServoPosition(CHANNEL, midPosition);
	}
}
