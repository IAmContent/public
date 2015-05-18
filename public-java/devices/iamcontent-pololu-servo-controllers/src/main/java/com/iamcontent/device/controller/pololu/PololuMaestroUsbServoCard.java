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
package com.iamcontent.device.controller.pololu;

import static com.iamcontent.io.usb.EasedUsbDevice.eased;
import static javax.usb.UsbConst.REQUESTTYPE_TYPE_VENDOR;

import javax.usb.UsbControlIrp;
import javax.usb.UsbDevice;

import com.iamcontent.io.usb.EasyUsbDevice;
import com.iamcontent.io.usb.Usb;

/**
 * Represents a Pololu Maestro card.
 * @author Greg Elderfield
 */
public class PololuMaestroUsbServoCard {

	public static final short VENDOR_ID = 0x1ffb;
	public static final short MICRO_MAESTRO_PRODUCT_ID = 0x0089;
	
	private static final byte REQUEST_SET_TARGET = (byte)0x85;
	private static final byte REQUEST_SET_SERVO_VARIABLE = (byte)0x84;
	
	private final EasyUsbDevice device;
	
	/**
	 * Creates an instance for the first Pololu Maestro device that is found.
	 */
	public static PololuMaestroUsbServoCard defaultInstance() {
		return new PololuMaestroUsbServoCard();
	}
	
	/**
	 * Creates an instance with the first Pololu Maestro device that is found.
	 */
	public PololuMaestroUsbServoCard() {
		device = Usb.device(VENDOR_ID, MICRO_MAESTRO_PRODUCT_ID);
	}

	/**
	 * Creates an instance with the given UsbDevice.
	 */
	public PololuMaestroUsbServoCard(UsbDevice device) {
		this.device = eased(device);
	}

	public void setRawPosition(short channel, short position) {
		device.syncSubmit(request(REQUEST_SET_TARGET, channel, position));
	}

	public void setRawSpeed(short channel, short speed) {
		device.syncSubmit(request(REQUEST_SET_SERVO_VARIABLE, channel, speed));
	}
	
	public void setRawAcceleration(short channel, short acceleration) {
		final int accelerationFlag = 0x80;
		device.syncSubmit(request(REQUEST_SET_SERVO_VARIABLE, (short)(channel | accelerationFlag), acceleration));
	}
	
	protected UsbControlIrp request(byte request, short index, short value) {
		return device.createUsbControlIrp(REQUESTTYPE_TYPE_VENDOR, request, value, index);
	}
}
