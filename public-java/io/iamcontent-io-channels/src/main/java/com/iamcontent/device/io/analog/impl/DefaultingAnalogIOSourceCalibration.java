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

import com.iamcontent.device.channel.DefaultingPerChannelSource;
import com.iamcontent.device.io.analog.AnalogIOCalibration;
import com.iamcontent.device.io.analog.AnalogIOSourceCalibration;

/**
 * A {@link AnalogIOSourceCalibration} that allows a default calibration to be set and over-ridden on a per-channel basis.
 * @author Greg Elderfield
 * 
 * @param <C> The type used to identify a channel. 
 */
public class DefaultingAnalogIOSourceCalibration<C> extends DefaultingPerChannelSource<C, AnalogIOCalibration, DefaultingAnalogIOSourceCalibration<C>> implements AnalogIOSourceCalibration<C> {

	public DefaultingAnalogIOSourceCalibration(AnalogIOCalibration defaultCalibration) {
		super(defaultCalibration);
	}
}