/**
  IAmContent Public Libraries.
  Copyright (C) 2015 Greg Elderfield
  @author Greg Elderfield, iamcontent@jarchitect.co.uk
 
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

import com.iamcontent.core.lang.Delegator;

/**
 * A class to represent an object that invokes a delegate and has its own channel id.
 * @author Greg Elderfield
 * 
 * @param <D> The type of the delegate. 
 * @param <C> The type used to identify the channel of the {@link ChannelSpecificDelegator}. 
 */
public class ChannelSpecificDelegator<D, C> extends Delegator<D> implements ChannelSpecific<C> {
	private final C channel;
	
	public ChannelSpecificDelegator(D delegate, C channel) {
		super(delegate);
		this.channel = channel;
	}

	@Override
	public C getChannelId() {
		return channel;
	}
}
