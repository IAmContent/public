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

import java.util.function.Predicate;

import javax.usb.UsbDevice;
import javax.usb.UsbDeviceDescriptor;

/**
 * Predicates for testing {@link UsbDevice}s.
 * @author Greg Elderfield
 */
public class UsbDevicePredicates {

	/**
	 * A Predicate that tests whether a {@link UsbDevice} has the given vendor id and product id.
	 */
    public static Predicate<UsbDevice> deviceHasVendorIdAndProductId(final short vendorId, final short productId) {
        return new Predicate<UsbDevice>() {
			public boolean test(UsbDevice device) {
		        return vendorAndProductIdsMatch(vendorId, productId, device);
		    }
        };
    }
    
    public static boolean vendorAndProductIdsMatch(short vendorId, short productId, UsbDevice device) {
    	return vendorAndProductIdsMatch(vendorId, productId, device.getUsbDeviceDescriptor());
    }
    
    public static boolean vendorAndProductIdsMatch(short vendorId, short productId, UsbDeviceDescriptor descriptor) {
    	return vendorId == descriptor.idVendor() && productId == descriptor.idProduct();
    }
    
    private UsbDevicePredicates() {
    }
}
