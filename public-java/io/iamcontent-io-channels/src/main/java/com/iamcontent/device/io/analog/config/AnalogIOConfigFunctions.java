/**
  IAmContent Public Libraries.
  Copyright (C) 2015-2021 Greg Elderfield
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
package com.iamcontent.device.io.analog.config;

import com.google.common.base.Function;
import com.iamcontent.device.io.analog.AnalogIO;
import com.iamcontent.device.io.analog.calibrate.AnalogIOSourceCalibrator;

/**
 * The functions and calibrators required to support a 'standard' configuration of {@link AnalogIO}s, where each raw {@link AnalogIO}
 * is calibrated to a normal range (0..1), then re-channeled and then calibrated once more (tuned).
 * 
 * @author Greg Elderfield
 * 
 * @param <R> The type used to identify the channel of a raw {@link AnalogIO}. 
 * @param <C> The type used to identify the channel of a rechanneled {@link AnalogIO}. 
 */
public interface AnalogIOConfigFunctions<R,C> {
	/**
	 * @return A Calibrator that calibrates the raw {@link AnalogIO}s to a normal range (0..1).
	 */
	AnalogIOSourceCalibrator<R> normalizingCalibrator();

	/**
	 * @return A function that translates from {@link C} values to the corresponding {@link R values.
	 */
	Function<C, R> channelTranslation();

	/**
	 * @return A Calibrator that tunes the rechanneled, normalized {@link AnalogIO}s to their desired range.
	 */
	AnalogIOSourceCalibrator<C> tuningCalibrator();
}
