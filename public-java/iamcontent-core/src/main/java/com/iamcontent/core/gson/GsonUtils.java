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
package com.iamcontent.core.gson;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;

/**
 * Helper functions for Gson usage.
 * @author Greg Elderfield
 */
public class GsonUtils {
	
	public static final String JSON_FILE_EXTENSION = ".json";

	public static <T> T getMemberAsObject(JsonElement json, String memberName, JsonDeserializationContext context, Type typeOfT) {
		return context.deserialize(getMember(json, memberName), typeOfT);
	}

	public static <T> T getMemberAsObject(JsonElement json, String memberName, JsonDeserializationContext context, Type typeOfT, T defaultValue) {
		final JsonElement member = getMember(json, memberName);
		if (member==null)
			return defaultValue;
		return context.deserialize(member, typeOfT);
	}

	public static double getMemberAsDouble(JsonElement json, String memberName) {
		return getMember(json, memberName).getAsDouble();
	}
	
	public static int getMemberAsInt(JsonElement json, String memberName) {
		return getMember(json, memberName).getAsInt();
	}
	
	public static String getMemberAsString(JsonElement json, String memberName) {
		return getMember(json, memberName).getAsString();
	}
	
	public static String getMemberAsString(JsonElement json, String memberName, String defaultValue) {
		final JsonElement member = getMember(json, memberName);
		return member==null ? defaultValue : member.getAsString();
	}
	
	public static JsonArray getArrayMember(JsonElement json, String memberName) {
		return json.getAsJsonObject().getAsJsonArray(memberName);
	}

	public static JsonElement getMember(JsonElement json, String memberName) {
		return json.getAsJsonObject().get(memberName);
	}
	
	private GsonUtils() {
	}
}
