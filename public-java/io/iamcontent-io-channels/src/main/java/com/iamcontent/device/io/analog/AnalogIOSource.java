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

import java.util.function.Function;

import com.iamcontent.device.channel.PerChannelSource;
import com.iamcontent.device.io.analog.impl.CalibratedAnalogIOSource;
import com.iamcontent.device.io.analog.impl.RawAnalogIO;
import com.iamcontent.device.io.analog.impl.RemappedAnalogIOSource;

/**
 * Represents a source of {@link AnalogIO}s.
 * @author Greg Elderfield
 * 
 * @param <C> The type used to identify an analog input/output channel. 
 */
public interface AnalogIOSource<C> extends PerChannelSource<C, AnalogIO> {
	
	/**
	 * Returns a AnalogIOSource that has new channel ids, as defined by the given function.
	 */
	default <NewChannelId> AnalogIOSource<NewChannelId> remapped(Function<NewChannelId, C> remapping) {
		return new RemappedAnalogIOSource<NewChannelId, C>(this, remapping);
	}

	/**
	 * Returns a proxy AnalogIO that is two-way calibrated representation of this instance.
	 */
	default AnalogIOSource<C> calibrated(AnalogIOSourceCalibration<C> calibration) {
		return new CalibratedAnalogIOSource<C>(this, calibration);
	}

	public static <C> AnalogIOSource<C> rawAnalogIOSource(final AnalogIOController<C> controller) {
		return new AnalogIOSource<C>() {
			@Override
			public AnalogIO forChannel(C channelId) {
				return new RawAnalogIO<C>(controller, channelId);
			}
		};
	}
}
