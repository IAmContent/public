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
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Before;

import com.google.common.base.Function;
import com.iamcontent.core.math.DoubleRange;
import com.iamcontent.core.math.InterRangeDoubleConverter;

/**
 * Abstract base class for testing JAXB-based XML bridges.
 * @author Greg Elderfield
 */
public abstract class AbstractXmlBridgeTest<T> {
	
	private final Class<T> jaxbClass;
	private static final double TOLERANCE = 0.0001;
	private Marshaller marshaller;
	private Unmarshaller unmarshaller;
	
	protected AbstractXmlBridgeTest(Class<T> jaxbClass) {
		this.jaxbClass = jaxbClass;
	}

	@Before
	public void setUp() throws Exception {
        final JAXBContext jc = jaxbContext();
        marshaller = jc.createMarshaller();
        unmarshaller = jc.createUnmarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	}

	protected JAXBContext jaxbContext() throws JAXBException {
		return JAXBContext.newInstance(jaxbClass);
	}

	protected String marshalled(T h) throws Exception {
		final StringWriter result = new StringWriter();
        marshaller.marshal(h, result);
        final String s = result.toString();
		return s;
	}

	protected T unmarshalled(final String resource) throws JAXBException, IOException {
		try (final InputStream is = getClass().getResourceAsStream(resource)) {
			final Object result = unmarshaller.unmarshal(is);
			return jaxbClass.cast(result);
		}
	}

	protected void assertContains(String s, final String substring) {
		assertTrue("Checking result " + s, s.contains(substring));
	}

	protected static void assertRangeEquals(final double expectedLimit1, final double expectedLimit2, final DoubleRange actual) {
		assertEquals(expectedLimit1, actual.getLimit1(), TOLERANCE);
		assertEquals(expectedLimit2, actual.getLimit2(), TOLERANCE);
	}

	protected void assertConverterEquals(final double expectedFromRangeLimit1, final double expectedFromRangeLimit2,
			final double expectedToRangeLimit1, final double expectedToRangeLimit2, 
			final Function<Double, Double> doubleConverter) {
		final InterRangeDoubleConverter c = (InterRangeDoubleConverter) doubleConverter;
		assertRangeEquals(expectedFromRangeLimit1, expectedFromRangeLimit2, c.getFromRange());
		assertRangeEquals(expectedToRangeLimit1, expectedToRangeLimit2, c.getToRange());
	}
}
