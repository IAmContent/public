package com.iamcontent.device.controller.pololu;


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


import static com.iamcontent.device.servo.raw.RawServoSource.rawServoSource;

import javax.usb.UsbDevice;

import com.iamcontent.device.servo.ServoSource;
import com.iamcontent.device.servo.raw.ServoController;

/**
 * Represents a Pololu Maestro card.
 * @author Greg Elderfield
 */
public class PololuMaestroUsbServoController extends PololuMaestroUsbServoCard implements ServoController {

	/**
	 * Creates a {@link ServoSource} for the first Pololu Maestro device that is found.
	 */
	public static ServoSource defaultServoSource() {
		return rawServoSource(defaultInstance());
	}
	
	/**
	 * Creates an instance for the first Pololu Maestro device that is found.
	 */
	public static PololuMaestroUsbServoController defaultInstance() {
		return new PololuMaestroUsbServoController();
	}
	
	/**
	 * Creates an instance with the first Pololu Maestro device that is found.
	 */
	public PololuMaestroUsbServoController() {
	}

	/**
	 * Creates an instance with the given UsbDevice.
	 */
	public PololuMaestroUsbServoController(UsbDevice device) {
		super(device);
	}

	@Override
	public void setPosition(int channel, double value) {
		setRawPosition((short)channel, (short)value);
	}
	
	@Override
	public double getPosition(int channel) {
		// TODO getPosition() not implemented yet
		return 0;
	}

	@Override
	public void setSpeed(int channel, double speed) {
		setRawSpeed((short)channel, (short)speed);
	}

	@Override
	public void setAcceleration(int channel, double acceleration) {
		setRawAcceleration((short)channel, (short)acceleration);
	}
}
