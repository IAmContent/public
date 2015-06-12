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

import static com.iamcontent.device.servo.command.SequentialServoCommandExecutor.executor;
import static com.iamcontent.device.servo.command.ServoCommands.servoCommands;

import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.iamcontent.device.servo.Servo;
import com.iamcontent.device.servo.ServoSource;
import com.iamcontent.io.cli.CommandHandler;
import com.iamcontent.io.cli.CommandLineDriver;
import com.iamcontent.io.cli.PrefixCommandHandler;

/**
 * An abstract {@link CommandLineDriver} for the control of {@link Servo}s. Useful for manual testing.
 * @author Greg Elderfield
 * 
 * @param <C> The type used to identify the channel of a {@link Servo}. 
 */
public abstract class ServoCommandLineDriver<C> extends CommandLineDriver implements Runnable {

	private ServoSource<C> servoSource;
	private ServoCommandExecutor<C> executor;
	private Class<C> channelClass;

	public ServoCommandLineDriver(Class<C> channelClass) {
		this.channelClass = channelClass;
		addCommandHandler(positionQueryCommandHandler());
		addCommandHandler(namedCommandHandler());
	}

	@Override
	public void run() {
		servoSource = servoSource();
		executor = executor(servoSource);
		for (ServoCommand<C> c : commands())
			execute(c);
	}
	
	private void execute(ServoCommand<C> c) {
		if (c!=null)
			executor.execute(c);
	}

	private Iterable<ServoCommand<C>> commands() {
		return Iterables.transform(commandStrings(), parsingFunction);
	}

	private final ParseStringIntoServoCommandFunction<C> parsingFunction = new ParseStringIntoServoCommandFunction<C>() {
		@Override
		public ServoCommand<C> apply(String command) {
			try {
				return super.apply(command);
			} catch (Exception e) {
				System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
				return null;
			}
		}

		@Override
		protected C parseChannel(String s) {
			return ServoCommandLineDriver.this.parseChannel(s);
		}
	};

	private CommandHandler positionQueryCommandHandler() {
		return new PrefixCommandHandler("?") {
			@Override
			protected void executeCommand(String channelString) {
				displayPosition(parseChannel(channelString));
			}

			private void displayPosition(C channel) {
				System.out.println("position(" + channel + ")=" + position(channel));
			}

			private double position(C channel) {
				return servoSource.getServo(channel).getPosition();
			}
		};
	}

	private Predicate<String> namedCommandHandler() {
		return new PrefixCommandHandler("!") {
			@Override
			protected void executeCommand(String commandName) {
				final String fileName = commandFolder() + commandName;
				executeCommands(servoCommands(fileName, channelClass));
			}

			private void executeCommands(final List<ServoCommand<C>> commands) {
				executor.execute(commands);
			}
		};
	}

	protected String commandFolder() {
		return "servo/commands/";
	}

	protected abstract ServoSource<C> servoSource();
	protected abstract C parseChannel(String s);
}
