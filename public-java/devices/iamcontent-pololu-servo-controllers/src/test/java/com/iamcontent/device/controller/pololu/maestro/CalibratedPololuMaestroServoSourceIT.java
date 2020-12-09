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

import org.junit.Before;

import com.iamcontent.device.servo.Servo;
import com.iamcontent.device.servo.ServoSource;

/**
 * A simple integration test, which doubles as an example of how to drive a Pololu Maestro card as a calibrated {@link ServoSource}.
 * @author Greg Elderfield
 */
public class CalibratedPololuMaestroServoSourceIT extends AbstractPololuMaestroServoCardIT {

	private static final double ACCELERATION = 0.5;
	private static final double SPEED = 0.7;
	private static final double POSITION = 0.8;
	private static final double MID_POSITION = 0.5;
	
	private ServoSource<Integer> calibratedServoSource;
	
	public CalibratedPololuMaestroServoSourceIT() {
		super(ACCELERATION, SPEED, POSITION, MID_POSITION);
	}

	@Before
	public void setUp() {
		calibratedServoSource = DefaultPololuServoConfig.normalServos();
	}

	@Override
	protected void setAcceleration(int channel, double acceleration) {
		getServo(channel).setAcceleration(acceleration);
	}

	@Override
	protected void setSpeed(int channel, double speed) {
		getServo(channel).setSpeed(speed);
	}

	@Override
	protected void setPosition(int channel, double position) {
		getServo(channel).setValue(position);
	}

	@Override
	protected double getPosition(int channel) {
		return getServo(channel).getValue();
	}
	
	private Servo getServo(int channel) {
		return calibratedServoSource.forChannel(channel);
	}
}
