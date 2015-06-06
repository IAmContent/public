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
public class PololuMaestroServoController implements ServoController<Integer> {

	public static final String DEFAULT_CALIBRATOR_NAME = "pololu-maestro-calibration";

	private final PololuMaestroServoCard card;
	
	public static PololuMaestroServoController pololuMaestroServoController(PololuMaestroServoCard card) {
		return new PololuMaestroServoController(card);
	}
	
	public PololuMaestroServoController(PololuMaestroServoCard card) {
		this.card = card;
	}

	@Override
	public void setPosition(Integer channel, double value) {
		card.setRawPosition(asShort(channel), (short)value);
	}

	@Override
	public double getPosition(Integer channel) {
		return card.getRawPosition(asShort(channel));
	}

	@Override
	public void setSpeed(Integer channel, double speed) {
		card.setRawSpeed(asShort(channel), (short)speed);
	}

	@Override
	public void setAcceleration(Integer channel, double acceleration) {
		card.setRawAcceleration(asShort(channel), (short)acceleration);
	}
	
	private static short asShort(Integer i) {
		return (short) i.intValue();
	}
}
