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
package com.iamcontent.device.servo.command;

import com.iamcontent.device.servo.Servo;
import com.iamcontent.device.servo.ServoSource;

/**
 * Represents a single command that can be performed by a {@link ServoSource} on a {@link Servo}. 
 * @author Greg Elderfield
 * 
 * @param <C> The type used to identify the channel of a servo. 
 */
public interface ServoCommand<C> {
	/**
	 * @return The channel of the {@link Servo}.
	 */
	C getChannel();
	
	/**
	 * @return The relative position for the {@link Servo}, between 0.0 and 1.0, or null if the position is not changed by this command
	 */
	Double getPosition();
	
	/**
	 * @return The relative speed for the {@link Servo}, between 0.0 and 1.0, or null if the speed is not changed by this command
	 */
	Double getSpeed();
	
	/**
	 * @return The relative acceleration for the {@link Servo}, between 0.0 and 1.0, or null if the acceleration is not changed by this command
	 */
	Double getAcceleration();
}
