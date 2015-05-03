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

/**
 * Represents the operations that can be performed on a Servo.
 * @author Greg Elderfield
 */
public interface Servo {
	
	/**
	 * Sets the relative position.
	 * @param position The relative position, between 0.0 and 1.0.
	 */
	void setPosition(double position);
	
	/**
	 * Gets the relative position.
	 * @return The relative position, between 0.0 and 1.0.
	 */
	double getPosition();
	
	/**
	 * Sets the relative speed.
	 * @param speed The relative speed, between 0.0 and 1.0. A value of zero or less, being treated the same as 1 (i.e. full speed)
	 */
	void setSpeed(double speed);
	
	/**
	 * Sets the relative acceleration.
	 * @param acceleration The relative acceleration, between 0.0 and 1.0. A value of zero or less, being treated the same as 1 (i.e. full acceleration)
	 */
	void setAcceleration(double acceleration);
}
