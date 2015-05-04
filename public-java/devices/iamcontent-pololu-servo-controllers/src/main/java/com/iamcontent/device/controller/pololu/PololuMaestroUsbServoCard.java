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

import static com.iamcontent.device.servo.raw.RawServoSource.rawServoSource;
import static com.iamcontent.io.usb.EasedUsbDevice.eased;
import static javax.usb.UsbConst.REQUESTTYPE_TYPE_VENDOR;

import javax.usb.UsbControlIrp;
import javax.usb.UsbDevice;

import com.iamcontent.device.servo.ServoSource;
import com.iamcontent.device.servo.raw.ServoController;
import com.iamcontent.io.usb.EasyUsbDevice;
import com.iamcontent.io.usb.Usb;

/**
 * Represents a Pololu Maestro card.
 * @author Greg Elderfield
 */
public class PololuMaestroUsbServoCard implements ServoController {

	public static final short VENDOR_ID = 0x1ffb;
	public static final short MICRO_MAESTRO_PRODUCT_ID = 0x0089;
	
	private static final byte REQUEST_SET_TARGET = (byte)0x85;
	
	private final EasyUsbDevice device;
	
	/**
	 * Creates a {@link ServoSource} for the first Pololu Maestro device that is found.
	 */
	public static ServoSource defaultServoSource() {
		return rawServoSource(defaultInstance());
	}
	
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

	/**
	 * Sets the raw position value of a specific servo.
	 * @param channel The channel number of the servo.
	 * @param value The raw position value.
	 */
	public void setTarget(short channel, short value) {
		device.syncSubmit(request(REQUEST_SET_TARGET, channel, value));
	}

	protected UsbControlIrp request(byte request, short index, short value) {
		return device.createUsbControlIrp(REQUESTTYPE_TYPE_VENDOR, request, value, index);
	}

	@Override
	public void setPosition(int channel, double value) {
		setTarget((short)channel, (short)value);
	}
}
