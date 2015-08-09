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
package com.iamcontent.robot.robonova1;

import static com.iamcontent.device.controller.pololu.maestro.PololuMaestroServoController.pololuMaestroServoController;
import static com.iamcontent.device.controller.pololu.maestro.usb.UsbPololuMaestroServoCards.defaultUsbPololuMaestroServoCard;

import com.iamcontent.core.geom.Geometry.ThreeDimension;
import com.iamcontent.device.io.analog.AnalogIOController;
import com.iamcontent.device.io.analog.AnalogIOSource;
import com.iamcontent.device.io.analog.calibrate.AnalogIOSourceCalibrator;
import com.iamcontent.device.io.analog.config.AnalogIOConfiguration;
import com.iamcontent.device.servo.ServoSource;
import com.iamcontent.device.servo.config.ServoConfigFunctions;
import com.iamcontent.device.servo.config.ServoConfiguration;
import com.iamcontent.device.servo.config.jaxb.JaxbServoConfig;
import com.iamcontent.device.servo.raw.ServoController;

/**
 * A specific implementation of Greg Elderfield's Robonova-1.
 * @author Greg Elderfield
 */
public class CustomRobonova implements Robonova {

	private static final String SERVO_CONFIG_FILE = "robonova-servo-config.xml";
//	private static final String IO_CONFIG_FILE = "robonova-io-config.xml";
	

	private final ServoController<Integer> controller = defaultServoController();
	private final ServoConfiguration<Integer, ServoId> servoConfiguration = servoConfiguration(controller);
	private final AnalogIOConfiguration<Integer, ThreeDimension> accelerometerConfig = accelerometerConfig(controller);
	
	@Override
	public ServoSource<ServoId> servos() {
		return servoConfiguration.getTunedRechanneledServos();
	}

	@Override
	public AnalogIOSource<ThreeDimension> accelerometer() {
		return accelerometerConfig.getTunedRechanneledIOs();
	}
	
	protected ServoConfiguration<Integer, ServoId> servoConfiguration(ServoController<Integer> servoController) {
		return new ServoConfiguration<Integer, ServoId>(servoController, servoConfigFunctions());
	}


	private static ServoConfigFunctions<Integer, ServoId> servoConfigFunctions() {
		return JaxbServoConfig.configFunctionsFromFile(SERVO_CONFIG_FILE, Integer.class, ServoId.class);
	}

	private AnalogIOConfiguration<Integer, ThreeDimension> accelerometerConfig(AnalogIOController<Integer> ioController) {
		return new AnalogIOConfiguration<Integer, ThreeDimension>(ioController, normalizingIOCalibrator());
	}
	
	protected ServoController<Integer> defaultServoController() {
		return pololuMaestroServoController(defaultUsbPololuMaestroServoCard());
	}
	
	private AnalogIOSourceCalibrator<Integer> normalizingIOCalibrator() {
		return null;
	}
}
