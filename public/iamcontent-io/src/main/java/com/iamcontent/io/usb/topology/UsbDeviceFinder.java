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
