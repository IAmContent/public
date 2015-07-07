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
import static com.iamcontent.device.servo.calibrate.ServoSourceCalibrators.channelCalibrator;

import com.google.common.base.Function;
import com.iamcontent.core.geom.Geometry.ThreeDimension;
import com.iamcontent.device.channel.ChannelIdTranslations;
import com.iamcontent.device.io.analog.AnalogIOController;
import com.iamcontent.device.io.analog.AnalogIOSource;
import com.iamcontent.device.io.analog.StandardAnalogIOConfiguration;
import com.iamcontent.device.io.analog.calibrate.AnalogIOSourceCalibrator;
import com.iamcontent.device.servo.ServoSource;
import com.iamcontent.device.servo.StandardServoConfiguration;
import com.iamcontent.device.servo.calibrate.ServoSourceCalibrator;
import com.iamcontent.device.servo.raw.ServoController;

/**
 * A specific implementation of Greg Elderfield's Robonova-1.
 * @author Greg Elderfield
 */
public class CustomRobonova implements Robonova {

	private static final String BASE_CONFIG_FOLDER = "robonova/";
	private static final String SERVO_CONFIG_FOLDER = BASE_CONFIG_FOLDER + "servo/";
	private static final String IO_CONFIG_FOLDER = BASE_CONFIG_FOLDER + "io/";
	
	private static final String SERVO_CALIBRATOR_NAME = SERVO_CONFIG_FOLDER + "pololu-maestro-calibration-for-Hitec-HSR4898HB-servos";
	private static final String SERVO_TUNING_CALIBRATOR_NAME = SERVO_CONFIG_FOLDER + "robonova-servo-tuning-calibration";
	private static final String SERVO_CHANNEL_TRANSLATION_NAME = SERVO_CONFIG_FOLDER + "channels";
	
	private static final String ACCELEROMETER_CHANNEL_TRANSLATION_NAME = IO_CONFIG_FOLDER + "accelerometer-channels";

	private final ServoController<Integer> controller = defaultServoController();
	private final StandardServoConfiguration<Integer, ServoId> servoConfiguration = servoConfiguration(controller);
	private final StandardAnalogIOConfiguration<Integer, ThreeDimension> accelerometerConfig = accelerometerConfig(controller);
	
	@Override
	public ServoSource<ServoId> servos() {
		return servoConfiguration.getTunedRechanneledServos();
	}

	@Override
	public AnalogIOSource<ThreeDimension> accelerometer() {
		return accelerometerConfig.getNormalRechanneledIOs();
	}
	
	protected StandardServoConfiguration<Integer, ServoId> servoConfiguration(ServoController<Integer> servoController) {
		return new StandardServoConfiguration<Integer, ServoId>(servoController, normalizingServoCalibrator(), servoChannelTranslation(), servoTuningCalibrator());
	}

	private StandardAnalogIOConfiguration<Integer, ThreeDimension> accelerometerConfig(AnalogIOController<Integer> ioController) {
		return new StandardAnalogIOConfiguration<Integer, ThreeDimension>(ioController, normalizingIOCalibrator());
	}
	
	protected ServoController<Integer> defaultServoController() {
		return pololuMaestroServoController(defaultUsbPololuMaestroServoCard());
	}
	
	private ServoSourceCalibrator<Integer> normalizingServoCalibrator() {
		return channelCalibrator(SERVO_CALIBRATOR_NAME, Integer.class);
	}

	protected Function<ServoId, Integer> servoChannelTranslation() {
		return ChannelIdTranslations.function(SERVO_CHANNEL_TRANSLATION_NAME, ServoId.class, Integer.class);
	}

	protected ServoSourceCalibrator<ServoId> servoTuningCalibrator() {
		return channelCalibrator(SERVO_TUNING_CALIBRATOR_NAME, ServoId.class);
	}
	
	private AnalogIOSourceCalibrator<Integer> normalizingIOCalibrator() {
		return null;
	}

	protected Function<ThreeDimension, Integer> accelerometerChannelTranslation() {
		return ChannelIdTranslations.function(ACCELEROMETER_CHANNEL_TRANSLATION_NAME, ThreeDimension.class, Integer.class);
	}
}
