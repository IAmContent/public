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

import static com.iamcontent.io.util.JaxbUtils.unmarshalledFromFile;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.iamcontent.device.servo.command.ServoCommand;

/**
 * A {@link ServoCommand} that can be read from XML using JAXB.
 * @author Greg Elderfield
 */
public class JaxbServoCommand<C> implements ServoCommand<C> {

	@XmlElement
	private C channel;
	
	@XmlElement
	private Double value, speed, acceleration;
	
	@Override
	public C getChannel() {
		return channel;
	}

	@Override
	public Double getValue() {
		return value;
	}

	@Override
	public Double getSpeed() {
		return speed;
	}

	@Override
	public Double getAcceleration() {
		return acceleration;
	}

	@SuppressWarnings("unchecked")
	public static <C> List<ServoCommand<C>> servoCommandsFromFile(final String fileName, Class<C> channelClass) {
		final ServoCommand<C>[] commands = unmarshalledFromFile(fileName, JaxbServoCommandArray.class, channelClass).commands();
		return Arrays.asList(commands);
	}
	
	@XmlRootElement(name="commands")
	private static class JaxbServoCommandArray<C> {
		@XmlElement(name="command")
		private JaxbServoCommand<C>[] commands;

		public ServoCommand<C>[] commands() {
			return commands;
		}
	}
}
