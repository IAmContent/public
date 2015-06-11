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
package com.iamcontent.device.servo.command;

import static com.iamcontent.device.servo.command.gson.ImmutableServoCommandDeserializer.customGsonBuilder;

import java.util.List;

import com.google.gson.Gson;
import com.iamcontent.io.gson.JsonBasedListReader;

/**
 * A source of named {@link ServoCommand}s.
 * @author Greg Elderfield
 */
public class ServoCommands {
	
	public static <C> List<ServoCommand<C>> servoCommands(String commandName, Class<C> channelClass) {
		final List<?> result = JsonBasedListReader.list(commandName, ImmutableServoCommand.class, gson(channelClass));
		return uncheckedCast(result);
	}

	private static <C> Gson gson(Class<C> channelClass) {
		return customGsonBuilder(channelClass).create();
	}

	@SuppressWarnings("unchecked")
	private static <C> List<ServoCommand<C>> uncheckedCast(List<?> list) {
		return (List<ServoCommand<C>>)list;
	}
	
	private ServoCommands() {
	}
}
