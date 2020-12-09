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
package com.iamcontent.device.controller.pololu.maestro;

import java.util.Collection;

import com.iamcontent.device.servo.ServoSource;
import com.iamcontent.device.servo.command.ServoCommand;
import com.iamcontent.device.servo.command.ServoCommandLineDriver;

/**
 * A {@link ServoCommandLineDriver} for the {@link PololuMaestroServoController}. Useful for manual testing.
 * @author Greg Elderfield
 */
public class PololuCommandLineDriver extends ServoCommandLineDriver<Integer> {

	private final ServoSource<Integer> normalServos = DefaultPololuServoConfig.normalServos();
	
	public static void main(String[] args) {
		final PololuCommandLineDriver driver = new PololuCommandLineDriver();
		driver.run();
	}

	@Override
	protected ServoSource<Integer> servoSource() {
		return normalServos;
	}
	
	@Override
	protected Integer parseChannel(String s) {
		return Integer.parseInt(s);
	}

	@Override
	protected Collection<ServoCommand<Integer>> compoundCommand(String commandName) {
		throw new UnsupportedOperationException("No compound command supported.");
	}
}
