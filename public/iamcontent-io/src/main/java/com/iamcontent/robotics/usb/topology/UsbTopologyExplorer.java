package com.iamcontent.robotics.usb.topology;

import javax.usb.UsbDevice;
import javax.usb.UsbHub;

import com.google.common.base.Predicate;
import com.iamcontent.robotics.usb.Usb;

/**
 * An abstract class that recursively explores the USB devices in a USB topology, starting at 
 * a specific device, or the root USB hub.
 * @author Greg Elderfield
 */
public abstract class UsbTopologyExplorer {
	
	private final Predicate<UsbDevice> shouldVisit;

	/**
	 * Creates an instance.
	 * @param shouldVisit Indicates whether the {@link #visit(UsbDevice)} method should be invoked for a given device.
	 */
	public UsbTopologyExplorer(Predicate<UsbDevice> shouldVisit) {
		this.shouldVisit = shouldVisit;
	}

	/**
	 * Explore the USB topology, starting at the root USB hub. 
	 */
	public void exploreRootUsbHub() {
		explore(Usb.rootUsbHub());
	}
	
	/**
	 * Explore the USB topology, starting at the given device. 
	 */
	public void explore(UsbDevice usbDevice) {
        if (shouldVisit(usbDevice))
            visit(usbDevice);

        if (usbDevice.isUsbHub())
        	exploreDescendants((UsbHub) usbDevice);
    }

	/**
	 * Indicates whether the {@link #visit(UsbDevice)} method should be invoked for a given device.
	 * The default implementation invokes the shouldVisit parameter used to construct this instance.
	 * @return true if the {@link #visit(UsbDevice)} method should be invoked for the given device,
	 * false otherwise.
	 */
	protected boolean shouldVisit(UsbDevice usbDevice) {
		return shouldVisit.apply(usbDevice);
	}
	
	/**
	 * Invoked with any discovered devices for which {@link #shouldVisit(UsbDevice)} returns true.
	 */
	public abstract void visit(UsbDevice usbDevice);

    private void exploreDescendants(UsbHub hub) {
        for (Object d : hub.getAttachedUsbDevices())
        	explore((UsbDevice) d);
    }
}