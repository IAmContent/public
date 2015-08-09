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
package com.iamcontent.io.usb;

import java.util.List;

import javax.usb.UsbConfiguration;
import javax.usb.UsbControlIrp;
import javax.usb.UsbDevice;
import javax.usb.UsbDeviceDescriptor;
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
		if (device instanceof EasyUsbDevice) 
			return (EasedUsbDevice) device;
		else
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
		} catch (Exception e) {
			throw new UsbRuntimeException(e);
		}
	}

	@Override
	public String getSerialNumberString() {
		try {
			return device.getSerialNumberString();
		} catch (Exception e) {
			throw new UsbRuntimeException(e);
		}
	}

	@Override
	public String getProductString() {
		try {
			return device.getProductString();
		} catch (Exception e) {
			throw new UsbRuntimeException(e);
		}
	}

	@Override
	public UsbStringDescriptor getUsbStringDescriptor(byte index) {
		try {
			return device.getUsbStringDescriptor(index);
		} catch (Exception e) {
			throw new UsbRuntimeException(e);
		}
	}

	@Override
	public String getString(byte index) {
		try {
			return device.getString(index);
		} catch (Exception e) {
			throw new UsbRuntimeException(e);
		}
	}

	@Override
	public void syncSubmit(UsbControlIrp irp) {
		try {
			device.syncSubmit(irp);
		} catch (Exception e) {
			throw new UsbRuntimeException(e);
		}
	}

	@Override
	public void asyncSubmit(UsbControlIrp irp) {
		try {
			device.asyncSubmit(irp);
		} catch (Exception e) {
			throw new UsbRuntimeException(e);
		}
	}

	@Override
	public void syncSubmit(List list) {
		try {
			device.syncSubmit(list);
		} catch (Exception e) {
			throw new UsbRuntimeException(e);
		}
	}

	@Override
	public void asyncSubmit(List list) {
		try {
			device.asyncSubmit(list);
		} catch (Exception e) {
			throw new UsbRuntimeException(e);
		}
	}

	@Override
	public String toString() {
		return device.toString();
	}
}
