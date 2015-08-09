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
package com.iamcontent.device.io.analog.config.jaxb;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.google.common.base.Function;
import com.google.common.base.Functions;

/**
 * A JAXB-based bridge between a channel-mapping {@link Function<C1,C2>} and XML. 
 * @author Greg Elderfield
 */
public class ChannelMappingFunctionToXmlBridge {

	/**
	 * An XmlAdapter that allows a channel-mapping {@link Function<C1,C2>} to be represented in XML as a {@link JaxbChannelMappingFunction}.
	 */
	@SuppressWarnings("rawtypes")
	public static class ChannelMappingFunctionXmlAdapter extends XmlAdapter<JaxbChannelMappingFunction, Function> {
		@Override
		public Function unmarshal(JaxbChannelMappingFunction in) throws Exception {
			return Functions.forMap(in.mapping());
		}

		@Override
		public JaxbChannelMappingFunction marshal(Function in) throws Exception {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * The fields required to construct a channel-mapping {@link Function<C1,C2>} in a record that can be handled by JAXB.
	 */
	protected static class JaxbChannelMappingFunction {
		@XmlElement(name="mapping")
		public JaxbChannelMapping[] mapping;

		public Map<Object, Object> mapping() {
			final Map<Object, Object> result = new HashMap<Object, Object>();
			for (JaxbChannelMapping c : mapping) {
				result.put(c.fromChannel,c.toChannel);
			}
			return result;
		}
	}
	
	protected static class JaxbChannelMapping {
		@XmlElement
		public Object fromChannel;
		@XmlElement
		public Object toChannel;
	}
}