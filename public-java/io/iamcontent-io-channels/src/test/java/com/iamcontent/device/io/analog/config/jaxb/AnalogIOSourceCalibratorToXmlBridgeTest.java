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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.junit.Test;

import com.iamcontent.core.jaxb.AbstractXmlBridgeTest;
import com.iamcontent.device.io.analog.calibrate.AnalogIOSourceCalibrator;
import com.iamcontent.device.io.analog.config.jaxb.AnalogIOSourceCalibratorToXmlBridge.AnalogIOSourceCalibratorXmlAdapter;

public class AnalogIOSourceCalibratorToXmlBridgeTest extends AbstractXmlBridgeTest<AnalogIOSourceCalibratorHolder> {

	public AnalogIOSourceCalibratorToXmlBridgeTest() {
		super(AnalogIOSourceCalibratorHolder.class);
	}

	protected JAXBContext jaxbContext() throws JAXBException {
		return JAXBContext.newInstance(AnalogIOSourceCalibratorHolder.class, Channel.class);
	}

	@Test
	public void testUnmarshalling() throws Exception {
        final AnalogIOSourceCalibratorHolder holder = unmarshalled("/test-analog-io-source-calibrator-to-xml-bridge.xml");
        final AnalogIOSourceCalibrator<Channel> cal = holder.c;
        assertConverterEquals(1.1, 2.2, 3.3, 4.4, cal.forChannel(Channel.KNEE).getValueConverter());
        assertConverterEquals(5.5, 6.6, 7.7, 8.8, cal.forChannel(Channel.ELBOW).getValueConverter());
        assertConverterEquals(0.0, 1.0, 9.9, 10.10, cal.forChannel(Channel.WRIST).getValueConverter());
        assertConverterEquals(1.1, 2.2, 3.3, 4.4, cal.forChannel(Channel.ANKLE).getValueConverter());
	}
}

enum Channel {ELBOW, WRIST, KNEE, ANKLE};

@XmlRootElement
class AnalogIOSourceCalibratorHolder {
	@XmlElement(name="sourceCalibrator")
	@XmlJavaTypeAdapter(AnalogIOSourceCalibratorXmlAdapter.class)
	public AnalogIOSourceCalibrator<Channel> c;
}
