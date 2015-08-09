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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.junit.Test;

import com.iamcontent.core.jaxb.AbstractXmlBridgeTest;
import com.iamcontent.device.io.analog.calibrate.AnalogIOCalibrator;
import com.iamcontent.device.io.analog.config.jaxb.AnalogIOCalibratorToXmlBridge.AnalogIOCalibratorXmlAdapter;

public class AnalogIOCalibratorToXmlBridgeTest extends AbstractXmlBridgeTest<AnalogIOCalibratorHolder> {

	public AnalogIOCalibratorToXmlBridgeTest() {
		super(AnalogIOCalibratorHolder.class);
	}

	@Test
	public void testUnmarshalling() throws Exception {
        final AnalogIOCalibratorHolder holder = unmarshalled("/test-analog-io-calibrator-to-xml-bridge.xml");
        final AnalogIOCalibrator cal = holder.c;
        assertConverterEquals(1.1, 2.2, 3.3, 4.4, cal.getValueConverter());
	}
}

@XmlRootElement
class AnalogIOCalibratorHolder {
	@XmlElement(name="channelCalibrator")
	@XmlJavaTypeAdapter(AnalogIOCalibratorXmlAdapter.class)
	public AnalogIOCalibrator c;
}
