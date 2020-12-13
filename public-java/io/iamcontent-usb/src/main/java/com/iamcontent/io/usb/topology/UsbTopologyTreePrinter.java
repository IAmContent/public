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
package com.iamcontent.io.usb.topology;

import static com.iamcontent.io.usb.license.License.displayLicense;

import java.io.IOException;
import java.util.function.Predicate;

import javax.usb.UsbDevice;
import javax.usb.UsbHub;

import com.iamcontent.io.usb.UsbRuntimeException;

/**
 * Prints the USB topology tree from the root hub downwards.
 * @author Greg Elderfield
 */
public class UsbTopologyTreePrinter extends UsbTopologyExplorer {

	private static final Predicate<UsbDevice> VISIT_ALL_DEVICES = x -> true;
	private static final String TAB = "  ";
	private static final StringBuilder padding = new StringBuilder();
	
	private final Appendable out;
	
	public UsbTopologyTreePrinter() {
		this(VISIT_ALL_DEVICES, System.out);
	}

	public UsbTopologyTreePrinter(Predicate<UsbDevice> shouldVisit, Appendable out) {
		super(shouldVisit);
		this.out = out;
	}

	public static void main(String[] args) {
		displayLicense();
		final UsbTopologyTreePrinter printer = new UsbTopologyTreePrinter();
		printer.exploreRootUsbHub();
	}
	
	@Override
	protected void exploreDescendants(UsbHub hub) {
		increasePadding();
		super.exploreDescendants(hub);
		decreasePadding();
	}
	
	@Override
	public void visit(UsbDevice usbDevice) {
		appendQuietly(out, padding + usbDevice.toString() + "\n");
	}
	
	public static void appendQuietly(Appendable out, String s) {
		try {
			out.append(s);
		} catch (IOException e) {
			throw new UsbRuntimeException(e);
		}
	}

	private void increasePadding() {
		padding.append(TAB);
	}

	private void decreasePadding() {
		padding.setLength(padding.length() - TAB.length());
	}
}
