package com.iamcontent.device.servo.raw;

import com.iamcontent.device.servo.Servo;
import com.iamcontent.device.servo.ServoSource;
import com.iamcontent.device.servo.raw.RawServo;

public class RawServoSource implements ServoSource {
	
	private final ServoController controller;
	
	public static RawServoSource rawServoSource(ServoController controller) {
		return new RawServoSource(controller);
	}
	/**
	 * Creates an instance for the given {@link ServoController}.
	 */
	public RawServoSource(ServoController controller) {
		this.controller = controller;
	}

	@Override
	public Servo getServo(int channel) {
		return new RawServo(controller, channel);
	}
}
