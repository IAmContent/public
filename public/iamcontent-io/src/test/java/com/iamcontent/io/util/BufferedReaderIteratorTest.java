package com.iamcontent.io.util;
import static com.google.common.collect.Iterables.elementsEqual;
import static com.iamcontent.io.util.BufferedReaderIterator.bufferedReaderIterator;
import static com.iamcontent.io.util.Readers.bufferedReader;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

public class BufferedReaderIteratorTest {

	@Test
	public void testSeveralLines() {
		checkLines("One\nTwo\nThree\n", "One", "Two", "Three", null);
	}

	@Test
	public void testEmptyInput() {
		checkLines("", new String[]{null});
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testRemove() {
		iteratorFor("ABC").remove();
	}

	@Test
	public void testEmptyStream() throws Exception {
		final BufferedReader r = mock(BufferedReader.class);
		when(r.readLine()).thenReturn(null);
		final BufferedReaderIterator iterator = bufferedReaderIterator(r);
		assertTrue(iterator.hasNext());
		assertNull(iterator.next());
		assertFalse(iterator.hasNext());
	}

	@Test
	public void testEndOfStream() throws Exception {
		final BufferedReader r = mock(BufferedReader.class);
		when(r.readLine()).thenReturn("One").thenReturn("Two").thenReturn(null);
		final BufferedReaderIterator iterator = bufferedReaderIterator(r);
		assertTrue(iterator.hasNext());
		assertEquals("One", iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals("Two", iterator.next());
		assertTrue(iterator.hasNext());
		assertNull(iterator.next());
		assertFalse(iterator.hasNext());
	}

	@Test(expected=NoSuchElementException.class)
	public void testReadPastEndOfStream() throws Exception {
		final BufferedReader r = mock(BufferedReader.class);
		when(r.readLine()).thenReturn(null);
		final BufferedReaderIterator iterator = bufferedReaderIterator(r);
		assertNull(iterator.next());
		iterator.next();
	}

	private void checkLines(String input, String... expected) {
		final List<String> expectedOutput = asList(expected);
		assertTrue(elementsEqual(expectedOutput, iteratorFor(input)));
	}

	private static BufferedReaderIterator iteratorFor(String s) {
		return bufferedReaderIterator(bufferedReaderFor(s));
	}

	private static BufferedReader bufferedReaderFor(String s) {
		return bufferedReader(new StringReader(s));
	}
}
