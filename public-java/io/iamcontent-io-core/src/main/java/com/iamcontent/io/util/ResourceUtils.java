package com.iamcontent.io.util;

import static com.iamcontent.io.util.IOUtils.appendQuietly;
import static com.iamcontent.io.util.IOUtils.lineStreamOf;

import java.io.InputStream;
import java.util.stream.Stream;

import com.iamcontent.io.IORuntimeException;

/**
 * Utilities for reading resources.
 * @author Greg Elderfield
 */
public class ResourceUtils {

	public static void appendResource(String fileName, Appendable onto) {
		resourceAsLineStream(fileName).forEach(l -> appendQuietly(onto, l));
	}

	public static Stream<String> resourceAsLineStream(String fileName) {
		final InputStream is = ResourceUtils.class.getClassLoader().getResourceAsStream(fileName);
		if (is==null)
			throw new IORuntimeException("Could not find resource: " + fileName);
		return lineStreamOf(is);
	}
}
