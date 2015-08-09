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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.iamcontent.core.jaxb.DoubleRangeToXmlBridge.DoubleRangeXmlAdapter;
import com.iamcontent.core.math.DoubleRange;
import com.iamcontent.core.math.InterRangeDoubleConverter;
import com.iamcontent.core.math.InterRangeDoubleConverter.Mode;

/**
 * A JAXB-based bridge between {@link InterRangeDoubleConverter} and XML. 
 * @author Greg Elderfield
 */
public class InterRangeDoubleConverterToXmlBridge {

	/**
	 * An XmlAdapter that allows an {@link InterRangeDoubleConverter} to be represented in XML as a {@link JaxbInterRangeDoubleConverter}.
	 */
	public static class InterRangeDoubleConverterXmlAdapter extends XmlAdapter<JaxbInterRangeDoubleConverter, InterRangeDoubleConverter> {
		@Override
		public InterRangeDoubleConverter unmarshal(JaxbInterRangeDoubleConverter in) throws Exception {
			return new InterRangeDoubleConverter(in.fromRangeOrDefault(), in.toRange, in.modeOrDefault());
		}

		@Override
		public JaxbInterRangeDoubleConverter marshal(InterRangeDoubleConverter in) throws Exception {
			return new JaxbInterRangeDoubleConverter(in.getFromRange(), in.getToRange(), in.getMode());
		}
	}

	/**
	 * The fields required to construct an {@link InterRangeDoubleConverter} in a record that can be handled by JAXB.
	 */
	protected static class JaxbInterRangeDoubleConverter {
		@XmlElement
		@XmlJavaTypeAdapter(DoubleRangeXmlAdapter.class)
		public DoubleRange fromRange;
		
		@XmlElement
		@XmlJavaTypeAdapter(DoubleRangeXmlAdapter.class)
		public DoubleRange toRange;
		
		@XmlAttribute
		public Mode mode;

		protected JaxbInterRangeDoubleConverter() {
		}

		public JaxbInterRangeDoubleConverter(DoubleRange fromRange, DoubleRange toRange, Mode mode) {
			this.fromRange = fromRange;
			this.toRange = toRange;
			this.mode = mode;
		}

		public DoubleRange fromRangeOrDefault() {
			return fromRange==null ? DoubleRange.NORMAL_RANGE : fromRange;
		}

		public Mode modeOrDefault() {
			return mode==null ? Mode.LIMIT_RESULT_TO_RANGE : mode;
		}
	}
}