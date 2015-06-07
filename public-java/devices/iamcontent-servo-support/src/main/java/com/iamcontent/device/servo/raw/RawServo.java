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
package com.iamcontent.device.servo.raw;

import com.iamcontent.device.servo.Servo;

/**
 * A {@link Servo} that directly delegates its operations to a {@link ServoController} without altering the
 * arguments of the operations.
 * @author Greg Elderfield
 * 
 * @param <C> The type used to identify the channel of a servo. 
 */
public class RawServo<C> implements Servo<C> {
	
	private final ServoController<C> controller;
	private final C channel;
	
	public RawServo(ServoController<C> controller, C channel) {
		this.controller = controller;
		this.channel = channel;
	}

	@Override
	public C getChannel() {
		return channel;
	}

	@Override
	public void setPosition(double position) {
		controller.setPosition(channel, position);
	}

	@Override
	public double getPosition() {
		return controller.getPosition(channel);
	}

	@Override
	public void setSpeed(double speed) {
		controller.setSpeed(channel, speed);
	}

	@Override
	public void setAcceleration(double acceleration) {
		controller.setAcceleration(channel, acceleration);
	}
}
