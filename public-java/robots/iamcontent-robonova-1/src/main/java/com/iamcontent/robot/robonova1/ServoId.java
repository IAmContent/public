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
package com.iamcontent.robot.robonova1;

/**
 * Identifies the different servos on the Robonova.
 * @author Greg Elderfield
 */
public enum ServoId {
	LEFT_SHOULDER_FLEXOR,
	RIGHT_SHOULDER_FLEXOR,
	LEFT_SHOULDER_ABDUCTOR,
	RIGHT_SHOULDER_ABDUCTOR,
	LEFT_ELBOW_FLEXOR,
	RIGHT_ELBOW_FLEXOR,
	
	LEFT_HIP_ABDUCTOR,
	RIGHT_HIP_ABDUCTOR,
	LEFT_HIP_FLEXOR,
	RIGHT_HIP_FLEXOR,
	LEFT_KNEE_FLEXOR,
	RIGHT_KNEE_FLEXOR,
	LEFT_ANKLE_FLEXOR,
	RIGHT_ANKLE_FLEXOR,
	LEFT_FOOT_INVERTER,
	RIGHT_FOOT_INVERTER;
}
