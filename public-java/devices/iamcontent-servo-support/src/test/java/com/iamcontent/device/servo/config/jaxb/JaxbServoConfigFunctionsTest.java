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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.base.Function;
import com.iamcontent.core.jaxb.AbstractXmlBridgeTest;
import com.iamcontent.device.servo.calibrate.ServoCalibrator;
import com.iamcontent.device.servo.calibrate.ServoSourceCalibrator;
import com.iamcontent.device.servo.config.ServoConfigFunctions;

@SuppressWarnings("rawtypes")
public class JaxbServoConfigFunctionsTest extends AbstractXmlBridgeTest<JaxbServoConfig> {

	public JaxbServoConfigFunctionsTest() {
		super(JaxbServoConfig.class);
	}

	@Test
	public void testUnmarshalling() throws Exception {
        final ServoConfigFunctions<Integer, NewChannel> fns = JaxbServoConfig.configFunctionsFromFile("test-jaxb-servo-config-functions.xml", Integer.class, NewChannel.class);
        
        final ServoSourceCalibrator<Integer> normalizingCalibrator = fns.normalizingCalibrator();
        final ServoCalibrator cal6 = normalizingCalibrator.forChannel(6);
		assertConverterEquals(1.1, 2.2, 3.3, 4.4, cal6.getValueConverter());
        assertConverterEquals(1.5, 1.6, 1.7, 1.8, cal6.getSpeedConverter());
        assertConverterEquals(1.9, 1.11, 1.22, 1.33, cal6.getAccelerationConverter());

		final ServoCalibrator cal3 = normalizingCalibrator.forChannel(3);
		assertConverterEquals(5.5, 6.6, 7.7, 8.8, cal3.getValueConverter());
        assertConverterEquals(0.0, 1.0, 2.7, 2.8, cal3.getSpeedConverter());
        assertConverterEquals(0.0, 1.0, 2.22, 2.33, cal3.getAccelerationConverter());

        final Function<NewChannel, Integer> channelTranslation = fns.channelTranslation();
        assertEquals(new Integer(4), channelTranslation.apply(NewChannel.ELBOW));
        assertEquals(new Integer(2), channelTranslation.apply(NewChannel.WRIST));
        assertEquals(new Integer(1), channelTranslation.apply(NewChannel.KNEE));
        assertEquals(new Integer(3), channelTranslation.apply(NewChannel.ANKLE));
        
        final ServoSourceCalibrator<NewChannel> tuningCalibrator = fns.tuningCalibrator();
        assertConverterEquals(1.11, 2.22, 3.33, 4.44, tuningCalibrator.forChannel(NewChannel.WRIST).getValueConverter());
	}
}

enum NewChannel {ELBOW, WRIST, KNEE, ANKLE}
