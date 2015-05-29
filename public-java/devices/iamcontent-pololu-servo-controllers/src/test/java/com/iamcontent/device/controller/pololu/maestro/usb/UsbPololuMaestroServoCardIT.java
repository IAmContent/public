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

import com.iamcontent.device.controller.pololu.maestro.AbstractPololuMaestroServoCardIT;
import com.iamcontent.device.controller.pololu.maestro.MaestroCardType;
import com.iamcontent.device.controller.pololu.maestro.PololuMaestroServoCard;

/**
 * A simple integration test, which doubles as an example of how to use the maestro.usb package.
 * @author Greg Elderfield
 */
public class UsbPololuMaestroServoCardIT extends AbstractPololuMaestroServoCardIT {

	private static final double ACCELERATION = 120;
	private static final double SPEED = 50;
	private static final double POSITION = 7000;
	private static final double MID_POSITION = 6000;
	
	private PololuMaestroServoCard card;
	
	public UsbPololuMaestroServoCardIT() {
		super(ACCELERATION, SPEED, POSITION, MID_POSITION);
	}

	@Before
	public void setUp() {
		card = UsbPololuMaestroServoCards.defaultUsbPololuMaestroServoCard();
	}

	@Test
	public void testGetType() {
		final MaestroCardType cardType = getCardType();
		assertEquals(MaestroCardType.MICRO_MAESTRO_6, cardType);
	}

	@Override
	protected void setAcceleration(int channel, double acceleration) {
		card.setRawAcceleration((short) channel, (short) acceleration);
	}

	@Override
	protected void setSpeed(int channel, double speed) {
		card.setRawSpeed((short) channel, (short)speed);
	}

	@Override
	protected void setPosition(int channel, double position) {
		card.setRawPosition((short) channel, (short)position);
	}

	@Override
	protected double getPosition(int channel) {
		return card.getRawPosition((short) channel);
	}
	
	protected MaestroCardType getCardType() {
		return card.getType();
	}
}
