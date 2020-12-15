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
package com.iamcontent.device.controller.owi;

import static com.iamcontent.io.usb.EasedUsbDevice.eased;
import static java.util.Arrays.asList;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.usb.UsbConst;
import javax.usb.UsbControlIrp;
import javax.usb.UsbDevice;

import com.iamcontent.io.usb.EasyUsbDevice;
import com.iamcontent.io.usb.Usb;

/**
 * Issues {@link Command} instructions via USB to the OWI-535 5-motor controller, which is the controller
 * commonly used in the OWI/Maplin Robotic Edge Arm.
 * @author Greg Elderfield
 */
public class Owi535UsbMotorController {
	public enum Actuator {
		MOTOR_1(0), MOTOR_2(0), MOTOR_3(0), MOTOR_4(0), MOTOR_5(1), LED(2);

		final int commandByteIndex;

		private Actuator(int commandByteIndex) {
			this.commandByteIndex = commandByteIndex;
		}
	};

	public enum Command {
		MOTOR_1_FORWARD(Actuator.MOTOR_1, (byte) 0x01), MOTOR_1_BACKWARD(Actuator.MOTOR_1, (byte) 0x02), MOTOR_1_STOP(Actuator.MOTOR_1, (byte) 0x00),
		MOTOR_2_FORWARD(Actuator.MOTOR_2, (byte) 0x04), MOTOR_2_BACKWARD(Actuator.MOTOR_2, (byte) 0x08), MOTOR_2_STOP(Actuator.MOTOR_2, (byte) 0x00),
		MOTOR_3_FORWARD(Actuator.MOTOR_3, (byte) 0x10), MOTOR_3_BACKWARD(Actuator.MOTOR_3, (byte) 0x20), MOTOR_3_STOP(Actuator.MOTOR_3, (byte) 0x00),
		MOTOR_4_FORWARD(Actuator.MOTOR_4, (byte) 0x40), MOTOR_4_BACKWARD(Actuator.MOTOR_4, (byte) 0x80), MOTOR_4_STOP(Actuator.MOTOR_4, (byte) 0x00),
		MOTOR_5_FORWARD(Actuator.MOTOR_5, (byte) 0x01), MOTOR_5_BACKWARD(Actuator.MOTOR_5, (byte) 0x02), MOTOR_5_STOP(Actuator.MOTOR_5, (byte) 0x00),
		LED_ON(Actuator.LED, (byte) 0x01), LED_OFF(Actuator.LED, (byte) 0x00);

		final Actuator actuator;
		final byte bits;

		private Command(Actuator actuator, byte bits) {
			this.actuator = actuator;
			this.bits = bits;
		}

		public Actuator getActuator() {
			return actuator;
		}
	}

	private final EasyUsbDevice device;

	private final Map<Actuator, Command> currentCommands = everythingTurnedOff();

	/**
	 * Creates an instance with the first OWI 535 device that is found.
	 */
	public Owi535UsbMotorController() {
		final short defaultVendorId = 0x1267;
		final short defaultProductId = 0x0;
		device = Usb.device(defaultVendorId, defaultProductId);
	}

	/**
	 * Creates an instance with the given UsbDevice.
	 */
	public Owi535UsbMotorController(UsbDevice device) {
		this.device = eased(device);
	}

	public Command getCurrentCommand(Actuator a) {
		return currentCommands.get(a);
	}

	public boolean execute(Command c) {
		final boolean stateHasChanged = commandUpdated(c);
		if (stateHasChanged)
			sendStateToDevice();
		return stateHasChanged;
	}

	public boolean execute(Collection<Command> commands) {
		boolean stateHasChanged = anyCommandUpdated(commands);
		if (stateHasChanged)
			sendStateToDevice();
		return stateHasChanged;
	}

	private boolean anyCommandUpdated(Collection<Command> commands) {
		boolean stateHasChanged = false;
		for (Command c : commands)
			stateHasChanged |= commandUpdated(c);
		return stateHasChanged;
	}

	private boolean commandUpdated(Command c) {
		final Actuator a = c.actuator;
		if (getCurrentCommand(a) != c) {
			currentCommands.put(a, c);
			return true;
		}
		return false;
	}

	private static final List<Command> STOP_ALL_MOTORS = Arrays.asList(Command.MOTOR_1_STOP, Command.MOTOR_2_STOP, Command.MOTOR_3_STOP, Command.MOTOR_4_STOP, Command.MOTOR_5_STOP);
	public boolean stopAllMovement() {
		return execute(STOP_ALL_MOTORS);
	}

	private static final List<Command> TURN_OFF_EVERYTHING = asList(Command.MOTOR_1_STOP, Command.MOTOR_2_STOP, Command.MOTOR_3_STOP, Command.MOTOR_4_STOP, Command.MOTOR_5_STOP, Command.LED_OFF);
	public boolean turnOffEverything() {
		return execute(TURN_OFF_EVERYTHING);
	}

	/**
	 * Sends a control message to the device. The message that is sent is the
	 * message that is required to synchronize the device's state with the state
	 * of this instance.
	 */
	protected void sendStateToDevice() {
		final UsbControlIrp controlRequest = controlRequest();
		controlRequest.setData(currentState());
		device.syncSubmit(controlRequest);
	}

	protected UsbControlIrp controlRequest() {
		final byte requestType = UsbConst.REQUESTTYPE_TYPE_VENDOR;
		final byte request = UsbConst.REQUEST_GET_DESCRIPTOR;
		final short value = 0x100;
		final short index = 0;
		return device.createUsbControlIrp(requestType, request, value, index);
	}

	private byte[] currentState() {
		final byte[] result = {0, 0, 0};
		for (Command c : currentCommands.values())
			result[c.actuator.commandByteIndex] |= c.bits;
		return result;
	}

	private EnumMap<Actuator, Command> everythingTurnedOff() {
		final EnumMap<Actuator, Command>result = new EnumMap<Actuator, Command>(Actuator.class);
		for (Command c : TURN_OFF_EVERYTHING)
			result.put(c.actuator, c);
		return result;
	}
}
