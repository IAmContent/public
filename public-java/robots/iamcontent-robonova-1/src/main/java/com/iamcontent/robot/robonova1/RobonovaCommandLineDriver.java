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
package com.iamcontent.robot.robonova1;

import static com.iamcontent.core.LangUtils.newInstance;
import static com.iamcontent.core.LangUtils.packageNameOf;
import static org.apache.commons.lang3.StringUtils.capitalize;

import com.iamcontent.device.servo.ServoSource;
import com.iamcontent.device.servo.command.CompoundServoCommand;
import com.iamcontent.device.servo.command.ServoCommandLineDriver;
import com.iamcontent.io.cli.UnknownCommandException;
import com.iamcontent.robot.robonova1.command.SitCommand;
import com.iamcontent.robot.robonova1.custom1.CustomRobonova;

/**
 * A {@link ServoCommandLineDriver} for a {@link Robonova}. Useful for manual testing.
 * @author Greg Elderfield
 */
public class RobonovaCommandLineDriver extends ServoCommandLineDriver<ServoId> {

	private static final String COMMAND_PACKAGE = packageNameOf(SitCommand.class);
	private final Robonova robonova = new CustomRobonova();
	
	public static void main(String[] args) {
		final RobonovaCommandLineDriver driver = new RobonovaCommandLineDriver();
		driver.run();
	}

	@Override
	protected ServoSource<ServoId> servoSource() {
		return robonova.servos();
	}

	@Override
	protected ServoId parseChannel(String s) {
		switch (tidied(s)) {
		case "lsf":
		case "sfl":
			return ServoId.LEFT_SHOULDER_FLEXOR;
		case "rsf":
		case "sfr":
			return ServoId.RIGHT_SHOULDER_FLEXOR;
		case "lsa":
		case "sal":
			return ServoId.LEFT_SHOULDER_ABDUCTOR;
		case "rsa":
		case "sar":
			return ServoId.RIGHT_SHOULDER_ABDUCTOR;
		case "lef":
		case "efl":
			return ServoId.LEFT_ELBOW_FLEXOR;
		case "ref":
		case "efr":
			return ServoId.RIGHT_ELBOW_FLEXOR;

		case "lha":
		case "hal":
			return ServoId.LEFT_HIP_ABDUCTOR;
		case "rha":
		case "har":
			return ServoId.RIGHT_HIP_ABDUCTOR;
		case "lhf":
		case "hfl":
			return ServoId.LEFT_HIP_FLEXOR;
		case "rhf":
		case "hfr":
			return ServoId.RIGHT_HIP_FLEXOR;
		case "lkf":
		case "kfl":
			return ServoId.LEFT_KNEE_FLEXOR;
		case "rkf":
		case "kfr":
			return ServoId.RIGHT_KNEE_FLEXOR;
		case "laf":
		case "afl":
		case "lapf":
		case "apfl":
			return ServoId.LEFT_ANKLE_PLANTARFLEXOR;
		case "raf":
		case "afr":
		case "rapf":
		case "apfr":
			return ServoId.RIGHT_ANKLE_PLANTARFLEXOR;
		case "lai":
		case "ail":
			return ServoId.LEFT_ANKLE_INVERTER;
		case "rai":
		case "air":
			return ServoId.RIGHT_ANKLE_INVERTER;

		default:
			throw new UnknownCommandException(s);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	protected CompoundServoCommand<ServoId> compoundCommand(String commandName) {
		return newInstance(commandClassName(commandName), CompoundServoCommand.class);
	}

	private String commandClassName(String commandName) {
		return String.format("%s.%sCommand", COMMAND_PACKAGE, capitalize(commandName));
	}
}
