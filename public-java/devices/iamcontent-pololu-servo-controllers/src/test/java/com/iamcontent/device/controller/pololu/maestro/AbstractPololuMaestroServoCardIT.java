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
package com.iamcontent.device.controller.pololu.maestro;

import static org.junit.Assert.assertEquals;

import java.util.logging.Logger;

import org.junit.Test;

import com.iamcontent.core.BooleanCondition;
import com.iamcontent.core.ThreadUtils;

/**
 * A simple abstract integration test for Pololu Maestro servo cards.
 * @author Greg Elderfield
 */
public abstract class AbstractPololuMaestroServoCardIT {

	private static final Logger LOGGER = Logger.getLogger(AbstractPololuMaestroServoCardIT.class.getName());
	private static final int CHANNEL = 0;

	private final double acceleration;
	private final double speed;
	private final double position;
	private final double midPosition;

	public AbstractPololuMaestroServoCardIT(double acceleration, double speed, double position, double midPosition) {
		this.acceleration = acceleration;
		this.speed = speed;
		this.position = position;
		this.midPosition = midPosition;
	}

	@Test
	public void testRawDynamics() throws Exception {
		resetDynamics();
		
		setAcceleration(CHANNEL, acceleration);
		setSpeed(CHANNEL, speed);
		setPosition(CHANNEL, position);
		
		waitForServoPosition(CHANNEL, position);
		
		final double measuredPosition = getPosition(CHANNEL);
		
		assertEquals(position, measuredPosition, 0.0001);
	}

	protected abstract void setAcceleration(int channel, double acceleration);
	protected abstract void setSpeed(int channel, double speed);
	protected abstract void setPosition(int channel, double position);
	protected abstract double getPosition(int channel);

	private void waitForServoPosition(final int channel, final double position) {
		ThreadUtils.sleepUntil(servoIsAtPosition(channel, position), 5000, 20);
		System.out.println("-----");
	}

	private BooleanCondition servoIsAtPosition(final int channel, final double position) {
		return new BooleanCondition() {
			@Override
			public boolean isTrue() {
				final double pos = getPosition(channel);
				LOGGER.info("Servo " + channel + " is at position " + pos + " waiting for position " + position);
				return pos == position;
			}
		};
	}

	private void resetDynamics() {
		setAcceleration(CHANNEL, (short) 0);
		setSpeed(CHANNEL, (short) 0);
		setPosition(CHANNEL, midPosition);
		waitForServoPosition(CHANNEL, midPosition);
	}
}
