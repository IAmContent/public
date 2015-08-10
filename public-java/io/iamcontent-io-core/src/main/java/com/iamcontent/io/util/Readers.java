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
package com.iamcontent.io.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Utility methods for {@link Reader}s.
 * @author Greg Elderfield
 */
public class Readers {
	public static BufferedReader bufferedReader(InputStream is) {
		return bufferedReader(inputStreamReader(is));
	}

	public static BufferedReader bufferedReader(Reader r) {
		return new BufferedReader(r);
	}

	public static InputStreamReader inputStreamReader(InputStream is) {
		return new InputStreamReader(is);
	}
}
