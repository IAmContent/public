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
package com.iamcontent.robotics.arm.edge;

import static com.iamcontent.io.util.BufferedReaderIterator.bufferedReaderIterator;
import static com.iamcontent.io.util.Readers.bufferedReader;

import java.util.Arrays;
import java.util.Collection;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.iamcontent.robotics.arm.edge.action.Action;
import com.iamcontent.robotics.arm.edge.action.Actor;
import com.iamcontent.robotics.arm.edge.action.GeneralAction;
import com.iamcontent.robotics.arm.edge.action.LedAction;

/**
 * An example driver for the {@link RoboticEdgeArm}. Useful for manual testing.
 * @author Greg Elderfield
 */
public class RoboticEdgeArmCommandLineDriver implements Runnable {
	final RoboticEdgeArm arm = new RoboticEdgeArm();
	
	public static void main(String[] args) {
		final RoboticEdgeArmCommandLineDriver driver = new RoboticEdgeArmCommandLineDriver();
		driver.run();
	}
	
	@Override
	public void run() {
		for (Action a : actions()) {
			execute(a);
			if (a==QUIT)
				break;
		}
		hibernateDevice();
	}

	private void hibernateDevice() {
		execute(GeneralAction.STOP_ALL_MOVEMENT);
		execute(LedAction.OFF);
	}

	private Iterable<Action> actions() {
		return Iterables.transform(commandIterator(), PARSING_FUNCTION);
	}

	private Iterable<String> commandIterator() {
		return bufferedReaderIterator(bufferedReader(System.in));
	}

	private void execute(Action action) {
		action.applyTo(arm);
	}

	private static final Collection<String> QUIT_COMMANDS = Arrays.asList(null, "q", "quit", "exit", "bye");

	private static final Function<String, Action> PARSING_FUNCTION = new ParseStringIntoActionFunction() {
		@Override
		public Action apply(String command) {
			if (isQuit(command))
				return QUIT;
			return super.apply(command);
		}

		private boolean isQuit(String command) {
			return QUIT_COMMANDS.contains(tidied(command));
		}
	};
			
	private static final Action QUIT = new Action() {
		@Override
		public void applyTo(Actor actor) {
			System.out.println("Bye");
		}
	};
}
