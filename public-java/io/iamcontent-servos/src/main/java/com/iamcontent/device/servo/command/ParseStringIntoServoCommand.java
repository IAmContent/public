/**
  IAmContent Public Libraries.
  Copyright (C) 2015-2021 Greg Elderfield
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
package com.iamcontent.device.servo.command;

import com.google.common.base.Function;
import com.iamcontent.device.servo.Servo;
import com.iamcontent.io.cli.UnknownCommandException;

/**
 * An abstract function to parse a String command into a {@link ServoCommand}.
 * @author Greg Elderfield
 * 
 * @param <C> The type used to identify the channel of a {@link Servo}. 
 */
public abstract class ParseStringIntoServoCommand<C> implements Function<String, ServoCommand<C>> {

	@Override
	public ServoCommand<C> apply(String command) {
		try {
			final Args args = new Args(command);
			return new ImmutableServoCommand<C>(args.channel, args.value, args.speed, args.acceleration);
		} catch (Exception e) {
			throw new UnknownCommandException(command);
		}
	}

	protected abstract C parseChannel(String s);

	private class Args {
		private final String[] args;
		final C channel;
		final double value;
		final Double speed;
		final Double acceleration;
		public Args(String command) {
			args = command.split(" ");
			channel = parseChannel(args[0]);
			value = Double.parseDouble(args[1]);
			speed = doubleIfPresent(2);
			acceleration = doubleIfPresent(3);
		}
		private Double doubleIfPresent(int i) {
			return (args.length <= i) ? null : Double.parseDouble(args[i]);
		}
	}
}