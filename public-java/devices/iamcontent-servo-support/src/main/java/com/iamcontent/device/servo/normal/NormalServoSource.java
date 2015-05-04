package com.iamcontent.device.servo.normal;

import com.iamcontent.device.servo.Servo;
import com.iamcontent.device.servo.ServoSource;

public class NormalServoSource implements ServoSource {
	
	private final ServoSource delegate;
	
	public static NormalServoSource normalized(ServoSource source) {
		return new NormalServoSource(source);
	}
	
	public NormalServoSource(ServoSource delegate) {
		this.delegate = delegate;
	}

	@Override
	public Servo getServo(int channel) {
		return new NormalServo(delegate.getServo(channel));
	}
}
