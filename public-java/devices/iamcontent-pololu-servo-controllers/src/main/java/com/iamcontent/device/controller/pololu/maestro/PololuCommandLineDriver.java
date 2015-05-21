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
package com.iamcontent.device.controller.pololu.maestro;

import static com.iamcontent.device.controller.pololu.maestro.PololuMaestroServoController.pololuMaestroServoController;
import static com.iamcontent.device.servo.calibrate.Calibrators.calibrator;
import static com.iamcontent.device.servo.command.SequentialServoCommandExecutor.executor;

import com.google.common.collect.Iterables;
import com.iamcontent.device.controller.pololu.maestro.usb.UsbPololuMaestroServoCard;
import com.iamcontent.device.servo.ServoSource;
import com.iamcontent.device.servo.Servos;
import com.iamcontent.device.servo.command.ParseStringIntoServoCommandFunction;
import com.iamcontent.device.servo.command.ServoCommand;
import com.iamcontent.device.servo.command.ServoCommandExecutor;
import com.iamcontent.device.servo.raw.ServoController;
import com.iamcontent.io.cli.CommandLineDriver;

/**
 * An example driver for the {@link PololuMaestroServoController}. Useful for manual testing.
 * @author Greg Elderfield
 */
public class PololuCommandLineDriver extends CommandLineDriver implements Runnable {

	private static final String DEFAULT_CALIBRATOR_NAME = "pololu-maestro";
	private static final ServoSource SERVO_SOURCE = calibratedServoSource();
	private static final ServoCommandExecutor executor = executor(SERVO_SOURCE);

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
		private static final String QUERY_PREFIX = "?";
		@Override
		public ServoCommand apply(String command) {
			try {
				if (isQuery(command)) {
					executeQuery(command);
					return null;
				}
				return super.apply(command);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return null;
			}
		}

		private void executeQuery(String command) {
			displayPosition(channelString(command));
		}

		private String channelString(String command) {
			return command.substring(QUERY_PREFIX.length()).trim();
		}

		private void displayPosition(String channelString) {
			displayPosition(Integer.parseInt(channelString));
		}

		private void displayPosition(int channel) {
			System.out.println("position(" + channel + ")=" + getPosition(channel));
		}

		private double getPosition(int channel) {
			return SERVO_SOURCE.getServo(channel).getPosition();
		}

		private boolean isQuery(String command) {
			return command.startsWith(QUERY_PREFIX);
		}
	};

	private static ServoSource calibratedServoSource() {
		return Servos.calibratedServoSource(rawServoSource(), calibrator(DEFAULT_CALIBRATOR_NAME));
	}

	private static ServoSource rawServoSource() {
		return Servos.rawServoSource(defaultServoController());
	}

	private static ServoController defaultServoController() {
		return pololuMaestroServoController(UsbPololuMaestroServoCard.defaultInstance());
	}
}
