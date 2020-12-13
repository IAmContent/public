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
package com.iamcontent.core;

import static java.lang.System.currentTimeMillis;

import java.util.function.Supplier;

/**
 * Helper functions for Thread usage.
 * @author Greg Elderfield
 */
public class ThreadUtils {

	/**
	 * Invokes Thread.sleep(long), catching any {@link InterruptedException} that is thrown.
	 * @param millis The length of time to sleep in milliseconds.
	 * @return true if Thread.sleep(long) terminated normally, false if an {@link InterruptedException} was thrown.
	 */
	public static boolean sleepQuietly(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Polls periodically to see whether a given condition ever becomes true.
	 * @param condition The condition to poll for.
	 * @param waitPeriodInMillis The total time to poll for, in milliseconds.
	 * @param pollPeriodInMillis The time between polls, in milliseconds.
	 * @return true if the condition ever returned true, false otherwise.
	 */
	public static boolean sleepUntil(Supplier<Boolean> condition, long waitPeriodInMillis, long pollPeriodInMillis) {
		final long finishTime = currentTimeMillis() + waitPeriodInMillis;
		while (finishTime > currentTimeMillis()) {
			if (condition.get())
				return true;
			ThreadUtils.sleepQuietly(pollPeriodInMillis);
		}
		return condition.get();
	}
	
}
