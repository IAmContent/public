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
import com.iamcontent.device.analog.in.AnalogInputSource;
import com.iamcontent.device.servo.ServoSource;
import com.iamcontent.device.servo.StandardServoConfiguration;
import com.iamcontent.device.servo.raw.ServoController;

/**
 * A specific implementation of Greg Elderfield's Robonova-1.
 * @author Greg Elderfield
 */
public class CustomRobonova implements Robonova {

	private static final String CONFIG_FOLDER = "robonova/servo/";
	public static final String SERVO_CALIBRATOR_NAME = CONFIG_FOLDER + "pololu-maestro-calibration-for-Hitec-HSR4898HB-servos";
	public static final String TUNING_CALIBRATOR_NAME = CONFIG_FOLDER + "robonova-servo-tuning-calibration";
	public static final String CHANNEL_TRANSLATION_NAME = CONFIG_FOLDER + "channels";

	private final StandardServoConfiguration<ServoId> servoConfiguration = servoConfiguration();
	
	@Override
	public ServoSource<ServoId> servos() {
		return servoConfiguration.getTunedServos();
	}

	@Override
	public AnalogInputSource<ThreeDimension> accelerometer() {
		return tunedAccelerometer();
	}
	
	protected StandardServoConfiguration<ServoId> servoConfiguration() {
		return new StandardServoConfiguration<ServoId>(defaultServoController(), SERVO_CALIBRATOR_NAME, CHANNEL_TRANSLATION_NAME, TUNING_CALIBRATOR_NAME, ServoId.class);
	}

	protected ServoController<Integer> defaultServoController() {
		return pololuMaestroServoController(defaultUsbPololuMaestroServoCard());
	}

	private AnalogInputSource<ThreeDimension> tunedAccelerometer() {
		return null;
	}
}
