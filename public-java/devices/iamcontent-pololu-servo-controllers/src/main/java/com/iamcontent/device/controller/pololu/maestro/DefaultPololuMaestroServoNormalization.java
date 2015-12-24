package com.iamcontent.device.controller.pololu.maestro;

import static com.iamcontent.core.math.InterRangeDoubleConverter.rangeFromNormalTo;

import com.iamcontent.core.math.InterRangeDoubleConverter;
import com.iamcontent.device.servo.calibrate.DefaultingServoSourceCalibrator;
import com.iamcontent.device.servo.calibrate.ImmutableServoCalibrator;
import com.iamcontent.device.servo.calibrate.ServoCalibrator;

/**
 * A {@link DefaultingServoSourceCalibrator} that normalizes Pololu Maestro servos.
 * @author Greg Elderfield
 */
public class DefaultPololuMaestroServoNormalization extends DefaultingServoSourceCalibrator<Integer> {

	public DefaultPololuMaestroServoNormalization() {
		super(defaultCalibrationForEachServo());
	}

	private static ServoCalibrator defaultCalibrationForEachServo() {
		final InterRangeDoubleConverter valueNormalization = rangeFromNormalTo(4000.0, 8000.0);
		final InterRangeDoubleConverter speedNormalization = rangeFromNormalTo(0.0, 200.0);
		final InterRangeDoubleConverter accelerationNormalization = rangeFromNormalTo(0.0, 255.0);
		
		return new ImmutableServoCalibrator(valueNormalization, speedNormalization, accelerationNormalization);
	}
}
