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
package com.iamcontent.device.controller.pololu.maestro;


/**
 * The minimal set of functions required of a Pololu Maestro card.
 * @author Greg Elderfield
 */
public interface PololuMaestroServoCard {

	interface State {
		short getPosition(short channel);
	}
	
	void setPosition(short channel, short position);
	void setSpeed(short channel, short speed);
	void setAcceleration(short channel, short acceleration);

	short getPosition(short channel);
	State getState();
	
	MaestroCardType getType();

}