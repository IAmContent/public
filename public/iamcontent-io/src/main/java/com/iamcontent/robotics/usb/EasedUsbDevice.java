package com.iamcontent.robotics.usb;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.usb.UsbConfiguration;
import javax.usb.UsbControlIrp;
import javax.usb.UsbDevice;
import javax.usb.UsbDeviceDescriptor;
import javax.usb.UsbDisconnectedException;
import javax.usb.UsbException;
import javax.usb.UsbPort;
import javax.usb.UsbStringDescriptor;
import javax.usb.event.UsbDeviceListener;

/**
 * Wraps a UsbDevice as a {@link EasyUsbDevice}, i.e. as a UsbDevice that throws no checked exceptions.
 * 
 * @author Greg Elderfield
 */
@SuppressWarnings("rawtypes")
public class EasedUsbDevice implements EasyUsbDevice {

	private final UsbDevice device;

	public EasedUsbDevice(UsbDevice device) {
		this.device = device;
	}
	
	public static EasedUsbDevice eased(UsbDevice device) {
		return new EasedUsbDevice(device);
	}

	@Override
	public boolean isUsbHub() {
		return device.isUsbHub();
	}

	@Override
	public Object getSpeed() {
		return device.getSpeed();
	}

	@Override
	public List getUsbConfigurations() {
		return device.getUsbConfigurations();
	}

	@Override
	public UsbConfiguration getUsbConfiguration(byte number) {
		return device.getUsbConfiguration(number);
	}

	@Override
	public boolean containsUsbConfiguration(byte number) {
		return device.containsUsbConfiguration(number);
	}

	@Override
	public byte getActiveUsbConfigurationNumber() {
		return device.getActiveUsbConfigurationNumber();
	}

	@Override
	public UsbConfiguration getActiveUsbConfiguration() {
		return device.getActiveUsbConfiguration();
	}

	@Override
	public boolean isConfigured() {
		return device.isConfigured();
	}

	@Override
	public UsbDeviceDescriptor getUsbDeviceDescriptor() {
		return device.getUsbDeviceDescriptor();
	}

	@Override
	public UsbControlIrp createUsbControlIrp(byte bmRequestType, byte bRequest, short wValue, short wIndex) {
		return device.createUsbControlIrp(bmRequestType, bRequest, wValue, wIndex);
	}

	@Override
	public void addUsbDeviceListener(UsbDeviceListener listener) {
		device.addUsbDeviceListener(listener);
	}

	@Override
	public void removeUsbDeviceListener(UsbDeviceListener listener) {
		device.removeUsbDeviceListener(listener);
	}

	@Override
	public UsbPort getParentUsbPort() {
		return device.getParentUsbPort();
	}

	@Override
	public String getManufacturerString() {
		try {
			return device.getManufacturerString();
		} catch (UnsupportedEncodingException | UsbDisconnectedException | UsbException e) {
			throw new UsbRuntimeException(e);
		}
	}

	@Override
	public String getSerialNumberString() {
		try {
			return device.getSerialNumberString();
		} catch (UnsupportedEncodingException | UsbDisconnectedException | UsbException e) {
			throw new UsbRuntimeException(e);
		}
	}

	@Override
	public String getProductString() {
		try {
			return device.getProductString();
		} catch (UnsupportedEncodingException | UsbDisconnectedException | UsbException e) {
			throw new UsbRuntimeException(e);
		}
	}

	@Override
	public UsbStringDescriptor getUsbStringDescriptor(byte index) {
		try {
			return device.getUsbStringDescriptor(index);
		} catch (UsbDisconnectedException | UsbException e) {
			throw new UsbRuntimeException(e);
		}
	}

	@Override
	public String getString(byte index) {
		try {
			return device.getString(index);
		} catch (UnsupportedEncodingException | UsbDisconnectedException | UsbException e) {
			throw new UsbRuntimeException(e);
		}
	}

	@Override
	public void syncSubmit(UsbControlIrp irp) {
		try {
			device.syncSubmit(irp);
		} catch (IllegalArgumentException | UsbDisconnectedException | UsbException e) {
			throw new UsbRuntimeException(e);
		}
	}

	@Override
	public void asyncSubmit(UsbControlIrp irp) {
		try {
			device.asyncSubmit(irp);
		} catch (IllegalArgumentException | UsbDisconnectedException | UsbException e) {
			throw new UsbRuntimeException(e);
		}
	}

	@Override
	public void syncSubmit(List list) {
		try {
			device.syncSubmit(list);
		} catch (IllegalArgumentException | UsbDisconnectedException | UsbException e) {
			throw new UsbRuntimeException(e);
		}
	}

	@Override
	public void asyncSubmit(List list) {
		try {
			device.asyncSubmit(list);
		} catch (IllegalArgumentException | UsbDisconnectedException | UsbException e) {
			throw new UsbRuntimeException(e);
		}
	}

	@Override
	public String toString() {
		return device.toString();
	}
}
