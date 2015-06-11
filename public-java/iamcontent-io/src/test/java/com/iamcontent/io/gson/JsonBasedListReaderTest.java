package com.iamcontent.io.gson;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class JsonBasedListReaderTest {
	
	private enum TestEnum {ONE, TWO, SEVEN};
	public static class TestClass {
		public TestEnum testEnum;
		public String testString;
	}
	
	@Test
	public void testStringToIntegerMap() {
		final List<TestClass> actual = JsonBasedListReader.list("json/test-list", TestClass.class);
		assertEquals(3, actual.size());
		checkEntry(actual.get(0), TestEnum.ONE, "First");
		checkEntry(actual.get(1), TestEnum.SEVEN, "Seventh");
		checkEntry(actual.get(2), TestEnum.TWO, "Second");
	}
	
	private void checkEntry(TestClass actual, TestEnum testEnum, String testString) {
		assertEquals(testEnum, actual.testEnum);
		assertEquals(testString, actual.testString);
	}
}
