package com.iamcontent.io.util;

import static com.google.common.io.Closeables.closeQuietly;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An {@link Iterable} {@link Iterator} that iterates over the lines read from a {@link BufferedReader}.
 * @author Greg Elderfield
 */
public class BufferedReaderIterator implements Iterator<String>, Iterable<String>, Closeable {

	final BufferedReader reader;
	
	private String nextLine;
	
	public BufferedReaderIterator(BufferedReader reader) {
		this.reader = reader;
		advance();
	}
	
	public static BufferedReaderIterator bufferedReaderIterator(BufferedReader reader) {
		return new BufferedReaderIterator(reader);
	}
	
	@Override
	public boolean hasNext() {
		return nextLine != null;
	}

	@Override
	public String next() {
		if (!hasNext())
			throw new NoSuchElementException();
		final String result = nextLine;
		advance();
		return result;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Cannot remove from a BufferedReader.");
	}

	@Override
	public Iterator<String> iterator() {
		return this;
	}

	private void advance() {
		readNextLine();
		closeIfEndReached();
	}

	private void readNextLine() {
		try {
			nextLine = reader.readLine();
		} catch (IOException e) {
			close();
			throw new RuntimeException(e);
		}
	}

	private void closeIfEndReached() {
		if (!hasNext())
			close();
	}

	@Override
	public void close() {
		nextLine = null;
		closeQuietly(reader);
	}
}
