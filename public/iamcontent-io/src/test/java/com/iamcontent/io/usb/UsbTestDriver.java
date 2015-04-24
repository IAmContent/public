package com.iamcontent.io.usb;

public class UsbTestDriver {
	public static final short VENDOR_ID = 0x1267;
	public static final short PRODUCT_ID = 0x0;

	public static void main(String[] args) {
		System.out.println(Usb.device(VENDOR_ID, PRODUCT_ID));
	}
}
