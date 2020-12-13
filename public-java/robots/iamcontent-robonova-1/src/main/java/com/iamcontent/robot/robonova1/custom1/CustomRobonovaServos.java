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
package com.iamcontent.robot.robonova1.custom1;

import static com.iamcontent.core.math.InterRangeDoubleConverter.converterFromNormalTo;
import static com.iamcontent.device.controller.pololu.maestro.DefaultPololuServoConfig.rawServoSource;
import static com.iamcontent.device.servo.impl.ImmutableServoCalibration.REVERSE_NORMAL_SERVO_CALIBRATION;
import static com.iamcontent.device.servo.impl.ImmutableServoCalibration.calibrateValueFromNormalTo;
import static com.iamcontent.robot.robonova1.ServoId.LEFT_ANKLE_INVERTER;
import static com.iamcontent.robot.robonova1.ServoId.LEFT_ANKLE_PLANTARFLEXOR;
import static com.iamcontent.robot.robonova1.ServoId.LEFT_ELBOW_FLEXOR;
import static com.iamcontent.robot.robonova1.ServoId.LEFT_HIP_ABDUCTOR;
import static com.iamcontent.robot.robonova1.ServoId.LEFT_KNEE_FLEXOR;
import static com.iamcontent.robot.robonova1.ServoId.RIGHT_ANKLE_INVERTER;
import static com.iamcontent.robot.robonova1.ServoId.RIGHT_ANKLE_PLANTARFLEXOR;
import static com.iamcontent.robot.robonova1.ServoId.RIGHT_HIP_ABDUCTOR;
import static com.iamcontent.robot.robonova1.ServoId.RIGHT_HIP_FLEXOR;
import static com.iamcontent.robot.robonova1.ServoId.RIGHT_KNEE_FLEXOR;
import static com.iamcontent.robot.robonova1.ServoId.RIGHT_SHOULDER_ABDUCTOR;
import static com.iamcontent.robot.robonova1.ServoId.RIGHT_SHOULDER_FLEXOR;

import java.util.function.Function;

import com.iamcontent.device.servo.ServoSource;
import com.iamcontent.device.servo.ServoSourceCalibration;
import com.iamcontent.device.servo.impl.ImmutableServoCalibration;
import com.iamcontent.robot.robonova1.Robonova;
import com.iamcontent.robot.robonova1.ServoId;

/**
 * Provides a custom ServoSource for my own {@link Robonova}.
 * @author Greg Elderfield
 */
public class CustomRobonovaServos {

	public static ServoSource<ServoId> servoSource() {
		return rawServoSource()
				.calibrated(normalization())
				.remapped(remapping())
				.calibrated(tuning());
	}
	
	private static ServoSourceCalibration<Integer> normalization() {
		return ServoSourceCalibration.servoSourceCalibration(new ImmutableServoCalibration(
				converterFromNormalTo(2432.0, 9600.0), 
				converterFromNormalTo(0.0, 200.0),
				converterFromNormalTo(0.0, 255.0)));
	}
	
	private static Function<ServoId, Integer> remapping() {
		return new Function<ServoId, Integer>() {
			@Override
			public Integer apply(ServoId servoId) {
				switch (servoId) {
				case LEFT_SHOULDER_FLEXOR:
					return 0;
				case RIGHT_SHOULDER_FLEXOR:
					return 1;
				case LEFT_SHOULDER_ABDUCTOR:
					return 2;
				case RIGHT_SHOULDER_ABDUCTOR:
					return 3;
				case LEFT_ELBOW_FLEXOR:
					return 4;
				case RIGHT_ELBOW_FLEXOR:
					return 5;
					
				case LEFT_HIP_ABDUCTOR:
					return 14;
				case RIGHT_HIP_ABDUCTOR:
					return 15;
				case LEFT_HIP_FLEXOR:
					return 16;
				case RIGHT_HIP_FLEXOR:
					return 17;
				case LEFT_KNEE_FLEXOR:
					return 18;
				case RIGHT_KNEE_FLEXOR:
					return 19;
				case LEFT_ANKLE_PLANTARFLEXOR:
					return 20;
				case RIGHT_ANKLE_PLANTARFLEXOR:
					return 21;
				case LEFT_ANKLE_INVERTER:
					return 22;
				case RIGHT_ANKLE_INVERTER:
					return 23;
				default:
					throw new IllegalStateException("Servo id not recognized: " + servoId);
				}
			}
		};
	}
	
	private static ServoSourceCalibration<ServoId> tuning() {
		return ServoSourceCalibration.<ServoId>servoSourceCalibration()
				.put(RIGHT_SHOULDER_FLEXOR, REVERSE_NORMAL_SERVO_CALIBRATION)
				.put(RIGHT_SHOULDER_ABDUCTOR, REVERSE_NORMAL_SERVO_CALIBRATION)
				.put(LEFT_ELBOW_FLEXOR, REVERSE_NORMAL_SERVO_CALIBRATION)
				.put(LEFT_HIP_ABDUCTOR, calibrateValueFromNormalTo(0.6, 0.0)) // Physical stop on full adduction
				.put(RIGHT_HIP_ABDUCTOR, calibrateValueFromNormalTo(0.4, 1.0)) // Physical stop on full adduction
				.put(RIGHT_HIP_FLEXOR, REVERSE_NORMAL_SERVO_CALIBRATION)
				.put(LEFT_KNEE_FLEXOR, calibrateValueFromNormalTo(0.05, 0.95)) // Hard stop and some L/R asymmetry
				.put(RIGHT_KNEE_FLEXOR, calibrateValueFromNormalTo(0.98, 0.08)) // Hard stop and some L/R asymmetry
				.put(LEFT_ANKLE_PLANTARFLEXOR, calibrateValueFromNormalTo(0.15, 1.0)) // Hard stop
				.put(RIGHT_ANKLE_PLANTARFLEXOR, calibrateValueFromNormalTo(0.85, 0.0)) // Hard stop
				.put(LEFT_ANKLE_INVERTER, calibrateValueFromNormalTo(0.55, 0.25)) // Hard stop
				.put(RIGHT_ANKLE_INVERTER, calibrateValueFromNormalTo(0.45, 0.75)); // Hard stop
	}
}
