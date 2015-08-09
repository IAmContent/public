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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.iamcontent.core.jaxb.InterRangeDoubleConverterToXmlBridge.InterRangeDoubleConverterXmlAdapter;
import com.iamcontent.core.math.InterRangeDoubleConverter;
import com.iamcontent.device.io.analog.calibrate.AnalogIOCalibrator;
import com.iamcontent.device.io.analog.calibrate.ImmutableAnalogIOCalibrator;

/**
 * A JAXB-based bridge between {@link AnalogIOCalibrator} and XML. 
 * @author Greg Elderfield
 */
public class AnalogIOCalibratorToXmlBridge {

	/**
	 * An XmlAdapter that allows an {@link AnalogIOCalibrator} to be represented in XML as a {@link JaxbAnalogIOCalibrator}.
	 */
	public static class AnalogIOCalibratorXmlAdapter extends XmlAdapter<JaxbAnalogIOCalibrator, AnalogIOCalibrator> {
		@Override
		public AnalogIOCalibrator unmarshal(JaxbAnalogIOCalibrator in) throws Exception {
			return new ImmutableAnalogIOCalibrator(in.valueConverter);
		}

		@Override
		public JaxbAnalogIOCalibrator marshal(AnalogIOCalibrator in) throws Exception {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * The fields required to construct an {@link AnalogIOCalibrator} in a record that can be handled by JAXB.
	 */
	protected static class JaxbAnalogIOCalibrator {
		@XmlElement
		@XmlJavaTypeAdapter(InterRangeDoubleConverterXmlAdapter.class)
		public InterRangeDoubleConverter valueConverter;
	}
}