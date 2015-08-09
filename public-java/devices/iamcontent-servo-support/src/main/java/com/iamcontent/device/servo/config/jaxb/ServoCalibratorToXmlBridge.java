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
package com.iamcontent.device.servo.config.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.iamcontent.core.jaxb.InterRangeDoubleConverterToXmlBridge.InterRangeDoubleConverterXmlAdapter;
import com.iamcontent.core.math.InterRangeDoubleConverter;
import com.iamcontent.device.io.analog.config.jaxb.AnalogIOCalibratorToXmlBridge;
import com.iamcontent.device.servo.calibrate.ImmutableServoCalibrator;
import com.iamcontent.device.servo.calibrate.ServoCalibrator;

/**
 * A JAXB-based bridge between {@link ServoCalibrator} and XML. 
 * @author Greg Elderfield
 */
public class ServoCalibratorToXmlBridge extends AnalogIOCalibratorToXmlBridge {

	/**
	 * An XmlAdapter that allows an {@link ServoCalibrator} to be represented in XML as a {@link JaxbServoCalibrator}.
	 */
	public static class ServoCalibratorXmlAdapter extends XmlAdapter<JaxbServoCalibrator, ServoCalibrator> {
		@Override
		public ServoCalibrator unmarshal(JaxbServoCalibrator in) throws Exception {
			return new ImmutableServoCalibrator(in.valueConverter, in.speedConverter, in.accelerationConverter);
		}

		@Override
		public JaxbServoCalibrator marshal(ServoCalibrator in) throws Exception {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * The fields required to construct an {@link ServoCalibrator} in a record that can be handled by JAXB.
	 */
	protected static class JaxbServoCalibrator extends JaxbAnalogIOCalibrator {
		@XmlElement
		@XmlJavaTypeAdapter(InterRangeDoubleConverterXmlAdapter.class)
		protected InterRangeDoubleConverter speedConverter;
		
		@XmlElement
		@XmlJavaTypeAdapter(InterRangeDoubleConverterXmlAdapter.class)
		protected InterRangeDoubleConverter accelerationConverter;
	}
}