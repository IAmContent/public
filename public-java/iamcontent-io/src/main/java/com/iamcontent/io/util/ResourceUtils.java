package com.iamcontent.io.util;

import static com.google.common.io.CharStreams.copy;
import static com.iamcontent.io.util.Readers.inputStreamReader;

import java.io.IOException;
import java.io.InputStream;

import com.iamcontent.io.IORuntimeException;

/**
 * Utilities for reading resources.
 * @author Greg Elderfield
 */
public class ResourceUtils {

	public static void appendResource(String fileName, Appendable onto) {
		try(final InputStream in = getStreamOrThrow(fileName)) {
			copyInputStream(in, onto);
		} catch (IOException e) {
			throw new IORuntimeException(e);
		};
	}

	public static InputStream getStreamOrThrow(String fileName) {
		final InputStream result = ResourceUtils.class.getClassLoader().getResourceAsStream(fileName);
		if (result==null)
			throw new IORuntimeException("Could not find resource: " + fileName);
		return result;
	}

	private static void copyInputStream(InputStream in, Appendable onto) throws IOException {
		copy(inputStreamReader(in), onto);
	}


}
