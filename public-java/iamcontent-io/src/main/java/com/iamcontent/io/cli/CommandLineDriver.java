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
package com.iamcontent.io.cli;

import static com.iamcontent.io.license.LicenseWriter.licenseWriter;
import static com.iamcontent.io.util.Readers.bufferedReader;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Collection;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.iamcontent.io.license.LicenseWriter;
import com.iamcontent.io.util.BufferedReaderIterator;

/**
 * Foundation class for Command Line Interface (CLI) drivers.
 * @author Greg Elderfield
 */
public abstract class CommandLineDriver {

	private final LicenseWriter licenseWriter = licenseWriter();
	private static final Collection<String> QUIT_COMMANDS = Arrays.asList(null, "q", "quit", "exit", "bye");

	protected final BufferedReader in;
	
	public CommandLineDriver() {
		this(bufferedReader(System.in));
	}
	
	public CommandLineDriver(BufferedReader in) {
		this.in = in;
	}

	protected Iterable<String> commandStrings() {
		licenseWriter.printInteractiveInstructions();
		return Iterables.filter(inputLines(), driverCommandHandler());
	}
	
	private BufferedReaderIterator inputLines() {
		return new BufferedReaderIterator(in) {
			@Override
			public String next() {
				System.out.print("> ");
				System.out.flush();
				return super.next();
			}
		};
	}

	private Predicate<String> driverCommandHandler() {
		return new Predicate<String>() {
			@Override
			public boolean apply(String command) {
				return !isDriverCommand(command);
			}

			private boolean isDriverCommand(String command) {
				switch (command) {
				case "license":
					licenseWriter.printTermsAndConditions();
					return true;
				case "warranty":
					licenseWriter.printWarranty();
					return true;
				default:
					if (isQuit(command))
						quit();
					return false;
				}
			}
			
			private void quit() {
				onQuit();
				System.out.println("Bye");
				System.exit(0);
			}
		};
	}
	
	protected boolean isQuit(String command) {
		return QUIT_COMMANDS.contains(tidied(command));
	}
	
	protected String tidied(String command) {
		return command.trim().toLowerCase();
	}

	protected void onQuit() {
	}
}
