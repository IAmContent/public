/**
  IAmContent Public Libraries.
  Copyright (C) 2015 Greg Elderfield
  @author Greg Elderfield, iamcontent@jarchitect.co.uk
 
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
package com.iamcontent.robot.arm.edge;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.iamcontent.io.cli.CommandLineDriver;
import com.iamcontent.io.cli.UnknownCommandException;
import com.iamcontent.robot.arm.edge.RoboticEdgeArm.Command;

/**
 * An example driver for the {@link RoboticEdgeArm}. Useful for manual testing.
 * @author Greg Elderfield
 */
public class RoboticEdgeArmCommandLineDriver extends CommandLineDriver implements Runnable {
	private static final Command TURN_OFF_EVERYTHING = null;
	
	private final RoboticEdgeArm arm = new RoboticEdgeArm();
	
	public static void main(String[] args) {
		final RoboticEdgeArmCommandLineDriver driver = new RoboticEdgeArmCommandLineDriver();
		driver.run();
	}
	
	@Override
	public void run() {
		for (Command c : commands())
			execute(c);
		turnOffEverything();
	}

	@Override
	protected void onQuit() {
		turnOffEverything();
	}

	private Iterable<Command> commands() {
		return Iterables.transform(commandStrings(), parsingFunction);
	}

	private void execute(Command c) {
		if (c==TURN_OFF_EVERYTHING)
			turnOffEverything();
		else
			arm.execute(c);
	}

	private void turnOffEverything() {
		arm.turnOffEverything();
	}

	private final Function<String, Command> parsingFunction = new ParseStringIntoCommandFunction() {
		@Override
		public Command apply(String command) {
			try {
				return super.apply(command);
			} catch (UnknownCommandException e) {
				System.out.println(e.getMessage());
				return TURN_OFF_EVERYTHING;
			}
		}
	};
}
