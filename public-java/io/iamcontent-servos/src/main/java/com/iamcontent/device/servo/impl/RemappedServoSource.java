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
package com.iamcontent.device.servo.impl;

import java.util.function.Function;

import com.iamcontent.core.lang.Delegator;
import com.iamcontent.device.servo.Servo;
import com.iamcontent.device.servo.ServoSource;

/**
 * A {@link ServoSource} that has new channel ids, as defined by a mapping function.
 * 
 * @author Greg Elderfield
 */
public class RemappedServoSource<NewChannelId, DelegateChannelId> extends Delegator<ServoSource<DelegateChannelId>> implements ServoSource<NewChannelId> {
	
	protected final Function<NewChannelId, DelegateChannelId> remapping;
	
	public RemappedServoSource(ServoSource<DelegateChannelId> target, Function<NewChannelId, DelegateChannelId> remapping) {
		super(target);
		this.remapping = remapping;
	}

	@Override
	public Servo forChannel(NewChannelId channelId) {
		return delegate().forChannel(remapping.apply(channelId));
	}
}
