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
package com.iamcontent.device.controller.pololu.maestro.usb;

import static com.iamcontent.io.usb.EasedUsbDevice.eased;
import static javax.usb.UsbConst.REQUESTTYPE_DIRECTION_IN;
import static javax.usb.UsbConst.REQUESTTYPE_DIRECTION_OUT;
import static javax.usb.UsbConst.REQUESTTYPE_TYPE_VENDOR;

import javax.usb.UsbControlIrp;
import javax.usb.UsbDevice;

import com.iamcontent.device.controller.pololu.maestro.MaestroCardType;
import com.iamcontent.device.controller.pololu.maestro.PololuMaestroServoCard;
import com.iamcontent.io.usb.EasyUsbDevice;

/**
 * An abstract {@link PololuMaestroServoCard} that uses Pololu's Native USB protocol.
 * Methods with implementations that vary by card type must be provided by concrete subclasses.
 * 
 * The command values used by this protocol were obtained from Pololu's Native USB SDK,
 * downloaded from www.pololu.com.
 * 
 * @author Greg Elderfield
 */
public abstract class AbstractUsbPololuMaestroServoCard implements PololuMaestroServoCard {
	
	private static final byte REQUEST_SET_TARGET = (byte)0x85;
	private static final byte REQUEST_SET_SERVO_VARIABLE = (byte)0x84;
	
	protected static final int BYTES_PER_SERVO_STATUS_BLOCK = 7;

	protected final EasyUsbDevice device;
	protected final UsbMaestroCardType type;
	
	/**
	 * Creates an instance with the given UsbDevice.
	 */
	public AbstractUsbPololuMaestroServoCard(UsbDevice device) {
		this.device = eased(device);
		this.type = UsbMaestroCardType.forUsbDeviceOrThrow(device);
	}

	@Override
	public void setRawPosition(short channel, short position) {
		device.syncSubmit(outRequest(REQUEST_SET_TARGET, channel, position));
	}

	@Override
	public void setRawSpeed(short channel, short speed) {
		device.syncSubmit(outRequest(REQUEST_SET_SERVO_VARIABLE, channel, speed));
	}
	
	@Override
	public void setRawAcceleration(short channel, short acceleration) {
		final int accelerationFlag = 0x80;
		device.syncSubmit(outRequest(REQUEST_SET_SERVO_VARIABLE, (short)(channel | accelerationFlag), acceleration));
	}
	
	public MaestroCardType getType() {
		return type.getType();
	}
	
	protected int getChannelCount() {
		return getType().getChannelCount();
	}

	protected UsbControlIrp inRequest(byte request, int dataSize) {
		final byte requestType = REQUESTTYPE_TYPE_VENDOR | REQUESTTYPE_DIRECTION_IN;
		final UsbControlIrp result = device.createUsbControlIrp(requestType, request, (short)0, (short)0);
		result.setData(new byte[dataSize]);
		return result;
	}
	
	protected UsbControlIrp outRequest(byte request, short index, short value) {
		final byte requestType = REQUESTTYPE_TYPE_VENDOR | REQUESTTYPE_DIRECTION_OUT;
		return device.createUsbControlIrp(requestType, request, value, index);
	}

	protected int sizeOfAllServoStatusBlocks() {
		return getChannelCount() * BYTES_PER_SERVO_STATUS_BLOCK;
	}

	protected static short extractPositionFromStatusBlocks(byte[] data, int offset, short channel) {
		final int loIndex = offset + channel * BYTES_PER_SERVO_STATUS_BLOCK;
		return asShort(data[loIndex], data[loIndex + 1]);
	}
	
	protected static short asShort(byte lo, byte hi) {
		return (short) ((hi << 8) | (lo & 0xFF));
	}
}
