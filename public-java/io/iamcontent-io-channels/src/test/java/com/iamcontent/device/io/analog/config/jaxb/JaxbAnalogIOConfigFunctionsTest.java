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

import static com.iamcontent.device.io.analog.config.jaxb.JaxbAnalogIOConfig.fromFile;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.base.Function;
import com.iamcontent.core.jaxb.AbstractXmlBridgeTest;
import com.iamcontent.device.io.analog.calibrate.AnalogIOSourceCalibrator;

@SuppressWarnings("rawtypes")
public class JaxbAnalogIOConfigFunctionsTest extends AbstractXmlBridgeTest<JaxbAnalogIOConfig> {

	public JaxbAnalogIOConfigFunctionsTest() {
		super(JaxbAnalogIOConfig.class);
	}

	@Test
	public void testUnmarshalling() throws Exception {
        final JaxbAnalogIOConfig<Integer, NewChannel> holder = fromFile("test-jaxb-analog-io-config-functions.xml", Integer.class, NewChannel.class);
        final JaxbAnalogIOConfigFunctions<Integer, NewChannel> fns = holder.f;
        
        final AnalogIOSourceCalibrator<Integer> normalizingCalibrator = fns.normalizingCalibrator();
        assertConverterEquals(1.1, 2.2, 3.3, 4.4, normalizingCalibrator.forChannel(6).getValueConverter());
        assertConverterEquals(5.5, 6.6, 7.7, 8.8, normalizingCalibrator.forChannel(3).getValueConverter());
        
        final Function<NewChannel, Integer> channelTranslation = fns.channelTranslation();
        assertEquals(new Integer(4), channelTranslation.apply(NewChannel.ELBOW));
        assertEquals(new Integer(2), channelTranslation.apply(NewChannel.WRIST));
        assertEquals(new Integer(1), channelTranslation.apply(NewChannel.KNEE));
        assertEquals(new Integer(3), channelTranslation.apply(NewChannel.ANKLE));
        
        final AnalogIOSourceCalibrator<NewChannel> tuningCalibrator = fns.tuningCalibrator();
        assertConverterEquals(1.11, 2.22, 3.33, 4.44, tuningCalibrator.forChannel(NewChannel.WRIST).getValueConverter());
	}
}

enum NewChannel {ELBOW, WRIST, KNEE, ANKLE}
