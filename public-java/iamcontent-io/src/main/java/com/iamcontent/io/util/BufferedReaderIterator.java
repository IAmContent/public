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
package com.iamcontent.io.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An {@link Iterable} {@link Iterator} that iterates over the lines read from a {@link BufferedReader}.
 * Note that this Iterator will return null as the last element if the EOF is reached on the reader.
 * @author Greg Elderfield
 */
public class BufferedReaderIterator implements Iterator<String>, Iterable<String> {

	final BufferedReader reader;
	
	private boolean eofReached = false;
	
	public BufferedReaderIterator(BufferedReader reader) {
		this.reader = reader;
	}
	
	public static BufferedReaderIterator bufferedReaderIterator(BufferedReader reader) {
		return new BufferedReaderIterator(reader);
	}
	
	@Override
	public boolean hasNext() {
		return !eofReached;
	}

	@Override
	public String next() {
		if (!hasNext())
			throw new NoSuchElementException();
		return readLineAndCheckForEof();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Cannot remove from a BufferedReader.");
	}

	@Override
	public Iterator<String> iterator() {
		return this;
	}

	private String readLineAndCheckForEof() {
		final String result = readLineQuietly();
		eofReached |= result==null;
		return result;
	}

	private String readLineQuietly() {
		try {
			return reader.readLine();
		} catch (IOException e) {
			eofReached = true;
			throw new RuntimeException(e);
		}
	}
}
