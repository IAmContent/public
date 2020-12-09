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
package com.iamcontent.io.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.iamcontent.io.IORuntimeException;

public class ResourceUtilsTest {

	private static final String TEST_RESOURCE = "test-resource.txt";
	
	@Test(expected=IORuntimeException.class)
	public void testGetStreamOrThrow_nonExistentResource() throws FileNotFoundException {
		ResourceUtils.getStreamOrThrow("NON_EXISTENT_FILE");
	}

	@Test
	public void testGetStreamOrThrow() throws Exception {
		try(InputStream in = ResourceUtils.getStreamOrThrow(TEST_RESOURCE)) {
			assertNotNull(in);
		}
	}
	
	@Test
	public void testAppendResource() throws IOException {
		final StringBuilder out = new StringBuilder();
		ResourceUtils.appendResource(TEST_RESOURCE, out);
		assertTrue(out.indexOf("Test resource line 1.") >= 0);
		assertTrue(out.indexOf("Test resource line 2.") >= 0);
	}
}
