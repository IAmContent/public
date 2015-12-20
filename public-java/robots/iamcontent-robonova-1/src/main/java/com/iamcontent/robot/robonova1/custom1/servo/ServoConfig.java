package com.iamcontent.robot.robonova1.custom1.servo;

import com.google.common.base.Function;
import com.iamcontent.device.servo.calibrate.ServoSourceCalibrator;
import com.iamcontent.device.servo.config.ServoConfigFunctions;
import com.iamcontent.robot.robonova1.ServoId;

/**
 * {@link ServoConfigFunctions} for the servos of Greg's custom Robonova (called number 1).
 * 
 * @author Greg Elderfield
 */
public class ServoConfig implements ServoConfigFunctions<Integer, ServoId> {

	@Override
	public ServoSourceCalibrator<Integer> normalizingCalibrator() {
		return new ServoNormalization();
	}

	@Override
	public Function<ServoId, Integer> channelTranslation() {
		return new ServoChannelTranslation();
	}

	@Override
	public ServoSourceCalibrator<ServoId> tuningCalibrator() {
		return new ServoTuning();
	}
}
