package com.iamcontent.io.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.InputStream;
import java.io.Reader;

import org.junit.Before;
import org.junit.Test;

public class ReadersTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testBufferedReader_InputStream() throws Exception {
		final InputStream is = mock(InputStream.class);
		Readers.bufferedReader(is).close();
		verify(is).close();
	}

	@Test
	public void testBufferedReader_Reader() throws Exception {
		final Reader r = mock(Reader.class);
		Readers.bufferedReader(r).close();
		verify(r).close();
	}

	@Test
	public void testInputStreamReader_InputStream() throws Exception {
		final InputStream is = mock(InputStream.class);
		Readers.inputStreamReader(is).close();
		verify(is).close();
	}
}
