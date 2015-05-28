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
	SHOULDER_FLEXOR_LEFT,
	SHOULDER_FLEXOR_RIGHT,
	SHOULDER_ABDUCTOR_LEFT,
	SHOULDER_ABDUCTOR_RIGHT,
	ELBOW_FLEXOR_LEFT,
	ELBOW_FLEXOR_RIGHT,
	
	HIP_ABDUCTOR_LEFT,
	HIP_ABDUCTOR_RIGHT,
	HIP_FLEXOR_LEFT,
	HIP_FLEXOR_RIGHT,
	KNEE_FLEXOR_LEFT,
	KNEE_FLEXOR_RIGHT,
	ANKLE_FLEXOR_LEFT,
	ANKLE_FLEXOR_RIGHT,
	FOOT_INVERTER_LEFT,
	FOOT_INVERTER_RIGHT;
}
