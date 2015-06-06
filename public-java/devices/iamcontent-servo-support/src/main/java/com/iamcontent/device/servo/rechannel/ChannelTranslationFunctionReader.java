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
package com.iamcontent.device.servo.rechannel;


import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.iamcontent.device.servo.Servo;
import com.iamcontent.io.gson.JsonBasedMapReader;

/**
 * Creates Functions to translate {@link Servo} channel identifiers according to JSON file resources.
 * @author Greg Elderfield
 */
public class ChannelTranslationFunctionReader<F, T> {

	private static final String FOLDER = "servo/channels/";

	public static <F, T> Function<F, T> function(String name, Class<F> fromChannelClass, Class<T> toChannelClass) {
		final Map<F, T> map = JsonBasedMapReader.map(FOLDER, name, fromChannelClass, toChannelClass);
		return Functions.forMap(map);
	}
}
