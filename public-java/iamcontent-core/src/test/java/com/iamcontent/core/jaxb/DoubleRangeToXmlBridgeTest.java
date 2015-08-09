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
package com.iamcontent.core.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.junit.Test;

import com.iamcontent.core.jaxb.DoubleRangeToXmlBridge.DoubleRangeXmlAdapter;
import com.iamcontent.core.math.DoubleRange;

public class DoubleRangeToXmlBridgeTest extends AbstractXmlBridgeTest<DoubleRangeHolder> {

	public DoubleRangeToXmlBridgeTest() {
		super(DoubleRangeHolder.class);
	}

	@Test
	public void testMarshalling() throws Exception {
		final DoubleRange r = new DoubleRange(1.1, 2.2);
		final DoubleRangeHolder h = new DoubleRangeHolder(r);
        String result = marshalled(h);
        assertContains(result, "<range limit1=\"1.1\" limit2=\"2.2\"/>");
	}

	@Test
	public void testUnmarshalling() throws Exception {
        final DoubleRangeHolder holder = unmarshalled("/test-double-range-to-xml-bridge.xml");
        assertRangeEquals(1.1, 2.2, holder.r);
	}

}

@XmlRootElement
class DoubleRangeHolder {
	@XmlElement(name="range")
	@XmlJavaTypeAdapter(DoubleRangeXmlAdapter.class)
	public DoubleRange r;
	
	public DoubleRangeHolder() {
		r=null;
	}
	
	public DoubleRangeHolder(DoubleRange r) {
		this.r = r;
	}
}
