package com.iamcontent.robot.controller.pololu;

import static com.iamcontent.io.util.BufferedReaderIterator.bufferedReaderIterator;
import static com.iamcontent.io.util.Readers.bufferedReader;

import java.util.Arrays;
import java.util.Collection;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.iamcontent.io.util.BufferedReaderIterator;

public class PololuCommandLineDriver {

	public static void main(String[] args) {
		final PololuMicroMaestro6ChannelUsbServoController device = new PololuMicroMaestro6ChannelUsbServoController();
		
		for (String s : commandStrings())
			device.setServoPosition(0, parse(s));
	}


	private static int parse(String s) {
		try {
			return Integer.parseInt(s);
		} catch(NumberFormatException e) {
			System.err.println("Ooops");
			return 0;
		}
	}


	private static Iterable<String> commandStrings() {
		return Iterables.filter(inputLines(), driverCommandHandler());
	}
	private static BufferedReaderIterator inputLines() {
		return bufferedReaderIterator(bufferedReader(System.in));
	}

	private static final Collection<String> QUIT_COMMANDS = Arrays.asList(null, "q", "quit", "exit", "bye");

	private static Predicate<String> driverCommandHandler() {
		return new Predicate<String>() {
			@Override
			public boolean apply(String command) {
				return !isLicenseInstruction(command);
			}

			private boolean isLicenseInstruction(String command) {
				switch (command) {
				default:
					if (isQuit(command))
						quit();
					return false;
				}
			}
			
			private boolean isQuit(String command) {
				return QUIT_COMMANDS.contains(tidied(command));
			}

			protected String tidied(String command) {
				return command.trim().toLowerCase();
			}
			
			private void quit() {
				System.out.println("Bye");
				System.exit(0);
			}
		};
	}

}
