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
package com.iamcontent.device.servo;

import static com.iamcontent.device.servo.ServoSourceCalibration.servoSourceCalibration;

import java.util.function.Function;

import com.iamcontent.device.channel.PerChannelSource;
import com.iamcontent.device.servo.impl.CalibratedServoSource;
import com.iamcontent.device.servo.impl.RawServo;
import com.iamcontent.device.servo.impl.RemappedServoSource;

/**
 * Represents a source of {@link Servo}s.
 * @author Greg Elderfield
 * 
 * @param <C> The type used to identify the channel of a {@link Servo}. 
 */
public interface ServoSource<C> extends PerChannelSource<C, Servo> {

	/**
	 * Returns a ServoSource that has new channel ids, as defined by the given function.
	 */
	default <NewServoId> ServoSource<NewServoId> remapped(Function<NewServoId, C> remapping) {
		return new RemappedServoSource<NewServoId, C>(this, remapping);
	}

	/**
	 * Returns a ServoSource that returns calibrated proxies of the {@link Servo}s from another (target) {@link ServoSource}.
	 */
	default ServoSource<C> calibrated(ServoSourceCalibration<C> calibration) {
		return new CalibratedServoSource<C>(this, calibration);
	}

	/**
	 * Returns a ServoSource that returns calibrated proxies of the {@link Servo}s from another (target) {@link ServoSource}.
	 */
	default ServoSource<C> calibrated(ServoCalibration calibration) {
		return calibrated(servoSourceCalibration(calibration));
	}

	/**
	 * @return A {@link ServoSource} of {@link RawServo}s for the given {@link ServoController}.
	 */
	public static <C> ServoSource<C> rawServoSource(final ServoController<C> controller) {
		return new ServoSource<C>() {
			@Override
			public Servo forChannel(C channel) {
				return new RawServo<C>(controller, channel);
			}
		};
	}

}
