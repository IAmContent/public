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
package com.iamcontent.device.servo.command;

import java.util.List;

import com.iamcontent.device.servo.ServoSource;

/**
 * A {@link ServoCommandExecutor} that executes {@link ServoCommand}s sequentially.
 * @author Greg Elderfield
 */
public class SequentialServoCommandExecutor implements ServoCommandExecutor {
	
	final ServoSource servoSource;

	public static SequentialServoCommandExecutor executor(ServoSource servoSource) {
		return new SequentialServoCommandExecutor(servoSource);
	}

	public SequentialServoCommandExecutor(ServoSource servoSource) {
		this.servoSource = servoSource;
	}
	
	@Override
	public void execute(List<? extends ServoCommand> commands) {
		for (ServoCommand c : commands)
			execute(c);
	}

	@Override
	public void execute(ServoCommand command) {
		final int channel = command.getChannel();
		setAcceleration(channel, command.getAcceleration());
		setSpeed(channel, command.getSpeed());
		setPosition(channel, command.getPosition());
	}

	private void setAcceleration(int channel, Double acceleration) {
		if (acceleration!=null)
			servoSource.getServo(channel).setAcceleration(acceleration);
	}

	private void setSpeed(int channel, Double speed) {
		if (speed!=null)
			servoSource.getServo(channel).setSpeed(speed);
	}

	private void setPosition(int channel, Double position) {
		if (position!=null)
			servoSource.getServo(channel).setPosition(position);
	}
}
