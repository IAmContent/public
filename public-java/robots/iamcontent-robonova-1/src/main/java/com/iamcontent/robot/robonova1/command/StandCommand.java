package com.iamcontent.robot.robonova1.command;

import static com.iamcontent.device.servo.command.ImmutableServoCommand.immutableCommand;
import static com.iamcontent.robot.robonova1.ServoId.LEFT_ANKLE_INVERTER;
import static com.iamcontent.robot.robonova1.ServoId.LEFT_ANKLE_PLANTARFLEXOR;
import static com.iamcontent.robot.robonova1.ServoId.LEFT_ELBOW_FLEXOR;
import static com.iamcontent.robot.robonova1.ServoId.LEFT_HIP_ABDUCTOR;
import static com.iamcontent.robot.robonova1.ServoId.LEFT_HIP_FLEXOR;
import static com.iamcontent.robot.robonova1.ServoId.LEFT_KNEE_FLEXOR;
import static com.iamcontent.robot.robonova1.ServoId.LEFT_SHOULDER_ABDUCTOR;
import static com.iamcontent.robot.robonova1.ServoId.LEFT_SHOULDER_FLEXOR;
import static com.iamcontent.robot.robonova1.ServoId.RIGHT_ANKLE_INVERTER;
import static com.iamcontent.robot.robonova1.ServoId.RIGHT_ANKLE_PLANTARFLEXOR;
import static com.iamcontent.robot.robonova1.ServoId.RIGHT_ELBOW_FLEXOR;
import static com.iamcontent.robot.robonova1.ServoId.RIGHT_HIP_ABDUCTOR;
import static com.iamcontent.robot.robonova1.ServoId.RIGHT_HIP_FLEXOR;
import static com.iamcontent.robot.robonova1.ServoId.RIGHT_KNEE_FLEXOR;
import static com.iamcontent.robot.robonova1.ServoId.RIGHT_SHOULDER_ABDUCTOR;
import static com.iamcontent.robot.robonova1.ServoId.RIGHT_SHOULDER_FLEXOR;

import com.iamcontent.device.servo.command.CompoundServoCommand;
import com.iamcontent.robot.robonova1.Robonova;
import com.iamcontent.robot.robonova1.ServoId;

/**
 * A compound command to put a {@link Robonova} into a sitting position.
 * @author Greg Elderfield
 */
public class StandCommand extends CompoundServoCommand<ServoId> {

	public StandCommand() {
		super(
			immutableCommand(LEFT_SHOULDER_FLEXOR, 0.5, 1.0, 0.02),
			immutableCommand(RIGHT_SHOULDER_FLEXOR, 0.5, 1.0, 0.02),
			immutableCommand(LEFT_SHOULDER_ABDUCTOR, 0.1, 1.0, 0.02),
			immutableCommand(RIGHT_SHOULDER_ABDUCTOR, 0.1, 1.0, 0.02),
			immutableCommand(LEFT_ELBOW_FLEXOR, 0.6, 1.0, 0.02),
			immutableCommand(RIGHT_ELBOW_FLEXOR, 0.6, 1.0, 0.02),
			
			immutableCommand(LEFT_HIP_ABDUCTOR, 0.18, 1.0, 0.02),
			immutableCommand(RIGHT_HIP_ABDUCTOR, 0.18, 1.0, 0.02),
			immutableCommand(LEFT_HIP_FLEXOR, 0.72, 1.0, 0.05),
			immutableCommand(RIGHT_HIP_FLEXOR, 0.72, 0.7, 0.05),
			immutableCommand(LEFT_KNEE_FLEXOR, 0.26, 1.0, 0.08),
			immutableCommand(RIGHT_KNEE_FLEXOR, 0.26, 1.0, 0.08),
			immutableCommand(LEFT_ANKLE_PLANTARFLEXOR, 0.53, 0.2, 0.02),
			immutableCommand(RIGHT_ANKLE_PLANTARFLEXOR, 0.53, 0.2, 0.02),
			immutableCommand(LEFT_ANKLE_INVERTER, 0.15, 1.0, 0.02),
			immutableCommand(RIGHT_ANKLE_INVERTER, 0.15, 1.0, 0.02)
		);
	}
	
	private static final long serialVersionUID = 1L;
}
