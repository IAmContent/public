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
package com.iamcontent.device.io.analog.calibrate;

import static com.iamcontent.core.math.InterRangeDoubleConverter.IDENTITY_FUNCTION;

import com.google.common.base.Converter;
import com.google.common.base.Function;
import com.iamcontent.core.lang.Delegator;
import com.iamcontent.device.io.analog.AnalogIO;

/**
 * An {@link AnalogIO} that is calibrated by applying the given calibration functions to values before they are set or returned. 
 * @author Greg Elderfield
 */
public class CalibratedAnalogIO extends Delegator<AnalogIO> implements AnalogIO {
	
	private final Function<Double, Double> outputCalibration;
	private final Function<Double, Double> inputCalibration;
	
	public CalibratedAnalogIO(AnalogIO delegate, AnalogIOCalibrator calibrator) {
		this(delegate, calibrator.getValueConverter());
	}

	public CalibratedAnalogIO(AnalogIO delegate, Converter<Double, Double> calibration) {
		this(delegate, calibration, reverse(calibration));
	}

	public CalibratedAnalogIO(AnalogIO delegate, Function<Double, Double> outputCalibration, Function<Double, Double> inputCalibration) {
		super(delegate);
		this.outputCalibration = identityIfNull(outputCalibration);
		this.inputCalibration = identityIfNull(inputCalibration);
	}
	
	@Override
	public void setValue(double v) {
		final double c = outputCalibration.apply(v);
		delegate().setValue(c);
	}
	
	@Override
	public double getValue() {
		final double v = delegate().getValue();
		return inputCalibration.apply(v);
	}
	
	protected static Function<Double, Double> identityIfNull(Function<Double, Double> f) {
		return f==null ? IDENTITY_FUNCTION : f;
	}
	
	private static Converter<Double, Double> reverse(Converter<Double, Double> calibration) {
		return calibration==null ? null : calibration.reverse();
	}
}
