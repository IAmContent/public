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
package com.iamcontent.device.servo;

import static com.iamcontent.core.MathUtils.limit;

/**
 * A {@link Servo} that normalizes all of its values to the range 0.0..1.0.
 * Implementations should invoke the abstract setLimited*() methods, which are invoked by the set*() methods
 * with the parameter value limited to the range 0.0..1.0.
 * @author Greg Elderfield
 */
public abstract class NormalizingServo implements Servo {
	
	public static final double MIN = 0.0;
	public static final double MAX = 1.0;

	@Override
	public void setPosition(double position) {
		setLimitedPosition(limited(position));
	}

	@Override
	public void setSpeed(double speed) {
		setLimitedSpeed(limited(speed));
	}

	@Override
	public void setAcceleration(double acceleration) {
		setLimitedAcceleration(limited(acceleration));
	}

	public abstract void setLimitedPosition(double position);
	public abstract void setLimitedSpeed(double speed);
	public abstract void setLimitedAcceleration(double acceleration);
	
	private static double limited(double value) {
		return limit(value, MIN, MAX);
	}
}
