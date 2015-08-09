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
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.iamcontent.device.io.analog.calibrate.AnalogIOCalibrator;
import com.iamcontent.device.io.analog.calibrate.AnalogIOSourceCalibrator;
import com.iamcontent.device.io.analog.calibrate.DefaultingAnalogIOSourceCalibrator;
import com.iamcontent.device.io.analog.calibrate.ImmutableAnalogIOCalibrator;
import com.iamcontent.device.io.analog.config.jaxb.AnalogIOCalibratorToXmlBridge.AnalogIOCalibratorXmlAdapter;
import com.iamcontent.device.io.analog.config.jaxb.AnalogIOCalibratorToXmlBridge.JaxbAnalogIOCalibrator;

/**
 * A JAXB-based bridge between {@link AnalogIOSourceCalibrator} and XML. 
 * @author Greg Elderfield
 */
public class AnalogIOSourceCalibratorToXmlBridge {

	/**
	 * An XmlAdapter that allows an {@link AnalogIOSourceCalibrator} to be represented in XML as a {@link JaxbAnalogIOSourceCalibrator}.
	 */
	@SuppressWarnings("rawtypes")
	public static class AnalogIOSourceCalibratorXmlAdapter extends XmlAdapter<JaxbAnalogIOSourceCalibrator, AnalogIOSourceCalibrator> {
		@Override
		@SuppressWarnings("unchecked")
		public AnalogIOSourceCalibrator unmarshal(JaxbAnalogIOSourceCalibrator in) throws Exception {
			return new DefaultingAnalogIOSourceCalibrator(in.defaultCalibrator, in.perChannelCalibrators());
		}

		@Override
		public JaxbAnalogIOSourceCalibrator marshal(AnalogIOSourceCalibrator in) throws Exception {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * The fields required to construct an {@link AnalogIOSourceCalibrator} in a record that can be handled by JAXB.
	 */
	protected static class JaxbAnalogIOSourceCalibrator {
		@XmlElement
		@XmlJavaTypeAdapter(AnalogIOCalibratorXmlAdapter.class)
		public ImmutableAnalogIOCalibrator defaultCalibrator;

		@XmlElementWrapper(name="perChannelCalibrators")
		@XmlElement(name="calibrator")
		public JaxbChanneledAnalogIOCalibrator[] perChannelCalibrators;

		public Map<Object, AnalogIOCalibrator> perChannelCalibrators() {
			final Map<Object, AnalogIOCalibrator> result = new HashMap<Object, AnalogIOCalibrator>();
			for (JaxbChanneledAnalogIOCalibrator c : perChannelCalibrators) {
				result.put(c.channel, new ImmutableAnalogIOCalibrator(c.valueConverter));
			}
			return result;
		}
	}
	
	protected static class JaxbChanneledAnalogIOCalibrator extends JaxbAnalogIOCalibrator {
		@XmlElement
		public Object channel;
	}
}