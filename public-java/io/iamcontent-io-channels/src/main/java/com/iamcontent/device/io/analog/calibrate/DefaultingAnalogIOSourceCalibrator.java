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

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.Map;

import com.google.common.base.Converter;

/**
 * A {@link AnalogIOSourceCalibrator} that allows a default {@link Converter} to be set but over-ridden on a per-channel basis.
 * @author Greg Elderfield
 * 
 * @param <C> The type used to identify a channel. 
 */
public class DefaultingAnalogIOSourceCalibrator<C> implements AnalogIOSourceCalibrator<C> {

	private final AnalogIOCalibrator defaultCalibrator;
	private final Map<C, AnalogIOCalibrator> perChannelCalibrators;
	
	public DefaultingAnalogIOSourceCalibrator(AnalogIOCalibrator defaultCalibrator) {
		this(defaultCalibrator, Collections.<C, AnalogIOCalibrator> emptyMap());
	}
	
	public DefaultingAnalogIOSourceCalibrator(AnalogIOCalibrator defaultCalibrator, Map<C, AnalogIOCalibrator> perChannelCalibrators) {
		checkNotNull(defaultCalibrator, "The default calibrator cannot be null.");
		checkNotNull(perChannelCalibrators, "The per-channel calibrator Map cannot be null, although it may be empty.");
		this.defaultCalibrator = defaultCalibrator;
		this.perChannelCalibrators = perChannelCalibrators;
	}
	
	@Override
	public AnalogIOCalibrator forChannel(C channel) {
		final AnalogIOCalibrator c = perChannelCalibrators.get(channel);
		return c!=null ? c : defaultCalibrator;
	}
}
