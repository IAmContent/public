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
import static com.iamcontent.io.usb.topology.UsbDevicePredicates.deviceHasVendorIdAndProductId;
import static javax.usb.UsbConst.REQUESTTYPE_DIRECTION_IN;
import static javax.usb.UsbConst.REQUESTTYPE_DIRECTION_OUT;
import static javax.usb.UsbConst.REQUESTTYPE_TYPE_VENDOR;

import javax.usb.UsbControlIrp;
import javax.usb.UsbDevice;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.iamcontent.device.controller.pololu.maestro.PololuMaestroServoCard;
import com.iamcontent.io.usb.EasyUsbDevice;
import com.iamcontent.io.usb.Usb;

/**
 * A {@link PololuMaestroServoCard} that uses Pololu's Native USB protocol.
 * 
 * The command values used by this protocol were obtained from Pololu's Native USB SDK,
 * downloaded from www.pololu.com.
 * 
 * @author Greg Elderfield
 */
public class UsbPololuMaestroServoCard implements PololuMaestroServoCard {
	
	public static final short VENDOR_ID = 0x1ffb;
	
	public static enum CardType {
		MICRO_MAESTRO_6(0x0089),
		MINI_MAESTRO_12(0x008A),
		MINI_MAESTRO_18(0x008B),
		MINI_MAESTRO_24(0x008C);
		
		private final short productId;

		private CardType(int productId) {
			this.productId = (short)productId;
		}

		public short getProductId() {
			return productId;
		}
	}

	private static final byte REQUEST_SET_TARGET = (byte)0x85;
	private static final byte REQUEST_SET_SERVO_VARIABLE = (byte)0x84;
	private static final byte REQUEST_GET_VARIABLES = (byte)0x83;
	private static final byte REQUEST_GET_SERVO_SETTINGS = (byte)0x87;
	
	private final EasyUsbDevice device;
	
	/**
	 * Creates an instance for the first Pololu Maestro device that is found.
	 */
	public static PololuMaestroServoCard defaultInstance() {
		return new UsbPololuMaestroServoCard();
	}
	
	/**
	 * Creates an instance with the first Pololu Maestro device that is found.
	 */
	public UsbPololuMaestroServoCard() {
		device = Usb.device(isAMaestroUsbDevice());
	}

	/**
	 * Creates an instance with the given UsbDevice.
	 */
	public UsbPololuMaestroServoCard(UsbDevice device) {
		this.device = eased(device);
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
	
	@Override
	public short getRawPosition(short channel) {
		final UsbControlIrp request = inRequest(REQUEST_GET_VARIABLES); // FIXME: This is specific to the Micro Maestro card.
		device.syncSubmit(request);
		final short result = extractPosition(request.getData(), channel);
		return result;
	}

	protected UsbControlIrp inRequest(byte request) {
		final byte requestType = REQUESTTYPE_TYPE_VENDOR | REQUESTTYPE_DIRECTION_IN;
		final UsbControlIrp result = device.createUsbControlIrp(requestType, request, (short)0, (short)0);
		result.setData(new byte[140]);
		return result;
	}
	
	protected UsbControlIrp outRequest(byte request, short index, short value) {
		final byte requestType = REQUESTTYPE_TYPE_VENDOR | REQUESTTYPE_DIRECTION_OUT;
		return device.createUsbControlIrp(requestType, request, value, index);
	}

	private short extractPosition(byte[] data, short channel) {
		final int positionIndex0 = 98;
		final int bytesPerChannel = 7;
		final int loIndex = positionIndex0 + channel * bytesPerChannel;
		return asShort(data[loIndex], data[loIndex + 1]);
	}

	private short asShort(byte lo, byte hi) {
		return (short) ((hi << 8) | (lo & 0xFF));
	}

	@SuppressWarnings("unchecked")
	private static Predicate<UsbDevice> isAMaestroUsbDevice() {
		return Predicates.or(
				isMaestroDevice(CardType.MICRO_MAESTRO_6), 
				isMaestroDevice(CardType.MINI_MAESTRO_12), 
				isMaestroDevice(CardType.MINI_MAESTRO_18), 
				isMaestroDevice(CardType.MINI_MAESTRO_24));
	}

	private static Predicate<UsbDevice> isMaestroDevice(CardType cardType) {
		return deviceHasVendorIdAndProductId(VENDOR_ID, cardType.getProductId());
	}
}
