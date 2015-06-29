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
package com.iamcontent.device.analog.in;

import com.google.common.base.Function;
import com.iamcontent.device.channel.PerChannelSource;

/**
 * Methods to create {@link AnalogInputSource}s.
 * @author Greg Elderfield
 */
public final class AnalogInputSources {

	/**
	 * @return An {@link AnalogInputSource} of {@link RawAnalogInput}s for the given {@link AnalogInputController}.
	 */
	public static <C> AnalogInputSource<C> rawAnalogInputSource(final AnalogInputController<C> controller) {
		return new AnalogInputSource<C>() {
			@Override
			public AnalogInput forChannel(C channelId) {
				return new RawAnalogInput<C>(controller, channelId);
			}
		};
	}
	
	/**
	 * @return An {@link AnalogInputSource}, calibrated by applying the given calibration function to values before they are returned. 
	 */
	public static <C> AnalogInputSource<C> calibratedAnalogInputSource(final AnalogInputSource<C> delegate, final PerChannelSource<C, Function<Double, Double>> calibration) {
		return new AnalogInputSource<C>() {
			@Override
			public AnalogInput forChannel(C channelId) {
				final AnalogInput input = delegate.forChannel(channelId);
				final Function<Double, Double> calibrationFunction = calibration.forChannel(channelId);
				return new CalibratedAnalogInput<C>(input, calibrationFunction);
			}
		};
	}
	
	/**
	 * @return An {@link AnalogInputSource} that reads values from a delegate {@link AnalogInputSource}, selecting inputs
	 * from the latter according to the given channelTranslation function.
	 */
	public static <C, D> AnalogInputSource<C> reChanneledAnalogInputSource(final AnalogInputSource<D> delegate, final Function<C, D> channelTranslation) {
		return new AnalogInputSource<C>() {
			@Override
			public AnalogInput forChannel(C channelId) {
				final D delegateChannel = channelTranslation.apply(channelId);
				return delegate.forChannel(delegateChannel);
			}
		};
	}
	
	private AnalogInputSources() {
	}
}
