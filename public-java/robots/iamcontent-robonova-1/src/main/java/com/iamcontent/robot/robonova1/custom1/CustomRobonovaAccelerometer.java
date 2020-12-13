/**
  IAmContent Public Libraries.
  Copyright (C) 2015-2021 Greg Elderfield
  @author Greg Elderfield, support@jarchitect.co.uk
 
  This program is free software; you can redistribute it and/or modify it under the terms of the
  GNU General Public License as published by the Free Software Foundation; either version 2 of 
  the License, or (at your option) any later version.

  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
  See the GNU General Public License for more details.

  You should have received a copy of the GNU General Public License along with this program;
  if not, write to the Free Software Foundation, Inc., 
  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package com.iamcontent.robot.robonova1.custom1;

import static com.iamcontent.core.math.InterRangeDoubleConverter.converterFromNormalTo;
import static com.iamcontent.device.controller.pololu.maestro.DefaultPololuServoConfig.rawAnalogIOSource;

import java.util.function.Function;

import com.iamcontent.core.geom.Geometry.ThreeDimension;
import com.iamcontent.device.io.analog.AnalogIOSource;
import com.iamcontent.device.io.analog.AnalogIOSourceCalibration;
import com.iamcontent.device.io.analog.impl.ImmutableAnalogIOCalibration;
import com.iamcontent.robot.robonova1.Robonova;

/**
 * Provides a custom accelerometer for my own {@link Robonova}.
 * @author Greg Elderfield
 */
public class CustomRobonovaAccelerometer {

	public static AnalogIOSource<ThreeDimension> accelerometer() {
		return rawAnalogIOSource()
				.calibrated(normalization())
				.remapped(remapping())
				.calibrated(tuning());
	}
	
	private static AnalogIOSourceCalibration<Integer> normalization() {
		return AnalogIOSourceCalibration.analogIOSourceCalibration(new ImmutableAnalogIOCalibration(converterFromNormalTo(0.0, 255.0))); // FIXME: Create default normalization for Poulu IO
	}
	
	private static Function<ThreeDimension, Integer> remapping() {
		return new Function<ThreeDimension, Integer>() {
			@Override
			public Integer apply(ThreeDimension dimension) {
				switch (dimension) {
				case X:
					return 0;
				case Y:
					return 1;
				case Z:
					return 2;
				default:
					throw new IllegalStateException("Dimension not recognized: " + dimension);
				}
			}
		};
	}
	
	private static AnalogIOSourceCalibration<ThreeDimension> tuning() {
		return AnalogIOSourceCalibration.<ThreeDimension>analogIOSourceCalibration(); // FIXME: Tune the accelerometer
	}
}
