/**
  IAmContent Public Libraries.
  Copyright (C) 2015 Greg Elderfield
  @author Greg Elderfield, iamcontent@jarchitect.co.uk
 
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
package com.iamcontent.device.servo.calibrate;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A {@link ServoSourceCalibrator} that allows a default {@link ServoCalibrator} to be set but over-ridden on a per-channel basis.
 * @author Greg Elderfield
 */
public class DefaultingServoSourceCalibrator implements ServoSourceCalibrator {

	private final ServoCalibrator defaultCalibrator;
	private final Map<Integer, ServoCalibrator> perServoCalibrators;
	
	public DefaultingServoSourceCalibrator(ServoCalibrator defaultCalibrator, Map<Integer, ServoCalibrator> perServoCalibrators) {
		checkNotNull(defaultCalibrator, "The default calibrator cannot be null.");
		this.defaultCalibrator = defaultCalibrator;
		this.perServoCalibrators = perServoCalibrators;
	}
	public DefaultingServoSourceCalibrator(ServoCalibrator defaultCalibrator, ServoCalibrator... perServoCalibrators) {
		this(defaultCalibrator, asMap(perServoCalibrators));
	}
	
	@Override
	public ServoCalibrator getServoCalibrator(int channel) {
		final ServoCalibrator c = perServoCalibrators.get(channel);
		return c!=null ? c : defaultCalibrator;
	}

	private static Map<Integer, ServoCalibrator> asMap(ServoCalibrator[] calibrators) {
		final Map<Integer, ServoCalibrator> map = new HashMap<Integer, ServoCalibrator>(calibrators.length);
		for (int i=0; i<calibrators.length; i++)
			map.put(i, calibrators[i]);
		return Collections.unmodifiableMap(map);
	}
}
