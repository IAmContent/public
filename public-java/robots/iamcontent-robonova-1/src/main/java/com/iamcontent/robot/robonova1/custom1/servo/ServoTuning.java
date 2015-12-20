package com.iamcontent.robot.robonova1.custom1.servo;

import static com.iamcontent.device.servo.calibrate.ImmutableServoCalibrator.NULL_SERVO_CALIBRATOR;
import static com.iamcontent.device.servo.calibrate.ImmutableServoCalibrator.REVERSED_SERVO_CALIBRATOR;
import static com.iamcontent.device.servo.calibrate.ImmutableServoCalibrator.calibrateValueFromNormalTo;
import static com.iamcontent.robot.robonova1.ServoId.LEFT_ANKLE_INVERTER;
import static com.iamcontent.robot.robonova1.ServoId.LEFT_ANKLE_PLANTARFLEXOR;
import static com.iamcontent.robot.robonova1.ServoId.LEFT_ELBOW_FLEXOR;
import static com.iamcontent.robot.robonova1.ServoId.LEFT_HIP_ABDUCTOR;
import static com.iamcontent.robot.robonova1.ServoId.LEFT_KNEE_FLEXOR;
import static com.iamcontent.robot.robonova1.ServoId.RIGHT_ANKLE_INVERTER;
import static com.iamcontent.robot.robonova1.ServoId.RIGHT_ANKLE_PLANTARFLEXOR;
import static com.iamcontent.robot.robonova1.ServoId.RIGHT_HIP_ABDUCTOR;
import static com.iamcontent.robot.robonova1.ServoId.RIGHT_HIP_FLEXOR;
import static com.iamcontent.robot.robonova1.ServoId.RIGHT_KNEE_FLEXOR;
import static com.iamcontent.robot.robonova1.ServoId.RIGHT_SHOULDER_ABDUCTOR;
import static com.iamcontent.robot.robonova1.ServoId.RIGHT_SHOULDER_FLEXOR;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.iamcontent.device.servo.calibrate.DefaultingServoSourceCalibrator;
import com.iamcontent.device.servo.calibrate.ServoCalibrator;
import com.iamcontent.robot.robonova1.ServoId;

/**
 * A {@link ServoCalibrator} for tuning the servos of Greg's custom Robonova (called number 1).
 * 
 * @author Greg Elderfield
 */
public class ServoTuning extends DefaultingServoSourceCalibrator<ServoId> {

	public ServoTuning() {
		super(NULL_SERVO_CALIBRATOR, perServoCalibrators());
	}

	private static Map<ServoId, ServoCalibrator> perServoCalibrators() {
		return mapBuilder()
				.put(RIGHT_SHOULDER_FLEXOR, REVERSED_SERVO_CALIBRATOR)
				.put(RIGHT_SHOULDER_ABDUCTOR, REVERSED_SERVO_CALIBRATOR)
				.put(LEFT_ELBOW_FLEXOR, REVERSED_SERVO_CALIBRATOR)
				.put(LEFT_HIP_ABDUCTOR, calibrateValueFromNormalTo(0.6, 0.0)) // Physical stop on full adduction
				.put(RIGHT_HIP_ABDUCTOR, calibrateValueFromNormalTo(0.4, 1.0)) // Physical stop on full adduction
				.put(RIGHT_HIP_FLEXOR, REVERSED_SERVO_CALIBRATOR)
				.put(LEFT_KNEE_FLEXOR, calibrateValueFromNormalTo(0.05, 0.95)) // Hard stop and some L/R asymmetry
				.put(RIGHT_KNEE_FLEXOR, calibrateValueFromNormalTo(0.98, 0.08)) // Hard stop and some L/R asymmetry
				.put(LEFT_ANKLE_PLANTARFLEXOR, calibrateValueFromNormalTo(0.15, 1.0)) // Hard stop
				.put(RIGHT_ANKLE_PLANTARFLEXOR, calibrateValueFromNormalTo(0.85, 0.0)) // Hard stop
				.put(LEFT_ANKLE_INVERTER, calibrateValueFromNormalTo(0.55, 0.25)) // Hard stop
				.put(RIGHT_ANKLE_INVERTER, calibrateValueFromNormalTo(0.45, 0.75)) // Hard stop
				.build();
	}

	private static Builder<ServoId, ServoCalibrator> mapBuilder() {
		return ImmutableMap.<ServoId, ServoCalibrator>builder();
	}
}
