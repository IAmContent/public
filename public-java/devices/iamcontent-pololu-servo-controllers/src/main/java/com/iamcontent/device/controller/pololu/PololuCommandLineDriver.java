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
package com.iamcontent.device.controller.pololu;

import static com.iamcontent.device.servo.command.SequentialServoCommandExecutor.executor;
import static com.iamcontent.device.servo.Servos.calibratedServoSource;

import com.google.common.collect.Iterables;
import com.iamcontent.device.servo.ServoSource;
import com.iamcontent.device.servo.command.ParseStringIntoServoCommandFunction;
import com.iamcontent.device.servo.command.ServoCommand;
import com.iamcontent.device.servo.command.ServoCommandExecutor;
import com.iamcontent.io.cli.CommandLineDriver;
import com.iamcontent.io.cli.UnknownCommandException;

/**
 * An example driver for the {@link PololuMaestroUsbServoController}. Useful for manual testing.
 * @author Greg Elderfield
 */
public class PololuCommandLineDriver extends CommandLineDriver implements Runnable {

	private final ServoCommandExecutor executor = executor(device());

	public static void main(String[] args) {
		final PololuCommandLineDriver driver = new PololuCommandLineDriver();
		driver.run();
	}

	@Override
	public void run() {
		for (ServoCommand c : commands())
			execute(c);
	}
	
	private void execute(ServoCommand c) {
		if (c!=null)
			executor.execute(c);
	}

	private Iterable<ServoCommand> commands() {
		return Iterables.transform(commandStrings(), parsingFunction);
	}

	private final ParseStringIntoServoCommandFunction parsingFunction = new ParseStringIntoServoCommandFunction() {
		@Override
		public ServoCommand apply(String command) {
			try {
				return super.apply(command);
			} catch (UnknownCommandException e) {
				System.out.println(e.getMessage());
				return null;
			}
		}
	};

	private ServoSource device() {
		return calibratedServoSource(PololuMaestroUsbServoController.defaultServoSource());
	}
}
