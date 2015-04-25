/**
  IAmContent Public Libraries.
  Copyright (C) 2015 Greg Elderfield
  @author Greg Elderfield, iamcontent@jarchitect.co.uk
 
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

/**
 * Searches for a USB device with a given vendor id and product id.
 * @author Greg Elderfield
 */
public class UsbTestDriver {

	public static void main(String[] args) {
		try {
			System.out.println(seekDevice(args));
		} catch (ArrayIndexOutOfBoundsException e) {
			usage("Too few parameters.");
		} catch (NumberFormatException e) {
			usage("Illegal number.");
		}
	}

	private static EasyUsbDevice seekDevice(String[] args) {
		return seekDevice(args(args));
	}

	private static EasyUsbDevice seekDevice(final Args a) {
		return Usb.device(a.vendorId, a.productId);
	}
	
	private static Args args(String[] args) {
		return new Args(args);
	}
	
	/**
	 * Parses the arguments.
	 */
	private static class Args {
		final Short vendorId;
		final Short productId;
		public Args(String[] args) {
			vendorId = Short.parseShort(args[0]);
			productId = Short.parseShort(args[1]);
		}
	}
	
	private static void usage(final String reason) {
		System.err.println(reason);
		System.err.println("Usage: java -cp<classpath>" + UsbTestDriver.class.getName() + " <vendorId> <productId>" );
		System.err.println("Please try again.");
	}
}
