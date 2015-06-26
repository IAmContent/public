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
import static com.iamcontent.device.servo.ServoSources.reChanneledServoSource;
import static com.iamcontent.device.servo.calibrate.ServoSourceCalibrators.numberedChannelCalibrator;

import com.google.common.base.Function;
import com.iamcontent.device.channel.ChannelIdTranslations;
import com.iamcontent.device.servo.ServoSource;
import com.iamcontent.device.servo.ServoSources;
import com.iamcontent.device.servo.calibrate.ServoSourceCalibrator;
import com.iamcontent.device.servo.calibrate.gson.JsonBasedServoSourceCalibratorReader;
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

	@Override
	public ServoSource<ServoId> servos() {
		return tunedServos();
	}

	protected ServoSource<ServoId> tunedServos() {
		return ServoSources.calibratedServoSource(rechanneledServos(), tuningCalibrator());
	}

	protected ServoSource<ServoId> rechanneledServos() {
		return reChanneledServoSource(calibratedServos(), channelTranslation());
	}

	protected ServoSource<Integer> calibratedServos() {
		return ServoSources.calibratedServoSource(rawServos(), numberedChannelCalibrator(SERVO_CALIBRATOR_NAME));
	}

	protected ServoSource<Integer> rawServos() {
		return ServoSources.rawServoSource(defaultServoController());
	}

	protected ServoController<Integer> defaultServoController() {
		return pololuMaestroServoController(defaultUsbPololuMaestroServoCard());
	}

	protected Function<ServoId, Integer> channelTranslation() {
		return ChannelIdTranslations.function(CHANNEL_TRANSLATION_NAME, ServoId.class, Integer.class);
	}
	
	protected ServoSourceCalibrator<ServoId> tuningCalibrator() {
		return JsonBasedServoSourceCalibratorReader.read(TUNING_CALIBRATOR_NAME, ServoId.class);
	}
	
}
