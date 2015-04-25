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
