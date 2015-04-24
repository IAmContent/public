package com.iamcontent.io.usb;

import java.util.List;

import javax.usb.UsbControlIrp;
import javax.usb.UsbDevice;
import javax.usb.UsbStringDescriptor;

/**
 * A UsbDevice that does not throw checked exceptions.
 * @author Greg Elderfield
 */

@SuppressWarnings("rawtypes")
public interface EasyUsbDevice extends UsbDevice {
	String getManufacturerString();

	String getSerialNumberString();

	String getProductString();

	UsbStringDescriptor getUsbStringDescriptor(byte index);

	String getString(byte index);

	void syncSubmit(UsbControlIrp irp);

	void asyncSubmit(UsbControlIrp irp);

	void syncSubmit(List list);

	void asyncSubmit(List list);
}