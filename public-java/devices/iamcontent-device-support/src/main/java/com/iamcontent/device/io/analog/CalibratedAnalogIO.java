/**
  IAmContent Public Libraries.
  Copyright (C) 2015 Greg Elderfield
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
package com.iamcontent.device.io.analog;

import com.google.common.base.Converter;
import com.google.common.base.Function;
import com.iamcontent.device.calibrate.CalibratedDelegator;

/**
 * An {@link AnalogIO} that is calibrated by applying the given calibration functions to values before they are set or returned. 
 * @author Greg Elderfield
 */
public class CalibratedAnalogIO extends CalibratedDelegator<AnalogIO> implements AnalogIO {
	
	private final Function<Double, Double> inputCalibration;
	
	public CalibratedAnalogIO(AnalogIO delegate, Function<Double, Double> outputCalibration, Function<Double, Double> inputCalibration) {
		super(delegate, outputCalibration);
		this.inputCalibration = identityIfNull(inputCalibration);
	}

	public CalibratedAnalogIO(AnalogIO delegate, Converter<Double, Double> calibration) {
		this(delegate, calibration, calibration.reverse());
	}

	@Override
	public void setValue(double v) {
		final double c = applyCalibration(v);
		delegate().setValue(c);
	}
	
	@Override
	public double getValue() {
		final double v = delegate().getValue();
		return inputCalibration.apply(v);
	}
}
