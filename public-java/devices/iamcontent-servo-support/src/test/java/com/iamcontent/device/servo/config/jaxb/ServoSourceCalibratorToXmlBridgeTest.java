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

import static org.junit.Assert.assertNull;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.junit.Test;

import com.iamcontent.core.jaxb.AbstractXmlBridgeTest;
import com.iamcontent.device.servo.calibrate.ServoCalibrator;
import com.iamcontent.device.servo.calibrate.ServoSourceCalibrator;
import com.iamcontent.device.servo.config.jaxb.ServoSourceCalibratorToXmlBridge.ServoSourceCalibratorXmlAdapter;

public class ServoSourceCalibratorToXmlBridgeTest extends AbstractXmlBridgeTest<ServoSourceCalibratorHolder> {

	public ServoSourceCalibratorToXmlBridgeTest() {
		super(ServoSourceCalibratorHolder.class);
	}

	protected JAXBContext jaxbContext() throws JAXBException {
		return JAXBContext.newInstance(ServoSourceCalibratorHolder.class, Suit.class);
	}

	@Test
	public void testUnmarshalling() throws Exception {
        final ServoSourceCalibratorHolder holder = unmarshalled("/test-servo-source-calibrator-to-xml-bridge.xml");
        final ServoSourceCalibrator<Suit> cal = holder.c;
        final ServoCalibrator diamondsCalibrator = cal.forChannel(Suit.DIAMONDS);
		assertConverterEquals(1.1, 2.2, 3.3, 4.4, diamondsCalibrator.getValueConverter());
        assertConverterEquals(1.5, 1.6, 1.7, 1.8, diamondsCalibrator.getSpeedConverter());
        assertConverterEquals(1.9, 1.11, 1.22, 1.33, diamondsCalibrator.getAccelerationConverter());
        
        final ServoCalibrator heartsCalibrator = cal.forChannel(Suit.HEARTS);
        assertConverterEquals(5.5, 6.6, 7.7, 8.8, heartsCalibrator.getValueConverter());
        assertConverterEquals(0.0, 1.0, 2.7, 2.8, heartsCalibrator.getSpeedConverter());
        assertConverterEquals(0.0, 1.0, 2.22, 2.33, heartsCalibrator.getAccelerationConverter());

        final ServoCalibrator spadesCalibrator = cal.forChannel(Suit.SPADES);
		assertConverterEquals(0.0, 1.0, 9.9, 10.10, spadesCalibrator.getValueConverter());
		assertNull(spadesCalibrator.getSpeedConverter());
		assertNull(spadesCalibrator.getAccelerationConverter());

		final ServoCalibrator clubsCalibrator = cal.forChannel(Suit.CLUBS);
		assertConverterEquals(1.1, 2.2, 3.3, 4.4, clubsCalibrator.getValueConverter());
        assertConverterEquals(1.5, 1.6, 1.7, 1.8, clubsCalibrator.getSpeedConverter());
        assertConverterEquals(1.9, 1.11, 1.22, 1.33, clubsCalibrator.getAccelerationConverter());
	}
}

enum Suit {HEARTS, SPADES, DIAMONDS, CLUBS};

@XmlRootElement
class ServoSourceCalibratorHolder {
	@XmlElement(name="sourceCalibrator")
	@XmlJavaTypeAdapter(ServoSourceCalibratorXmlAdapter.class)
	public ServoSourceCalibrator<Suit> c;
}
