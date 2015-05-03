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
package com.iamcontent.core;

/**
 * Math utility functions.
 * @author Greg Elderfield
 */
public class MathUtils {

	public static int limit(int i, int min, int max) {
		return Math.min(max, Math.max(i, min));
	}

	public static double limit(double i, double min, double max) {
		return Math.min(max, Math.max(i, min));
	}

	public static double linearConvert(double v, double from1, double from2, double to1, double to2) {
		final double fromRange = from2 - from1;
		final double toRange = to2 - to1;
		final double quotient = (v-from1)/fromRange;
		return (quotient * toRange) + to1;
	}
}
