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

import static com.iamcontent.device.controller.pololu.maestro.PololuMaestroServoController.pololuMaestroServoController;
import static com.iamcontent.device.controller.pololu.maestro.usb.UsbPololuMaestroServoCards.defaultUsbPololuMaestroServoCard;

import javax.usb.UsbDevice;

import com.iamcontent.device.servo.Servo;
import com.iamcontent.device.servo.ServoSource;
import com.iamcontent.device.servo.config.ServoConfigFunctions;
import com.iamcontent.device.servo.config.ServoConfiguration;
import com.iamcontent.device.servo.config.jaxb.JaxbServoConfig;
import com.iamcontent.device.servo.raw.ServoController;

/**
 * A helper for creating a default configuration of {@link Servo}s of the {@link PololuMaestroServoController},
 * calibrated to the normal (0..1) range. Uses the first Pololu Maestro {@link UsbDevice} that is found and calibrates
 * it based on a config file.
 * @author Greg Elderfield
 */
public class DefaultPololuServoConfig {

	public static ServoSource<Integer> normalServos() {
		return normalizingConfiguration().getNormalServos();
	}
	
	public static ServoConfiguration<Integer, Void> normalizingConfiguration() {
		return new ServoConfiguration<Integer, Void>(servoController(), configFunctions());
	}

	public static ServoController<Integer> servoController() {
		return pololuMaestroServoController(defaultUsbPololuMaestroServoCard());
	}

	public static ServoConfigFunctions<Integer, Void> configFunctions() {
		return JaxbServoConfig.configFunctionsFromFile("servo/pololu-maestro-calibration.xml", Integer.class, Void.class);
	}
}
