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

import javax.usb.UsbDevice;

import com.google.common.base.Predicate;
import com.iamcontent.device.controller.pololu.maestro.PololuMaestroServoCard;
import com.iamcontent.io.usb.Usb;

/**
 * A factory for {@link AbstractUsbPololuMaestroServoCard}s.
 * @author Greg Elderfield
 */
public class UsbPololuMaestroServoCards {

	/**
	 * Creates an instance with the first Pololu Maestro {@link UsbDevice} that is found.
	 */
	public static PololuMaestroServoCard defaultUsbPololuMaestroServoCard() {
		return usbPololuMaestroServoCard(Usb.device(isAMaestroUsbDevice()));
	}

	/**
	 * Creates an instance with the given UsbDevice.
	 */
	public static PololuMaestroServoCard usbPololuMaestroServoCard(UsbDevice device) {
		final UsbMaestroCardType cardType = UsbMaestroCardType.forUsbDeviceOrThrow(device);
		switch (cardType) {
		case USB_MICRO_MAESTRO_6:
			return new UsbPololuMicroMaestroServoCard(device);
		default:
			return new UsbPololuMiniMaestroServoCard(device);
		}
	}

	private static Predicate<UsbDevice> isAMaestroUsbDevice() {
		return new Predicate<UsbDevice>() {
			@Override
			public boolean apply(UsbDevice device) {
				return UsbMaestroCardType.isAMaestroCard(device);
			}
		};
	}

	private UsbPololuMaestroServoCards() {
	}
}
