package com.iamcontent.device.servo.raw;

import com.iamcontent.device.servo.Servo;

public class RawServo implements Servo {
	
	private final ServoController controller;
	private final int channel;
	
	public RawServo(ServoController controller, int channel) {
		this.controller = controller;
		this.channel = channel;
	}

	@Override
	public int getChannel() {
		return channel;
	}

	@Override
	public void setPosition(double position) {
		controller.setPosition(channel, position);
	}

	@Override
	public double getPosition() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSpeed(double speed) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setAcceleration(double acceleration) {
		// TODO Auto-generated method stub
	}
}
