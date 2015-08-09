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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.junit.Test;

import com.iamcontent.core.jaxb.AbstractXmlBridgeTest;
import com.iamcontent.device.servo.calibrate.ServoCalibrator;
import com.iamcontent.device.servo.config.jaxb.ServoCalibratorToXmlBridge.ServoCalibratorXmlAdapter;

public class ServoCalibratorToXmlBridgeTest extends AbstractXmlBridgeTest<ServoCalibratorHolder> {

	public ServoCalibratorToXmlBridgeTest() {
		super(ServoCalibratorHolder.class);
	}

	@Test
	public void testUnmarshalling() throws Exception {
        final ServoCalibratorHolder holder = unmarshalled("/test-servo-calibrator-to-xml-bridge.xml");
        final ServoCalibrator cal = holder.c;
        assertConverterEquals(1.1, 2.2, 3.3, 4.4, cal.getValueConverter());
        assertConverterEquals(5.5, 6.6, 7.7, 8.8, cal.getSpeedConverter());
        assertConverterEquals(9.9, 11.11, 22.22, 33.33, cal.getAccelerationConverter());
	}
}

@XmlRootElement
class ServoCalibratorHolder {
	@XmlElement(name="servoCalibrator")
	@XmlJavaTypeAdapter(ServoCalibratorXmlAdapter.class)
	public ServoCalibrator c;
}
