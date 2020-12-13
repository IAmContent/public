/**
  IAmContent Public Libraries.
  Copyright (C) 2015-2021 Greg Elderfield
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
package com.iamcontent.robot.robonova1.custom1;

import com.iamcontent.core.geom.Geometry.ThreeDimension;
import com.iamcontent.device.io.analog.AnalogIOSource;
import com.iamcontent.device.servo.ServoSource;
import com.iamcontent.robot.robonova1.Robonova;
import com.iamcontent.robot.robonova1.ServoId;

/**
 * A specific implementation of Greg Elderfield's Robonova-1.
 * @author Greg Elderfield
 */
public class CustomRobonova implements Robonova {

	@Override
	public ServoSource<ServoId> servos() {
		return CustomRobonovaServos.servoSource();
	}

	@Override
	public AnalogIOSource<ThreeDimension> accelerometer() {
		return CustomRobonovaAccelerometer.accelerometer();
	}
}
