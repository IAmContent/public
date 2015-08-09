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

import com.google.common.base.Function;
import com.iamcontent.device.io.analog.config.jaxb.ChannelMappingFunctionToXmlBridge.ChannelMappingFunctionXmlAdapter;
import com.iamcontent.device.servo.calibrate.ServoSourceCalibrator;
import com.iamcontent.device.servo.config.ServoConfigFunctions;
import com.iamcontent.device.servo.config.jaxb.ServoSourceCalibratorToXmlBridge.ServoSourceCalibratorXmlAdapter;

/**
 * An implementation of {@link ServoConfigFunctions} that can be read from XML using JAXB.
 * @author Greg Elderfield
 */
@XmlRootElement
public class JaxbServoConfigFunctions<R, C> implements ServoConfigFunctions<R, C> {

	@XmlElement
	@XmlJavaTypeAdapter(ServoSourceCalibratorXmlAdapter.class)
	private ServoSourceCalibrator<R> normalizingCalibrator;
	
	@XmlElement
	@XmlJavaTypeAdapter(ChannelMappingFunctionXmlAdapter.class)
	private Function<C, R> channelTranslation;

	@XmlElement
	@XmlJavaTypeAdapter(ServoSourceCalibratorXmlAdapter.class)
	private ServoSourceCalibrator<C> tuningCalibrator;
	
	@Override
	public ServoSourceCalibrator<R> normalizingCalibrator() {
		return normalizingCalibrator;
	}

	@Override
	public Function<C, R> channelTranslation() {
		return channelTranslation;
	}

	@Override
	public ServoSourceCalibrator<C> tuningCalibrator() {
		return tuningCalibrator;
	}
}
