package com.iamcontent.robot.robonova1.custom1.servo;

import static com.iamcontent.core.math.InterRangeDoubleConverter.rangeFromNormalTo;

import com.iamcontent.core.math.InterRangeDoubleConverter;
import com.iamcontent.device.controller.pololu.maestro.PololuMaestroServoController;
import com.iamcontent.device.servo.calibrate.DefaultingServoSourceCalibrator;
import com.iamcontent.device.servo.calibrate.ImmutableServoCalibrator;
import com.iamcontent.device.servo.calibrate.ServoCalibrator;

/**
 * A {@link ServoCalibrator} for the Hitec HSR4898HB servos when controlled by a {@link PololuMaestroServoController}.
 * @author Greg Elderfield
 */
public class ServoNormalization extends DefaultingServoSourceCalibrator<Integer> {

	public ServoNormalization() {
		super(defaultCalibrationForEachServo());
	}

	private static ServoCalibrator defaultCalibrationForEachServo() {
		final InterRangeDoubleConverter valueNormalization = rangeFromNormalTo(2432.0, 9600.0);
		final InterRangeDoubleConverter speedNormalization = rangeFromNormalTo(0.0, 200.0);
		final InterRangeDoubleConverter accelerationNormalization = rangeFromNormalTo(0.0, 255.0);
		
		return new ImmutableServoCalibrator(valueNormalization, speedNormalization, accelerationNormalization);
	}
}
