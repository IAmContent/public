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
