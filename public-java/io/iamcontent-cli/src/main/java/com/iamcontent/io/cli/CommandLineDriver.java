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
package com.iamcontent.io.cli;

import static com.iamcontent.io.cli.LicenseWriter.licenseWriter;
import static com.iamcontent.io.util.Readers.bufferedReader;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Foundation class for Command Line Interface (CLI) drivers.
 * @author Greg Elderfield
 */
public abstract class CommandLineDriver {

	private final LicenseWriter licenseWriter = licenseWriter();

	protected final BufferedReader in;
	private final List<Predicate<String>> commandHandlers = new ArrayList<>();
	
	public CommandLineDriver() {
		this(bufferedReader(System.in));
		addCommandHandler(warrantyCommandHandler());
		addCommandHandler(licenseCommandHandler());
		addCommandHandler(quitCommandHandler());
	}
	
	public CommandLineDriver(BufferedReader in) {
		this.in = in;
	}

	public static String tidied(String command) {
		return command.trim().toLowerCase();
	}
	
	protected Stream<String> commandStrings() {
		licenseWriter.printInteractiveInstructions();
		return inputLines().filter(commandHandled().negate());
	}
	
	private Stream<String> inputLines() {
		displayPrompt();
		return in.lines().peek(l -> displayPrompt());
	}
	
	private void displayPrompt() {
		System.out.print("> ");
		System.out.flush();
	}

	private Predicate<String> commandHandled() {
		return s -> commandHandlers.stream().anyMatch(p -> p.test(s));
	}
	
	protected void onQuit() {
	}
	
	protected void addCommandHandler(Predicate<String> handler) {
		commandHandlers.add(handler);
	}
	
	private CommandHandler warrantyCommandHandler() {
		return new LiteralCommandHandler("warranty") {
			@Override
			protected void executeCommand(String argument) {
				licenseWriter.printWarranty();
			}
		};
	}
	
	private CommandHandler licenseCommandHandler() {
		return new LiteralCommandHandler("license", "licence") {
			@Override
			protected void executeCommand(String argument) {
				licenseWriter.printTermsAndConditions();
			}
		};
	}
	
	private CommandHandler quitCommandHandler() {
		return new LiteralCommandHandler(null, "q", "quit", "exit", "bye") {
			@Override
			protected void executeCommand(String argument) {
				onQuit();
				System.out.println("Bye");
				System.exit(0);
			}
		};
	}
}
