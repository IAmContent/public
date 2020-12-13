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
package com.iamcontent.core.math;

import com.iamcontent.core.lang.Delegator;

/**
 * A MutableDouble that is a calibrated proxy for another (target) MutableDouble.
 * @author Greg Elderfield
 */
public class CalibratedMutableDouble extends Delegator<MutableDouble> implements MutableDouble {
	
	private final DoubleConverter converter;
	
	public CalibratedMutableDouble(MutableDouble target, DoubleConverter converter) {
		super(target);
		this.converter = converter;
	}
	
	@Override
	public double getValue() {
		return converter.applyBackward(delegate().getValue());
	}

	@Override
	public void setValue(double v) {
		delegate().setValue(converter.applyForward(v));
	}

}
