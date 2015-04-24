package com.iamcontent.robotics.usb.topology;

import javax.usb.UsbDevice;
import javax.usb.UsbDeviceDescriptor;

import com.google.common.base.Predicate;

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
			public boolean apply(UsbDevice device) {
		        return isAMatch(device.getUsbDeviceDescriptor());
		    }

			private boolean isAMatch(final UsbDeviceDescriptor descriptor) {
				return vendorId == descriptor.idVendor()
		            && productId == descriptor.idProduct();
			}
        };
    }
}
