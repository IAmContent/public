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

import static com.iamcontent.device.servo.command.SimpleServoCommandExecutor.executor;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Stream;

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

	public ServoCommandLineDriver() {
		addCommandHandler(positionQueryCommandHandler());
		addCommandHandler(namedCommandHandler());
	}

	@Override
	public void run() {
		servoSource = servoSource();
		executor = executor(servoSource);
		commands().forEach(this::execute);
	}
	
	private void execute(ServoCommand<C> c) {
		if (c!=null)
			try {
				executor.execute(c);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
	}

	private Stream<ServoCommand<C>> commands() {
		return commandStrings().map(parsingFunction);
	}

	private final ParseStringIntoServoCommand<C> parsingFunction = new ParseStringIntoServoCommand<C>() {
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
				return servoSource.forChannel(channel).value().getValue();
			}
		};
	}

	private Predicate<String> namedCommandHandler() {
		return new PrefixCommandHandler("!") {
			@Override
			protected void executeCommand(String commandName) {
				execute(compoundCommand(commandName));
			}

			private void execute(Collection<ServoCommand<C>> commands) {
				for (ServoCommand<? extends C> c : commands)
					executor.execute(c);
			}
		};
	}

	protected abstract Collection<ServoCommand<C>> compoundCommand(String commandName);
	protected abstract ServoSource<C> servoSource();
	protected abstract C parseChannel(String s);
}
