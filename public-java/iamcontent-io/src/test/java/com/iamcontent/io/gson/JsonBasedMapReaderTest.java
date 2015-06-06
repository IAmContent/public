package com.iamcontent.io.gson;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

public class JsonBasedMapReaderTest {
	
	private final static String FOLDER = "json/";

	private enum TestEnum {ONE, TWO, SEVEN};
	
	@Test
	public void testStringToIntegerMap() {
		final Map<String, Integer> actual = JsonBasedMapReader.map(FOLDER, "string-to-integer-map", String.class, Integer.class);
		assertEquals(3, actual.size());
		assertEquals(1, actual.get("ONE").intValue());
		assertEquals(2, actual.get("TWO").intValue());
		assertEquals(7, actual.get("SEVEN").intValue());
	}
	
	@Test
	public void testEnumToIntegerMap() {
		final Map<TestEnum, Integer> actual = JsonBasedMapReader.map(FOLDER, "string-to-integer-map", TestEnum.class, Integer.class);
		assertEquals(3, actual.size());
		assertEquals(1, actual.get(TestEnum.ONE).intValue());
		assertEquals(2, actual.get(TestEnum.TWO).intValue());
		assertEquals(7, actual.get(TestEnum.SEVEN).intValue());
	}

	@Test
	public void testIntegerToStringMap() {
		final Map<Integer, String> actual = JsonBasedMapReader.map(FOLDER, "integer-to-string-map", Integer.class, String.class);
		assertEquals(3, actual.size());
		assertEquals("ONE", actual.get(1));
		assertEquals("TWO", actual.get(2));
		assertEquals("SEVEN", actual.get(7));
	}

	@Test
	public void testIntegerToEnumMap() {
		final Map<Integer, TestEnum> actual = JsonBasedMapReader.map(FOLDER, "integer-to-string-map", Integer.class, TestEnum.class);
		assertEquals(3, actual.size());
		assertEquals(TestEnum.ONE, actual.get(1));
		assertEquals(TestEnum.TWO, actual.get(2));
		assertEquals(TestEnum.SEVEN, actual.get(7));
	}

}
