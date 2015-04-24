package com.iamcontent.io.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Utility methods for {@link Reader}s.
 * @author Greg Elderfield
 */
public class Readers {
	public static BufferedReader bufferedReader(InputStream is) {
		return bufferedReader(inputStreamReader(is));
	}

	public static BufferedReader bufferedReader(Reader r) {
		return new BufferedReader(r);
	}

	public static InputStreamReader inputStreamReader(InputStream is) {
		return new InputStreamReader(is);
	}
}
