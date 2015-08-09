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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.iamcontent.core.math.DoubleRange;

/**
 * A JAXB-based bridge between {@link DoubleRange} and XML. 
 * @author Greg Elderfield
 */
public class DoubleRangeToXmlBridge {

	/**
	 * An XmlAdapter that allows a {@link DoubleRange} to be represented in XML as a {@link JaxbDoubleRange}.
	 */
	public static class DoubleRangeXmlAdapter extends XmlAdapter<JaxbDoubleRange, DoubleRange> {
		@Override
		public DoubleRange unmarshal(JaxbDoubleRange in) throws Exception {
			return new DoubleRange(in.limit1, in.limit2);
		}

		@Override
		public JaxbDoubleRange marshal(DoubleRange in) throws Exception {
			return new JaxbDoubleRange(in.getLimit1(), in.getLimit2());
		}
	}

	/**
	 * The fields required to construct a {@link DoubleRange} in a record that can be handled by JAXB.
	 */
	protected static class JaxbDoubleRange {
		@XmlAttribute
		public double limit1;
		@XmlAttribute
		public double limit2;

		protected JaxbDoubleRange() {
		}

		public JaxbDoubleRange(double limit1, double limit2) {
			this.limit1 = limit1;
			this.limit2 = limit2;
		}
	}
}