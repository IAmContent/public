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
package com.iamcontent.device.servo.channel;

import com.iamcontent.device.channel.ChannelSpecificDelegator;
import com.iamcontent.device.servo.Servo;

/**
 * A {@link Servo} that invokes a delegate {@link Servo} but has its own channel id, which may be different from its delegate
 * and might not even have the same channel class.
 * @author Greg Elderfield
 * 
 * @param <C> The type used to identify the channel of a {@link ReChanneledServo}. 
 */
public class ReChanneledServo<C> extends ChannelSpecificDelegator<Servo<?>, C> implements Servo<C> {
	
	public ReChanneledServo(Servo<?> delegateServo, C channel) {
		super(delegateServo, channel);
	}

	@Override
	public void setPosition(double position) {
		delegate().setPosition(position);
	}

	@Override
	public double getPosition() {
		return delegate().getPosition();
	}

	@Override
	public void setSpeed(double speed) {
		delegate().setSpeed(speed);
	}

	@Override
	public void setAcceleration(double acceleration) {
		delegate().setAcceleration(acceleration);
	}
}
