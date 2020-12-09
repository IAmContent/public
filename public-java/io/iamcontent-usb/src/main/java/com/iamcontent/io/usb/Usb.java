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
package com.iamcontent.io.usb;

import static com.iamcontent.io.usb.UsbDevicePredicates.deviceHasVendorIdAndProductId;
import static com.iamcontent.io.usb.topology.UsbDeviceFinder.usbDeviceFinder;

import java.util.List;

import javax.usb.UsbDevice;
import javax.usb.UsbHostManager;
import javax.usb.UsbHub;
import javax.usb.UsbServices;

import com.google.common.base.Predicate;
import com.iamcontent.io.usb.topology.UsbDeviceFinder;

/**
 * USB utility methods.
 * @author Greg Elderfield
 */
public class Usb {

    public static EasyUsbDevice device(short vendorId, short productId) {
        return first(devices(vendorId, productId));
    }

    public static EasyUsbDevice device(Predicate<UsbDevice> shouldIncludeDevice) {
        return first(devices(shouldIncludeDevice));
    }

    public static List<EasyUsbDevice> devices(short vendorId, short productId) {
    	return devices(deviceHasVendorIdAndProductId(vendorId, productId));
    }

    public static List<EasyUsbDevice> devices(Predicate<UsbDevice> shouldIncludeDevice) {
        final UsbDeviceFinder deviceFinder = usbDeviceFinder(shouldIncludeDevice);
        deviceFinder.exploreRootUsbHub();
		return deviceFinder.getDevices();
    }

    public static UsbHub rootUsbHub() {
        try {
            return usbServices().getRootUsbHub();
        } catch (Exception e) {
            throw new UsbRuntimeException(e);
        }
    }

    public static UsbServices usbServices() {
        try {
            return UsbHostManager.getUsbServices();
        } catch (Exception e) {
            throw new UsbRuntimeException(e);
        }
    }

    private static <T> T first(List<T> l) {
        try {
            return l.get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new UsbRuntimeException("No suitable USB Device found.");
        }
    }
    
    private Usb() {
    }
}