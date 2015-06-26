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
package com.iamcontent.device.calibrate;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.iamcontent.core.lang.Delegator;

/**
 * A {@link Delegator} that is calibrated by a function. 
 * @author Greg Elderfield
 * 
 * @param <D> The type of the delegate object. 
 */
public abstract class CalibratedDelegator<D> extends Delegator<D> {
	
	private static final Function<Double, Double> IDENTITY_FUNCTION = Functions.identity();
	
	private final Function<Double, Double> calibration;
	
	public CalibratedDelegator(D delegate, Function<Double, Double> calibration) {
		super(delegate);
		this.calibration = identityIfNull(calibration);
	}

	protected Double applyCalibration(Double input) {
		return calibration.apply(input);
	}

	protected static Function<Double, Double> identityIfNull(Function<Double, Double> f) {
		return f==null ? IDENTITY_FUNCTION : f;
	}
}
