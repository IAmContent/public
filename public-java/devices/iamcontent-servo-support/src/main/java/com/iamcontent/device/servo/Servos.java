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
package com.iamcontent.device.servo;

import com.iamcontent.device.servo.calibrate.ServoCalibrator;
import com.iamcontent.device.servo.calibrate.ServoCalibrators;
import com.iamcontent.device.servo.calibrate.ServoSourceCalibrator;
import com.iamcontent.device.servo.raw.RawServo;
import com.iamcontent.device.servo.raw.ServoController;

/**
 * Methods to facilitate {@link Servo} usage.
 * @author Greg Elderfield
 */
public final class Servos {
	/**
	 * @return A {@link ServoSource} of {@link RawServo}s for the given {@link ServoController}.
	 */
	public static ServoSource rawServoSource(final ServoController controller) {
		return new ServoSource() {
			@Override
			public Servo getServo(int channel) {
				return new RawServo(controller, channel);
			}
		};
	}

	/**
	 * @return A {@link ServoSource} of {@link CalibratedServo}s, calibrated by the default {@link ServoSourceCalibrator}. Each servo from the returned source
	 * delegates to its corresponding {@link Servo} from the given {@link ServoSource}.
	 */
	public static ServoSource calibratedServoSource(final ServoSource delegate) {
		return new ServoSource() {
			private final ServoSourceCalibrator calibrator = ServoCalibrators.defaultServoSourceCalibrator();
			@Override
			public Servo getServo(int channel) {
				final Servo delegateServo = delegate.getServo(channel);
				final ServoCalibrator servoCalibrator = calibrator.getServoCalibrator(channel);
				return new CalibratedServo(delegateServo, servoCalibrator);
			}
		};
	}

	/**
	 * @return A {@link ServoSource} of {@link CalibratedServo}s, calibrated by the given {@link ServoSourceCalibrator}. Each servo from the returned source
	 * delegates to its corresponding {@link Servo} from the given {@link ServoSource}.
	 */
	public static ServoSource calibratedServoSource(final ServoSource delegate, final ServoSourceCalibrator calibrator) {
		return new ServoSource() {
			@Override
			public Servo getServo(int channel) {
				final Servo delegateServo = delegate.getServo(channel);
				final ServoCalibrator servoCalibrator = calibrator.getServoCalibrator(channel);
				return new CalibratedServo(delegateServo, servoCalibrator);
			}
		};
	}
}
