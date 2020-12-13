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
package com.iamcontent.device.io.analog;

import com.iamcontent.device.channel.PerChannelSource;
import com.iamcontent.device.io.analog.impl.DefaultingAnalogIOSourceCalibration;

/**
 * Defines the calibration of the {@link AnalogIO}s of a {@link AnalogIOSource}.
 * @author Greg Elderfield
 */
public interface AnalogIOSourceCalibration<C> extends PerChannelSource<C, AnalogIOCalibration> {

	public static <C> DefaultingAnalogIOSourceCalibration<C> analogIOSourceCalibration(AnalogIOCalibration calibration) {
		return new DefaultingAnalogIOSourceCalibration<C>(calibration);
	}

	public static <C> DefaultingAnalogIOSourceCalibration<C> analogIOSourceCalibration() {
		return analogIOSourceCalibration(AnalogIOCalibration.IDENTITY);
	}
}
