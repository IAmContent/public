package com.iamcontent.robot.robonova1.custom1.servo;

import com.google.common.base.Function;
import com.iamcontent.robot.robonova1.ServoId;

final class ServoChannelTranslation implements Function<ServoId, Integer> {
	@Override
	public Integer apply(ServoId servoId) {
		switch (servoId) {
		case LEFT_SHOULDER_FLEXOR:
			return 0;
		case RIGHT_SHOULDER_FLEXOR:
			return 1;
		case LEFT_SHOULDER_ABDUCTOR:
			return 2;
		case RIGHT_SHOULDER_ABDUCTOR:
			return 3;
		case LEFT_ELBOW_FLEXOR:
			return 4;
		case RIGHT_ELBOW_FLEXOR:
			return 5;
			
		case LEFT_HIP_ABDUCTOR:
			return 14;
		case RIGHT_HIP_ABDUCTOR:
			return 15;
		case LEFT_HIP_FLEXOR:
			return 16;
		case RIGHT_HIP_FLEXOR:
			return 17;
		case LEFT_KNEE_FLEXOR:
			return 18;
		case RIGHT_KNEE_FLEXOR:
			return 19;
		case LEFT_ANKLE_PLANTARFLEXOR:
			return 20;
		case RIGHT_ANKLE_PLANTARFLEXOR:
			return 21;
		case LEFT_ANKLE_INVERTER:
			return 22;
		case RIGHT_ANKLE_INVERTER:
			return 23;
		default:
			throw new IllegalStateException("Servo id not recognized: " + servoId);
		}
	}
}