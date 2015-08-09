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
package com.iamcontent.io.usb.topology;

import static com.iamcontent.io.usb.EasedUsbDevice.eased;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.usb.UsbDevice;

import com.google.common.base.Predicate;
import com.iamcontent.io.usb.EasyUsbDevice;

/**
 * Explores a USB topology and composes a list of the devices that match the given predicate.
 * @author Greg Elderfield
 */
public class UsbDeviceFinder extends UsbTopologyExplorer {

	public UsbDeviceFinder(Predicate<UsbDevice> shouldIncludeDevice) {
		super(shouldIncludeDevice);
	}
	
	public static UsbDeviceFinder usbDeviceFinder(Predicate<UsbDevice> shouldIncludeDevice) {
		return new UsbDeviceFinder(shouldIncludeDevice);
	}

	private final List<EasyUsbDevice> devices = new ArrayList<EasyUsbDevice>();
	
	@Override
	public void visit(UsbDevice usbDevice) {
		devices.add(eased(usbDevice));
	}

	public List<EasyUsbDevice> getDevices() {
		return Collections.unmodifiableList(devices);
	}
}
