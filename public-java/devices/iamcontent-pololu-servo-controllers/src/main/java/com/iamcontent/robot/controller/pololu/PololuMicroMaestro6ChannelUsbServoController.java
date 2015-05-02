package com.iamcontent.robot.controller.pololu;

import static com.iamcontent.io.usb.EasedUsbDevice.eased;
import static javax.usb.UsbConst.REQUESTTYPE_TYPE_VENDOR;

import javax.usb.UsbControlIrp;
import javax.usb.UsbDevice;

import com.iamcontent.io.usb.EasyUsbDevice;
import com.iamcontent.io.usb.Usb;

public class PololuMicroMaestro6ChannelUsbServoController {
	
	public static final short VENDOR_ID = 0x1ffb;
	public static final short MICRO_MAESTRO_PRODUCT_ID = 0x0089;
	
	private static final byte REQUEST_SET_TARGET = (byte)0x85;

	private final EasyUsbDevice device;

	/**
	 * Creates an instance with the first Pololu Micro Maestro device that is found.
	 */
	public PololuMicroMaestro6ChannelUsbServoController() {
		device = Usb.device(VENDOR_ID, MICRO_MAESTRO_PRODUCT_ID);
	}

	/**
	 * Creates an instance with the given UsbDevice.
	 */
	public PololuMicroMaestro6ChannelUsbServoController(UsbDevice device) {
		this.device = eased(device);
	}

	public void setServoPosition(int channel, int position) {
		final UsbControlIrp controlRequest = positionRequest((short)channel, (short)position);
		device.syncSubmit(controlRequest);
	}

	private int bits7to13(int i) {
		return (i >> 7) & 0x7F;
	}

	private int bits0to6(int i) {
		return i & 0x7F;
	}

	private static byte[] bytes(int... vals) {
		final int length = vals.length;
		final byte[] result = new byte[length];
		for (int i=0; i<length; i++)
			result[i] = (byte)vals[i];
		return result;
	}

	protected UsbControlIrp positionRequest(short index, short value) {
		return request(REQUEST_SET_TARGET, index, value);
	}

	private UsbControlIrp request(byte request, short index, short value) {
		return device.createUsbControlIrp(REQUESTTYPE_TYPE_VENDOR, request, value, index);
	}
}
