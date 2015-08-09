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

import static org.junit.Assert.assertEquals;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.junit.Test;

import com.iamcontent.core.jaxb.InterRangeDoubleConverterToXmlBridge.InterRangeDoubleConverterXmlAdapter;
import com.iamcontent.core.math.DoubleRange;
import com.iamcontent.core.math.InterRangeDoubleConverter;
import com.iamcontent.core.math.InterRangeDoubleConverter.Mode;

public class InterRangeDoubleConverterToXmlBridgeTest extends AbstractXmlBridgeTest<ConverterHolder> {

	public InterRangeDoubleConverterToXmlBridgeTest() {
		super(ConverterHolder.class);
	}

	@Test
	public void testMarshalling() throws Exception {
		final DoubleRange from = new DoubleRange(1.1, 2.2);
		final DoubleRange to = new DoubleRange(3.3, 4.4);
		final InterRangeDoubleConverter c = new InterRangeDoubleConverter(from, to, Mode.DO_NOT_LIMIT_RESULT_TO_RANGE);
		final ConverterHolder h = new ConverterHolder(c);
		final String result = marshalled(h);
		assertContains(result, "<converter mode=\"DO_NOT_LIMIT_RESULT_TO_RANGE\">");
		assertContains(result, "<fromRange limit1=\"1.1\" limit2=\"2.2\"/>");
		assertContains(result, "<toRange limit1=\"3.3\" limit2=\"4.4\"/>");
	}

	@Test
	public void testUnmarshalling() throws Exception {
        final ConverterHolder holder = unmarshalled("/test-inter-range-double-converter-to-xml-bridge.xml");
        final InterRangeDoubleConverter c = holder.c;
		assertRangeEquals(1.1, 2.2, c.getFromRange());
		assertRangeEquals(3.3, 4.4, c.getToRange());
        assertEquals(Mode.DO_NOT_LIMIT_RESULT_TO_RANGE, c.getMode());
	}

	@Test
	public void testUnmarshalling_defaultValues() throws Exception {
        final ConverterHolder holder = unmarshalled("/test-inter-range-double-converter-to-xml-bridge-defaults.xml");
        final InterRangeDoubleConverter c = holder.c;
		assertRangeEquals(0.0, 1.0, c.getFromRange());
		assertRangeEquals(3.3, 4.4, c.getToRange());
        assertEquals(Mode.LIMIT_RESULT_TO_RANGE, c.getMode());

	}
}

@XmlRootElement
class ConverterHolder {
	@XmlElement(name="converter")
	@XmlJavaTypeAdapter(InterRangeDoubleConverterXmlAdapter.class)
	public InterRangeDoubleConverter c;
	
	public ConverterHolder() {
		c=null;
	}
	
	public ConverterHolder(InterRangeDoubleConverter c) {
		this.c = c;
	}
}
