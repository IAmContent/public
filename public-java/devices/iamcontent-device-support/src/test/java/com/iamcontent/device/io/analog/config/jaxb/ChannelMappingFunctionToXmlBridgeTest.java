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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.junit.Test;

import com.google.common.base.Function;
import com.iamcontent.core.jaxb.AbstractXmlBridgeTest;
import com.iamcontent.device.io.analog.config.jaxb.ChannelMappingFunctionToXmlBridge.ChannelMappingFunctionXmlAdapter;

public class ChannelMappingFunctionToXmlBridgeTest extends AbstractXmlBridgeTest<ChannelMappingHolder> {

	public ChannelMappingFunctionToXmlBridgeTest() {
		super(ChannelMappingHolder.class);
	}

	protected JAXBContext jaxbContext() throws JAXBException {
		return JAXBContext.newInstance(ChannelMappingHolder.class, FromChannel.class, ToChannel.class);
	}

	@Test
	public void testUnmarshalling() throws Exception {
        final ChannelMappingHolder holder = unmarshalled("/test-channel-mapping-to-xml-bridge.xml");
        final Function<Object, Object> f = holder.f;
        assertEquals(ToChannel.ORANGE, f.apply(FromChannel.ELBOW));
        assertEquals(ToChannel.ORANGE, f.apply(FromChannel.WRIST));
        assertEquals(ToChannel.RED, f.apply(FromChannel.KNEE));
        assertNull(f.apply(FromChannel.ANKLE));
	}
}

enum FromChannel {ELBOW, WRIST, KNEE, ANKLE};
enum ToChannel {RED, ORANGE, YELLOW, GREEN};

@XmlRootElement
class ChannelMappingHolder {
	@XmlElement(name="channelMappings")
	@XmlJavaTypeAdapter(ChannelMappingFunctionXmlAdapter.class)
	public Function<Object, Object> f;
}
