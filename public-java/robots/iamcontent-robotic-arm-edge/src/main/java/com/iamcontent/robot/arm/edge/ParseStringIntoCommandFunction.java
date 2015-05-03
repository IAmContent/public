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
import com.iamcontent.robot.arm.edge.RoboticEdgeArm.Command;

/**
 * A function to parse a String command into a {@link Command}.
 */
public class ParseStringIntoCommandFunction implements Function<String, Command> {

	@Override
	public Command apply(String command) {
		switch (tidied(command)) {
		
		case "base left":
		case "bl":
			return Command.BASE_LEFT;
		case "base right":
		case "br":
			return Command.BASE_RIGHT;
		case "base stop":
		case "bs":
			return Command.BASE_STOP;
			
		case "shoulder forward":
		case "shoulder forwards":
		case "sf":
			return Command.SHOULDER_FORWARD;
		case "shoulder backward":
		case "shoulder backwards":
		case "sb":
			return Command.SHOULDER_BACKWARD;
		case "shoulder stop":
		case "ss":
			return Command.SHOULDER_STOP;
			
		case "elbow extend":
		case "ee":
			return Command.ELBOW_EXTEND;
		case "elbow flex":
		case "ef":
			return Command.ELBOW_FLEX;
		case "elbow stop":
		case "es":
			return Command.ELBOW_STOP;
			
		case "wrist extend":
		case "we":
			return Command.WRIST_EXTEND;
		case "wrist flex":
		case "wf":
			return Command.WRIST_FLEX;
		case "wrist stop":
		case "ws":
			return Command.WRIST_STOP;
			
			
		case "grip open":
		case "gripper open":
		case "go":
			return Command.GRIPPER_OPEN;
		case "grip close":
		case "gripper close":
		case "gc":
			return Command.GRIPPER_CLOSE;
		case "grip stop":
		case "gripper stop":
		case "gs":
			return Command.GRIPPER_STOP;
			
		case "led on":
		case "light on":
		case "l+":
			return Command.LED_ON;
		case "led off":
		case "light off":
		case "l-":
			return Command.LED_OFF;

		case "stop":
		case "":
			return null;

		default:
			throw new UnknownCommandException(command);
		}
	}

	protected String tidied(String command) {
		return command.trim().toLowerCase();
	}
	
	public static class UnknownCommandException extends RuntimeException {
		public UnknownCommandException(String command) {
			super("Unknown command: " + command);
		}

		private static final long serialVersionUID = 1L;
	}
}