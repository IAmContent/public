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
package com.iamcontent.device.controller.pololu.maestro;

import com.iamcontent.device.servo.raw.ServoController;

/**
 * Wraps a {@link PololuMaestroServoCard}, presenting it as a {@link ServoController}.
 * @author Greg Elderfield
 */
public class PololuMaestroServoController implements ServoController {

	private final PololuMaestroServoCard card;
	
	public static PololuMaestroServoController pololuMaestroServoController(PololuMaestroServoCard card) {
		return new PololuMaestroServoController(card);
	}
	
	public PololuMaestroServoController(PololuMaestroServoCard card) {
		this.card = card;
	}

	@Override
	public void setPosition(int channel, double value) {
		card.setRawPosition((short)channel, (short)value);
	}
	
	@Override
	public double getPosition(int channel) {
		return card.getRawPosition((short)channel);
	}

	@Override
	public void setSpeed(int channel, double speed) {
		card.setRawSpeed((short)channel, (short)speed);
	}

	@Override
	public void setAcceleration(int channel, double acceleration) {
		card.setRawAcceleration((short)channel, (short)acceleration);
	}
}
