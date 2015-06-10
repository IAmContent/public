/**
  IAmContent Public Libraries.
  Copyright (C) 2015 Greg Elderfield
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
package com.iamcontent.io.util;

import static com.iamcontent.io.util.Readers.inputStreamReader;
import static com.iamcontent.io.util.ResourceUtils.getStreamOrThrow;

import java.io.IOException;
import java.io.Reader;

import com.iamcontent.io.IORuntimeException;

/**
 * An abstract class for reading objects from file resources.
 * @author Greg Elderfield
 */
public abstract class AbstractResourceReader<T> {
	private final String calibratorName;

	public AbstractResourceReader(String calibratorName) {
		this.calibratorName = calibratorName;
	}

	public AbstractResourceReader(String calibratorName, String extension) {
		this(calibratorName + extension);
	}

	public T read() {
		try (final Reader r = reader()) {
			return readFrom(r);
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}

	protected abstract T readFrom(Reader r);

	private Reader reader() {
		return inputStreamReader(getStreamOrThrow(calibratorName));
	}
}
