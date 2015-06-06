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
package com.iamcontent.device.controller.pololu.maestro;

import static com.iamcontent.device.controller.pololu.maestro.PololuMaestroServoController.DEFAULT_CALIBRATOR_NAME;
import static com.iamcontent.device.controller.pololu.maestro.PololuMaestroServoController.pololuMaestroServoController;
import static com.iamcontent.device.controller.pololu.maestro.usb.UsbPololuMaestroServoCards.defaultUsbPololuMaestroServoCard;
import static com.iamcontent.device.servo.calibrate.Calibrators.numberedChannelCalibrator;

import com.iamcontent.device.servo.ServoSource;
import com.iamcontent.device.servo.Servos;
import com.iamcontent.device.servo.command.ServoCommandLineDriver;
import com.iamcontent.device.servo.raw.ServoController;

/**
 * A {@link ServoCommandLineDriver} for the {@link PololuMaestroServoController}. Useful for manual testing.
 * @author Greg Elderfield
 */
public class PololuCommandLineDriver extends ServoCommandLineDriver<Integer> {

	public static void main(String[] args) {
		final PololuCommandLineDriver driver = new PololuCommandLineDriver();
		driver.run();
	}

	@Override
	protected ServoSource<Integer> servoSource() {
		return Servos.calibratedServoSource(rawServoSource(), numberedChannelCalibrator(DEFAULT_CALIBRATOR_NAME));
	}

	private static ServoSource<Integer> rawServoSource() {
		return Servos.rawServoSource(defaultServoController());
	}

	private static ServoController<Integer> defaultServoController() {
		return pololuMaestroServoController(defaultUsbPololuMaestroServoCard());
	}

	@Override
	protected Integer parseChannel(String s) {
		return new Integer(s);
	}
}
