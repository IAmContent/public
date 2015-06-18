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
package com.iamcontent.device.servo.command.gson;

import static com.iamcontent.io.gson.GsonUtils.getMemberAsObject;

import java.lang.reflect.Type;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.iamcontent.device.servo.command.ImmutableServoCommand;

/**
 * A Gson {@link JsonDeserializer} for {@link ImmutableServoCommand} objects.
 * @author Greg Elderfield
 */
public class ImmutableServoCommandDeserializer<C> implements JsonDeserializer<ImmutableServoCommand<C>> {

	private final Class<C> channelClass;
	
	public ImmutableServoCommandDeserializer(Class<C> channelClass) {
		this.channelClass = channelClass;
	}

	@Override
	public ImmutableServoCommand<C> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		final C channel = getMemberAsObject(json, "channel", context, channelClass);
		final Double position = getMemberAsObject(json, "position", context, Double.class);
		final Double speed = getMemberAsObject(json, "speed", context, Double.class);
		final Double acceleration = getMemberAsObject(json, "acceleration", context, Double.class);
		return new ImmutableServoCommand<C>(channel, position, speed, acceleration);
	}

	public static <C> GsonBuilder customGsonBuilder(Class<C> channelClass) {
		return register(new GsonBuilder(), channelClass);
	}
	
	public static <C> GsonBuilder register(GsonBuilder builder, Class<C> channelClass) {
		return builder.registerTypeAdapter(ImmutableServoCommand.class, newInstance(channelClass));
	}
	
	private static <C> ImmutableServoCommandDeserializer<C> newInstance(Class<C> channelClass) {
		return new ImmutableServoCommandDeserializer<C>(channelClass);
	}
}
