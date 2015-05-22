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
package com.iamcontent.device.controller.pololu.maestro.usb;

import static com.iamcontent.device.controller.pololu.maestro.MaestroCardType.MICRO_MAESTRO_6;
import static com.iamcontent.device.controller.pololu.maestro.MaestroCardType.MINI_MAESTRO_12;
import static com.iamcontent.device.controller.pololu.maestro.MaestroCardType.MINI_MAESTRO_18;
import static com.iamcontent.device.controller.pololu.maestro.MaestroCardType.MINI_MAESTRO_24;
import static com.iamcontent.io.usb.UsbDevicePredicates.vendorAndProductIdsMatch;

import javax.usb.UsbDevice;

import com.iamcontent.device.controller.pololu.maestro.MaestroCardType;
import com.iamcontent.io.IORuntimeException;

/**
 * The USB characteristics of the different types of Pololu Maestro cards.
 * 
 * The id values used by this protocol were obtained from Pololu's Native USB SDK,
 * downloaded from www.pololu.com.
 * 
 * @author Greg Elderfield
 */
public enum UsbMaestroCardType {
	
	USB_MICRO_MAESTRO_6(MICRO_MAESTRO_6, 0x0089),
	USB_MINI_MAESTRO_12(MINI_MAESTRO_12, 0x008A),
	USB_MINI_MAESTRO_18(MINI_MAESTRO_18, 0x008B),
	USB_MINI_MAESTRO_24(MINI_MAESTRO_24, 0x008C);
	
	public static final short VENDOR_ID = 0x1ffb;
	
	private final MaestroCardType type;
	private final short productId;

	private UsbMaestroCardType(MaestroCardType type, int productId) {
		this.type = type;
		this.productId = (short)productId;
	}

	public MaestroCardType getType() {
		return type;
	}

	public short getProductId() {
		return productId;
	}

	public static UsbMaestroCardType forUsbDeviceOrThrow(UsbDevice device) {
		final UsbMaestroCardType result = forUsbDeviceOrNull(device);
		if (result!=null)
			return result;
		else
			throw new IORuntimeException("USB Device is not a Usb Maestro Card");
	}

	public static UsbMaestroCardType forUsbDeviceOrNull(UsbDevice device) {
		for (UsbMaestroCardType c : values())
			if (vendorAndProductIdsMatch(VENDOR_ID, c.getProductId(), device))
				return c;
		return null;
	}

	public static boolean isAMaestroCard(UsbDevice device) {
		return forUsbDeviceOrNull(device) !=null;
	}
}