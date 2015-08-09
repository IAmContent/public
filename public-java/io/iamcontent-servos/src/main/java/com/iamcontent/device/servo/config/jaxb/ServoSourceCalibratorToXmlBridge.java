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

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.iamcontent.device.servo.calibrate.ServoCalibrator;
import com.iamcontent.device.servo.calibrate.ServoSourceCalibrator;
import com.iamcontent.device.servo.calibrate.DefaultingServoSourceCalibrator;
import com.iamcontent.device.servo.calibrate.ImmutableServoCalibrator;
import com.iamcontent.device.servo.config.jaxb.ServoCalibratorToXmlBridge.JaxbServoCalibrator;
import com.iamcontent.device.servo.config.jaxb.ServoCalibratorToXmlBridge.ServoCalibratorXmlAdapter;

/**
 * A JAXB-based bridge between {@link ServoSourceCalibrator} and XML. 
 * @author Greg Elderfield
 */
public class ServoSourceCalibratorToXmlBridge {

	/**
	 * An XmlAdapter that allows an {@link ServoSourceCalibrator} to be represented in XML as a {@link JaxbServoSourceCalibrator}.
	 */
	@SuppressWarnings("rawtypes")
	public static class ServoSourceCalibratorXmlAdapter extends XmlAdapter<JaxbServoSourceCalibrator, ServoSourceCalibrator> {
		@Override
		@SuppressWarnings("unchecked")
		public ServoSourceCalibrator unmarshal(JaxbServoSourceCalibrator in) throws Exception {
			return new DefaultingServoSourceCalibrator(in.defaultCalibrator, in.perChannelCalibrators());
		}

		@Override
		public JaxbServoSourceCalibrator marshal(ServoSourceCalibrator in) throws Exception {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * The fields required to construct an {@link ServoSourceCalibrator} in a record that can be handled by JAXB.
	 */
	protected static class JaxbServoSourceCalibrator {
		@XmlElement
		@XmlJavaTypeAdapter(ServoCalibratorXmlAdapter.class)
		public ImmutableServoCalibrator defaultCalibrator;

		@XmlElementWrapper(name = "perChannelCalibrators")
		@XmlElement(name="calibrator")
		public JaxbChanneledServoCalibrator[] perChannelCalibrators;

		public Map<Object, ServoCalibrator> perChannelCalibrators() {
			final Map<Object, ServoCalibrator> result = new HashMap<Object, ServoCalibrator>();
			for (JaxbChanneledServoCalibrator c : perChannelCalibrators) {
				result.put(c.channel, new ImmutableServoCalibrator(c.valueConverter, c.speedConverter, c.accelerationConverter));
			}
			return result;
		}
	}
	
	protected static class JaxbChanneledServoCalibrator extends JaxbServoCalibrator {
		@XmlElement
		public Object channel;
	}
}