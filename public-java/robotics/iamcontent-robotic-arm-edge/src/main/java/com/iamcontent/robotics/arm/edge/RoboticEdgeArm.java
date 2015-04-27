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
package com.iamcontent.robotics.arm.edge;

import java.util.Collection;
import java.util.EnumMap;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.iamcontent.robotics.control.hardware.owi535.Owi535UsbMotorController;

/**
 * Issues {@link Command} instructions to the Maplin/OWI Robotic Edge Arm.
 * This class is a facade to the {@link Owi535UsbMotorController}, providing meaningful {@link Command}
 * names.
 * 
 * @author Greg Elderfield
 */
public class RoboticEdgeArm {

	public enum Actuator {
		GRIPPER(Owi535UsbMotorController.Actuator.MOTOR_1),
		WRIST(Owi535UsbMotorController.Actuator.MOTOR_2),
		ELBOW(Owi535UsbMotorController.Actuator.MOTOR_3),
		SHOULDER(Owi535UsbMotorController.Actuator.MOTOR_4),
		BASE(Owi535UsbMotorController.Actuator.MOTOR_5),
		LED(Owi535UsbMotorController.Actuator.LED);
		
		final Owi535UsbMotorController.Actuator controllerActuator;

		private Actuator(Owi535UsbMotorController.Actuator actuator) {
			this.controllerActuator = actuator;
		}
		public Owi535UsbMotorController.Actuator getControllerActuator() {
			return controllerActuator;
		}
	};

	public enum Command {
		GRIPPER_CLOSE(Actuator.GRIPPER, Owi535UsbMotorController.Command.MOTOR_1_FORWARD),
		GRIPPER_OPEN(Actuator.GRIPPER, Owi535UsbMotorController.Command.MOTOR_1_BACKWARD),
		GRIPPER_STOP(Actuator.GRIPPER, Owi535UsbMotorController.Command.MOTOR_1_STOP),
		
		WRIST_EXTEND(Actuator.WRIST, Owi535UsbMotorController.Command.MOTOR_2_FORWARD),
		WRIST_FLEX(Actuator.WRIST, Owi535UsbMotorController.Command.MOTOR_2_BACKWARD),
		WRIST_STOP(Actuator.WRIST, Owi535UsbMotorController.Command.MOTOR_2_STOP),
		
		ELBOW_EXTEND(Actuator.ELBOW, Owi535UsbMotorController.Command.MOTOR_3_FORWARD),
		ELBOW_FLEX(Actuator.ELBOW, Owi535UsbMotorController.Command.MOTOR_3_BACKWARD),
		ELBOW_STOP(Actuator.ELBOW, Owi535UsbMotorController.Command.MOTOR_3_STOP),
		
		SHOULDER_BACKWARD(Actuator.SHOULDER, Owi535UsbMotorController.Command.MOTOR_4_FORWARD),
		SHOULDER_FORWARD(Actuator.SHOULDER, Owi535UsbMotorController.Command.MOTOR_4_BACKWARD),
		SHOULDER_STOP(Actuator.SHOULDER, Owi535UsbMotorController.Command.MOTOR_4_STOP),
		
		BASE_RIGHT(Actuator.BASE, Owi535UsbMotorController.Command.MOTOR_5_FORWARD),
		BASE_LEFT(Actuator.BASE, Owi535UsbMotorController.Command.MOTOR_5_BACKWARD),
		BASE_STOP(Actuator.BASE, Owi535UsbMotorController.Command.MOTOR_5_STOP),
		
		LED_ON(Actuator.LED, Owi535UsbMotorController.Command.LED_ON),
		LED_OFF(Actuator.LED, Owi535UsbMotorController.Command.LED_OFF);
		
		final Actuator actuator;
		final Owi535UsbMotorController.Command controllerCommand;

		private Command(Actuator actuator, Owi535UsbMotorController.Command controllerCommand) {
			this.actuator = actuator;
			this.controllerCommand = controllerCommand;
		}
		public Actuator getActuator() {
			return actuator;
		}
		public Owi535UsbMotorController.Command getControllerCommand() {
			return controllerCommand;
		}
	};
	
	private final Function<Command, Owi535UsbMotorController.Command> commandTranslation = new Function<Command, Owi535UsbMotorController.Command>() {
		@Override
		public Owi535UsbMotorController.Command apply(Command c) {
			return translated(c);
		}
	};
	
	private final EnumMap<Owi535UsbMotorController.Command, Command> reverseCommandTranslation = reverseCommandTranslation();
	
	private final Owi535UsbMotorController controller;
	
	/**
	 * Creates an instance with the first Robot Arm device that is found.
	 */
	public RoboticEdgeArm() {
		this(new Owi535UsbMotorController());
	}

	protected EnumMap<Owi535UsbMotorController.Command, Command> reverseCommandTranslation() {
		final EnumMap<Owi535UsbMotorController.Command, Command>result = new EnumMap<Owi535UsbMotorController.Command, Command>(Owi535UsbMotorController.Command.class);
		for (Command c : Command.values())
			result.put(c.controllerCommand, c);
		return result;
	}

	/**
	 * Creates an instance with the given controller.
	 */
	public RoboticEdgeArm(Owi535UsbMotorController controller) {
		this.controller = controller;
	}

	public Command getCurrentCommand(Actuator a) {
		return reverseTranslated(currentControllerCommand(a.controllerActuator));
	}

	public boolean execute(Command command) {
		return controller.execute(translated(command));
	}

	public boolean execute(List<Command> commands) {
		return controller.execute(translated(commands));
	}
	
	public boolean stopAllMovement() {
		return controller.stopAllMovement();
	}

	public boolean turnOffEverything() {
		return controller.turnOffEverything();
	}

	public Owi535UsbMotorController getController() {
		return controller;
	}
	
	protected Owi535UsbMotorController.Command translated(Command c) {
		return c.controllerCommand;
	}

	protected Collection<Owi535UsbMotorController.Command> translated(Collection<Command> commands) {
		return Collections2.transform(commands, commandTranslation);
	}

	protected Command reverseTranslated(Owi535UsbMotorController.Command c) {
		return reverseCommandTranslation.get(c);
	}

	protected Owi535UsbMotorController.Command currentControllerCommand(Owi535UsbMotorController.Actuator controllerActuator) {
		return controller.getCurrentCommand(controllerActuator);
	}
}
