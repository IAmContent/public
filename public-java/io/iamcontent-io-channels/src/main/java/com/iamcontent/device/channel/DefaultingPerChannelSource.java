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
package com.iamcontent.device.channel;

import java.util.HashMap;
import java.util.Map;

/**
 * A {@link PerChannelSource} that allows a default value to be set and over-ridden on a per-channel basis.
 * @author Greg Elderfield
 * 
 * @param <C> The type used to identify a channel. 
 * @param <T> The type that can be returned for each channel. 
 * @param <SELF> The type of 'this' instance.
 */
public class DefaultingPerChannelSource<C, T, SELF extends DefaultingPerChannelSource<C, T, SELF>> implements PerChannelSource<C, T> {

	private final T defaultValue;
	private final Map<C, T> perChannelValues = new HashMap<>();
	
	public DefaultingPerChannelSource(T defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	@SuppressWarnings("unchecked")
	public SELF put(C channel, T value) {
		this.perChannelValues.put(channel, value);
		return (SELF)this;
	}
	
	@Override
	public T forChannel(C channel) {
		return perChannelValues.getOrDefault(channel, defaultValue);
	}
}