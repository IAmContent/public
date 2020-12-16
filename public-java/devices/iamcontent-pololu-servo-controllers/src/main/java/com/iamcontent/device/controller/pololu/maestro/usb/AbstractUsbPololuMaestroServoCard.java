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
package com.iamcontent.device.controller.pololu.maestro.usb;

import static com.iamcontent.io.usb.EasedUsbDevice.eased;
import static java.util.stream.Collectors.joining;
import static javax.usb.UsbConst.REQUESTTYPE_DIRECTION_IN;
import static javax.usb.UsbConst.REQUESTTYPE_DIRECTION_OUT;
import static javax.usb.UsbConst.REQUESTTYPE_TYPE_VENDOR;

import java.io.Serializable;
import java.util.stream.IntStream;

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
	
	private static final int BYTES_PER_SERVO_STATUS_BLOCK = 7;

	private final EasyUsbDevice device;
	private final UsbMaestroCardType type;
	private final byte[] dataIn;
	private final byte inRequestCode;
	private final int offsetOfFirstServoStatusBlock;
	
	/**
	 * Creates an instance with the given UsbDevice.
	 */
	public AbstractUsbPololuMaestroServoCard(UsbDevice device, byte inRequestCode, int offsetOfFirstServoStatusBlock) {
		this.device = eased(device);
		this.type = UsbMaestroCardType.cardTypeForUsbDeviceOrThrow(device);
		this.inRequestCode = inRequestCode;
		this.offsetOfFirstServoStatusBlock = offsetOfFirstServoStatusBlock;
		this.dataIn = new byte[offsetOfFirstServoStatusBlock + sizeOfAllServoStatusBlocks()];
	}

	@Override
	public void setPosition(short channel, short position) {
		device.syncSubmit(outRequest(REQUEST_SET_TARGET, chan(channel), position));
	}

	@Override
	public void setSpeed(short channel, short speed) {
		device.syncSubmit(outRequest(REQUEST_SET_SERVO_VARIABLE, chan(channel), speed));
	}
	
	@Override
	public void setAcceleration(short channel, short acceleration) {
		final int accelerationFlag = 0x80;
		device.syncSubmit(outRequest(REQUEST_SET_SERVO_VARIABLE, (short)(chan(channel) | accelerationFlag), acceleration));
	}
	
	@Override
	public short getPosition(short channel) {
		return getState().getPosition(chan(channel));
	}
	
	@Override
	public State getState() {
		final UsbControlIrp request = inRequest(inRequestCode);
		device.syncSubmit(request);
		return new PololuState(request.getData());
	}

	@Override
	public MaestroCardType getType() {
		return type.getType();
	}
	
	protected int channelCount() {
		return getType().channelCount();
	}

	protected UsbControlIrp inRequest(byte request) {
		final byte requestType = REQUESTTYPE_TYPE_VENDOR | REQUESTTYPE_DIRECTION_IN;
		final UsbControlIrp result = device.createUsbControlIrp(requestType, request, (short)0, (short)0);
		result.setData(dataIn);
		return result;
	}
	
	protected UsbControlIrp outRequest(byte request, short index, short value) {
		final byte requestType = REQUESTTYPE_TYPE_VENDOR | REQUESTTYPE_DIRECTION_OUT;
		return device.createUsbControlIrp(requestType, request, value, index);
	}

	protected int sizeOfAllServoStatusBlocks() {
		return channelCount() * BYTES_PER_SERVO_STATUS_BLOCK;
	}

	protected static short asShort(byte lo, byte hi) {
		return (short) ((hi << 8) | (lo & 0xFF));
	}
	
	protected short chan(short c) {
		checkChannelIndex(c);
		return c;
	}
	
	protected class PololuState implements State, Serializable {
		private final byte[] data;
		
		public PololuState(byte[] data) {
			this.data = data;
		}

		@Override
		public short getPosition(short channel) {
			final int loIndex = offsetOfFirstServoStatusBlock + chan(channel) * BYTES_PER_SERVO_STATUS_BLOCK;
			return asShort(data[loIndex], data[loIndex + 1]);
		}
		
		@Override
		public String toString() {
			final String prefix = "PololuState [positions=[";
			final String channelDelimiter = ", ";
			final String suffix = "PololuState [positions=[";
			return IntStream.range(0, channelCount()).mapToObj(c -> "" + getPosition((short) c)).collect(joining(channelDelimiter, prefix, suffix));
		}

		private static final long serialVersionUID = 1L;
	}
	
	protected void checkChannelIndex(short i) {
		if (i < 0 || i >= channelCount()) {
			throw new IndexOutOfBoundsException("Invalid channel index: " + i);
		}
	}
}
