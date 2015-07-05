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
package com.iamcontent.device.io.analog;

import static com.iamcontent.device.io.analog.AnalogIOSources.calibratedAnalogIOSource;
import static com.iamcontent.device.io.analog.AnalogIOSources.rawAnalogIOSource;
import static com.iamcontent.device.io.analog.AnalogIOSources.reChanneledAnalogIOSource;

import com.google.common.base.Function;

/**
 * A 'standard' configuration of {@link AnalogIO}s, where each raw {@link AnalogIO} is calibrated to a normal range (0..1), then
 * re-channeled and then calibrated once more (tuned) (or some subset of this configuration).
 * 
 * @author Greg Elderfield
 * 
 * @param <R> The type used to identify the channel of a raw {@link AnalogIO}. 
 * @param <C> The type used to identify the channel of a rechanneled {@link AnalogIO}. 
 */
public class StandardAnalogIOConfiguration<R,C> {

	private final AnalogIOController<R> controller;
	private final AnalogIOSource<R> rawIOs;
	private final AnalogIOSource<R> normalIOs;
	private final AnalogIOSource<C> normalRechanneledIOs;
	private final AnalogIOSource<C> tunedRechanneledIOs;

	public StandardAnalogIOConfiguration(AnalogIOController<R> analogIOController, AnalogIOSourceCalibrator<R> normalizingAnalogIOCalibrator,
			Function<C, R> channelTranslation, AnalogIOSourceCalibrator<C> tuningCalibrator) {
		tunedRechanneledIOs = 
				calibratedAnalogIOSource(
						normalRechanneledIOs = reChanneledAnalogIOSource(
								normalIOs = calibratedAnalogIOSource(
										rawIOs = rawAnalogIOSource(this.controller = analogIOController), 
										normalizingAnalogIOCalibrator), 
								channelTranslation),
						tuningCalibrator);
	}

	public StandardAnalogIOConfiguration(AnalogIOController<R> analogIOController, AnalogIOSourceCalibrator<R> normalizingAnalogIOCalibrator,
			Function<C, R> channelTranslation) {
		normalRechanneledIOs = reChanneledAnalogIOSource(
				normalIOs = calibratedAnalogIOSource(
						rawIOs = rawAnalogIOSource(this.controller = analogIOController), 
						normalizingAnalogIOCalibrator), 
				channelTranslation);
		tunedRechanneledIOs = null;
	}

	public StandardAnalogIOConfiguration(AnalogIOController<R> analogIOController, AnalogIOSourceCalibrator<R> normalizingAnalogIOCalibrator) {
		normalIOs = calibratedAnalogIOSource(
				rawIOs = rawAnalogIOSource(this.controller = analogIOController), 
				normalizingAnalogIOCalibrator);
		normalRechanneledIOs = null;
		tunedRechanneledIOs = null;
	}

	public AnalogIOController<R> getController() {
		return throwIfNull(controller);
	}

	public AnalogIOSource<R> getRawIOs() {
		return throwIfNull(rawIOs);
	}

	public AnalogIOSource<R> getNormalIOs() {
		return throwIfNull(normalIOs);
	}

	public AnalogIOSource<C> getNormalRechanneledIOs() {
		return throwIfNull(normalRechanneledIOs);
	}

	public AnalogIOSource<C> getTunedRechanneledIOs() {
		return throwIfNull(tunedRechanneledIOs);
	}
	
	protected static <X> X throwIfNull(X x) {
		if (x==null)
			throw new UnsupportedOperationException("This field has not been set in this configuration.");
		return x;
	}
}
