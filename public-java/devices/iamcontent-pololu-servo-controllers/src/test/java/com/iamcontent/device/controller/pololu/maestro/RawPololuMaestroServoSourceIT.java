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

import static com.iamcontent.device.controller.pololu.maestro.PololuMaestroServoController.pololuMaestroServoController;
import static com.iamcontent.device.controller.pololu.maestro.usb.UsbPololuMaestroServoCards.defaultUsbPololuMaestroServoCard;

import org.junit.Before;

import com.iamcontent.device.servo.Servo;
import com.iamcontent.device.servo.ServoSource;
import com.iamcontent.device.servo.ServoSources;
import com.iamcontent.device.servo.raw.ServoController;

/**
 * A simple integration test, which doubles as an example of how to drive a Pololu Maestro card as a raw {@link ServoSource}.
 * @author Greg Elderfield
 */
public class RawPololuMaestroServoSourceIT extends AbstractPololuMaestroServoCardIT {

	private static final double ACCELERATION = 120;
	private static final double SPEED = 50;
	private static final double POSITION = 7000;
	private static final double MID_POSITION = 6000;
	
	private ServoSource<Integer> rawServoSource;
	
	public RawPololuMaestroServoSourceIT() {
		super(ACCELERATION, SPEED, POSITION, MID_POSITION);
	}

	@Before
	public void setUp() {
		rawServoSource = rawServoSource();
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
		return rawServoSource.forChannel(channel);
	}

	protected static ServoSource<Integer> rawServoSource() {
		return ServoSources.rawServoSource(defaultServoController());
	}

	private static ServoController<Integer> defaultServoController() {
		return pololuMaestroServoController(defaultUsbPololuMaestroServoCard());
	}
}
