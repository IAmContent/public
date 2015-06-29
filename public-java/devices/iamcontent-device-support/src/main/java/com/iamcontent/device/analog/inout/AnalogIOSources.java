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
package com.iamcontent.device.analog.inout;

import com.google.common.base.Converter;
import com.google.common.base.Function;
import com.iamcontent.device.channel.PerChannelSource;

/**
 * Methods to create {@link AnalogIOSource}s.
 * @author Greg Elderfield
 */
public final class AnalogIOSources {

	/**
	 * @return An {@link AnalogIOSource} of {@link RawAnalogIO}s for the given {@link AnalogIOController}.
	 */
	public static <C> AnalogIOSource<C> rawAnalogIOSource(final AnalogIOController<C> controller) {
		return new AnalogIOSource<C>() {
			@Override
			public AnalogIO forChannel(C channelId) {
				return new RawAnalogIO<C>(controller, channelId);
			}
		};
	}
	
	/**
	 * @return An {@link AnalogIOSource}, calibrated by applying the given calibration function to values before they are returned. 
	 */
	public static <C> AnalogIOSource<C> calibratedAnalogIOSource(final AnalogIOSource<C> delegate, final PerChannelSource<C, Converter<Double, Double>> calibration) {
		return new AnalogIOSource<C>() {
			@Override
			public AnalogIO forChannel(C channelId) {
				final AnalogIO input = delegate.forChannel(channelId);
				final Converter<Double, Double> converter = calibration.forChannel(channelId);
				return new CalibratedAnalogIO<C>(input, converter);
			}
		};
	}
	
	/**
	 * @return An {@link AnalogIOSource} that reads values from a delegate {@link AnalogIOSource}, selecting inputs
	 * from the latter according to the given channelTranslation function.
	 */
	public static <C, D> AnalogIOSource<C> reChanneledAnalogIOSource(final AnalogIOSource<D> delegate, final Function<C, D> channelTranslation) {
		return new AnalogIOSource<C>() {
			@Override
			public AnalogIO forChannel(C channelId) {
				final D delegateChannel = channelTranslation.apply(channelId);
				return delegate.forChannel(delegateChannel);
			}
		};
	}
	
	private AnalogIOSources() {
	}
}
