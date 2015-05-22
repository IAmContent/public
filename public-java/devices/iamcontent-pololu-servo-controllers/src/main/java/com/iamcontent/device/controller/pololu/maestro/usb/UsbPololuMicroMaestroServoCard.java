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
package com.iamcontent.device.controller.pololu.maestro.usb;

import javax.usb.UsbControlIrp;
import javax.usb.UsbDevice;

import com.iamcontent.device.controller.pololu.maestro.PololuMaestroServoCard;

/**
 * A {@link PololuMaestroServoCard} for Micro Maestro cards, using Pololu's Native USB protocol.
 * 
 * The command values used by this protocol were obtained from Pololu's Native USB SDK,
 * downloaded from www.pololu.com.
 * 
 * @author Greg Elderfield
 */
public class UsbPololuMicroMaestroServoCard extends AbstractUsbPololuMaestroServoCard {

	private static final byte REQUEST_GET_VARIABLES = (byte)0x83;
	
	public UsbPololuMicroMaestroServoCard(UsbDevice device) {
		super(device);
	}

	@Override
	public short getRawPosition(short channel) {
		final int offset = 98;
		final int dataSize = offset + sizeOfAllServoStatusBlocks();
		final UsbControlIrp request = inRequest(REQUEST_GET_VARIABLES, dataSize);
		device.syncSubmit(request);
		return extractPositionFromStatusBlocks(request.getData(), offset, channel);
	}
}
