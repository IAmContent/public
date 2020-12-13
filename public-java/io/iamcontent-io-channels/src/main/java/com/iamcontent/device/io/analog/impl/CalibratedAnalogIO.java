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
import com.iamcontent.core.math.MutableDouble;
import com.iamcontent.device.io.analog.AnalogIO;
import com.iamcontent.device.io.analog.AnalogIOCalibration;

/**
 * A {@link AnalogIO} that is a calibrated proxy for another (target) {@link AnalogIO}.
 * @author Greg Elderfield
 */
public class CalibratedAnalogIO extends Delegator<AnalogIO> implements AnalogIO {
	
	private final AnalogIOCalibration calibration;
	
	public CalibratedAnalogIO(AnalogIO target, AnalogIOCalibration calibration) {
		super(target);
		this.calibration = calibration;
	}
	
	@Override
	public MutableDouble value() {
		return delegate().value().calibrated(calibration().value());
	}

	protected AnalogIOCalibration calibration() {
		return calibration;
	}
}
