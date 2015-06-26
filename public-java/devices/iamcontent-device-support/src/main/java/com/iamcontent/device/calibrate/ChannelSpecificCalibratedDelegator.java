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
import com.iamcontent.device.calibrate.CalibratedDelegator;
import com.iamcontent.device.channel.ChannelSpecific;

/**
 * A {@link CalibratedDelegator} that is {@link ChannelSpecific}. 
 * @author Greg Elderfield
 * 
 * @param <C> The type used to identify a channel. 
 * @param <D> The type of the delegate object. 
 */
public abstract class ChannelSpecificCalibratedDelegator<C, D extends ChannelSpecific<C>> extends CalibratedDelegator<D> implements ChannelSpecific<C> {
	
	public ChannelSpecificCalibratedDelegator(D delegate, Function<Double, Double> calibration) {
		super(delegate, calibration);
	}

	@Override
	public C getChannelId() {
		return delegate().getChannelId();
	}
}
