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
package com.iamcontent.device.servo.command;

import com.google.common.base.Function;
import com.iamcontent.io.cli.UnknownCommandException;

/**
 * A function to parse a String command into a {@link ServoCommand}.
 */
public class ParseStringIntoServoCommandFunction implements Function<String, ServoCommand<Integer>> {

	@Override
	public ServoCommand<Integer> apply(String command) {
		try {
			final Args args = new Args(command);
			return new ImmutableServoCommand<Integer>(args.channel, args.position, args.speed, args.acceleration);
		} catch (Exception e) {
			throw new UnknownCommandException(command);
		}
	}

	private static class Args {
		private final String[] args;
		final int channel;
		final double position;
		final Double speed;
		final Double acceleration;
		public Args(String command) {
			args = command.split(" ");
			channel = Integer.parseInt(args[0]);
			position = Double.parseDouble(args[1]);
			speed = doubleIfPresent(2);
			acceleration = doubleIfPresent(3);
		}
		private Double doubleIfPresent(int i) {
			return (args.length <= i) ? null : Double.parseDouble(args[i]);
		}
	}
}