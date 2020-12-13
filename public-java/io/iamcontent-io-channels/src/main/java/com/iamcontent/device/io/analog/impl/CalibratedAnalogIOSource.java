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
package com.iamcontent.device.io.analog.impl;

import com.iamcontent.core.lang.Delegator;
import com.iamcontent.device.io.analog.AnalogIO;
import com.iamcontent.device.io.analog.AnalogIOSource;
import com.iamcontent.device.io.analog.AnalogIOSourceCalibration;

/**
 * An {@link AnalogIOSource} that returns calibrated proxies of the {@link AnalogIO}s from another (target) {@link AnalogIOSource}.
 * {@link AnalogIO}s are calibrated using the coresponding calibrator from the {@link AnalogIOSourceCalibration}
 * 
 * @param <C> The type used to identify an analog input/output channel.
 * 
 * @author Greg Elderfield
 */
public class CalibratedAnalogIOSource<C> extends Delegator<AnalogIOSource<C>> implements AnalogIOSource<C> {
	
	private final AnalogIOSourceCalibration<C> calibration;
	
	public CalibratedAnalogIOSource(AnalogIOSource<C> target, AnalogIOSourceCalibration<C> calibration) {
		super(target);
		this.calibration = calibration;
	}

	@Override
	public AnalogIO forChannel(C channelId) {
		return delegate().forChannel(channelId).calibrated(calibration.forChannel(channelId));
	}
	
	protected AnalogIOSourceCalibration<C> calibration() {
		return calibration;
	}
}
