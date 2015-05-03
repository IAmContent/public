package com.iamcontent.device.controller.pololu;

import static com.iamcontent.core.MathUtils.linearConvert;

import javax.usb.UsbDevice;

import com.iamcontent.device.servo.NormalizingServo;
import com.iamcontent.device.servo.Servo;
import com.iamcontent.device.servo.ServoController;

public class PololuMaestroUsbServoController extends PololuMaestroUsbServoCard implements ServoController {
	
	private static final double MIN_TARGET = 4000;
	private static final double MAX_TARGET = 8000;
	
	/**
	 * Creates an instance with the first Pololu Maestro device that is found.
	 */
	public PololuMaestroUsbServoController() {
		super();
	}

	/**
	 * Creates an instance with the given UsbDevice.
	 */
	public PololuMaestroUsbServoController(UsbDevice device) {
		super(device);
	}

	@Override
	public Servo getServo(int channel) {
		return new PololuServo((short)channel);
	}

	/**
	 * A {@link NormalizingServo} that is attached to this {@link ServoController} and converts between normalized
	 * values and those used by Pololu Maestro cards.
	 */
	private class PololuServo extends NormalizingServo {
		final short channel;
		
		public PololuServo(short channel) {
			this.channel = channel;
		}

		@Override
		public void setLimitedPosition(double position) {
			setTarget(channel, positionToTarget(position));
		}

		@Override
		public double getPosition() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void setLimitedSpeed(double speed) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setLimitedAcceleration(double speed) {
			// TODO Auto-generated method stub
			
		}
		
		private short positionToTarget(double position) {
			return (short)linearConvert(position, MIN, MAX, MIN_TARGET, MAX_TARGET);
		}
	}
}
