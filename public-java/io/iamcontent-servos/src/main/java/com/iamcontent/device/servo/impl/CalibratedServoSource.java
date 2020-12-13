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
package com.iamcontent.device.servo.impl;

import com.iamcontent.core.lang.Delegator;
import com.iamcontent.device.servo.Servo;
import com.iamcontent.device.servo.ServoSource;
import com.iamcontent.device.servo.ServoSourceCalibration;

/**
 * A ServoSource that returns calibrated proxies of the {@link Servo}s from another (target) {@link ServoSource}.
 * All {@link Servo}s are calibrated using the same {@link ServoSourceCalibration}
 * 
 * @author Greg Elderfield
 */
public class CalibratedServoSource<C> extends Delegator<ServoSource<C>> implements ServoSource<C> {
	
	protected final ServoSourceCalibration<C> calibration;
	
	public CalibratedServoSource(ServoSource<C> target, ServoSourceCalibration<C> calibration) {
		super(target);
		this.calibration = calibration;
	}

	@Override
	public Servo forChannel(C channelId) {
		return delegate().forChannel(channelId).calibrated(calibration.forChannel(channelId));
	}
}
