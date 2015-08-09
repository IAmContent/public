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

import com.google.common.base.Function;
import com.iamcontent.device.io.analog.calibrate.AnalogIOSourceCalibrator;
import com.iamcontent.device.io.analog.config.AnalogIOConfigFunctions;
import com.iamcontent.device.io.analog.config.jaxb.AnalogIOSourceCalibratorToXmlBridge.AnalogIOSourceCalibratorXmlAdapter;
import com.iamcontent.device.io.analog.config.jaxb.ChannelMappingFunctionToXmlBridge.ChannelMappingFunctionXmlAdapter;

/**
 * An implementation of {@link AnalogIOConfigFunctions} that can be read from XML using JAXB.
 * @author Greg Elderfield
 */
@XmlRootElement
public class JaxbAnalogIOConfigFunctions<R, C> implements AnalogIOConfigFunctions<R, C> {

	@XmlElement
	@XmlJavaTypeAdapter(AnalogIOSourceCalibratorXmlAdapter.class)
	private AnalogIOSourceCalibrator<R> normalizingCalibrator;
	
	@XmlElement
	@XmlJavaTypeAdapter(ChannelMappingFunctionXmlAdapter.class)
	private Function<C, R> channelTranslation;

	@XmlElement
	@XmlJavaTypeAdapter(AnalogIOSourceCalibratorXmlAdapter.class)
	private AnalogIOSourceCalibrator<C> tuningCalibrator;
	
	@Override
	public AnalogIOSourceCalibrator<R> normalizingCalibrator() {
		return normalizingCalibrator;
	}

	@Override
	public Function<C, R> channelTranslation() {
		return channelTranslation;
	}

	@Override
	public AnalogIOSourceCalibrator<C> tuningCalibrator() {
		return tuningCalibrator;
	}
}
