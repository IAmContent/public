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
package com.iamcontent.device.servo.normal;

import static com.iamcontent.core.math.MathUtils.limit;
import static com.iamcontent.core.math.MathUtils.linearConvert;

import com.iamcontent.device.servo.Servo;

/**
 * A {@link Servo} that normalizes all of its values to the range 0.0..1.0 and invokes a delegate {@link Servo},
 * scaling values linearly.
 * @author Greg Elderfield
 */
public class NormalServo implements Servo {
	
	public static final double MIN = 0.0;
	public static final double MAX = 1.0;
	
	private static final double MIN_TARGET = 4000; // TODO min/max range should be per servo.
	private static final double MAX_TARGET = 8000;
	

	private final Servo servo;
	
	public NormalServo(Servo servo) {
		this.servo = servo;
	}

	@Override
	public int getChannel() {
		return servo.getChannel();
	}
	
	@Override
	public void setPosition(double position) {
		servo.setPosition(toDelegatePosition(position));
	}

	private double toDelegatePosition(double position) {
		return toDelegateValue(position, MIN_TARGET, MAX_TARGET);
	}

	@Override
	public double getPosition() {
		return fromDelegateValue(servo.getPosition(), MIN_TARGET, MAX_TARGET);
	}
	
	@Override
	public void setSpeed(double speed) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setAcceleration(double acceleration) {
		// TODO Auto-generated method stub
	}
	
	private double toDelegateValue(double value, double delegateMin, double delegateMax) {
		return linearConvert(normal(value), MIN, MAX, delegateMin, delegateMax);
	}
	
	private double fromDelegateValue(double value, double delegateMin, double delegateMax) {
		return normal(linearConvert(value, delegateMin, delegateMax, MIN, MAX));
	}

	private static double normal(double value) {
		return limit(value, MIN, MAX);
	}
}
