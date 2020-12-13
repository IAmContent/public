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
package com.iamcontent.io.cli;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class LicenseWriterTest {

	private static final String PRODUCT_NAME = "PROD";
	private static final String YEARS = "2099, 2100";
	private static final String COPYRIGHT_HOLDER = "M. Mouse";
	
	private StringBuilder out;
	private LicenseWriter licenseWriter;
	
	@Before
	public void setUp() throws Exception {
		out = new StringBuilder();
		licenseWriter = new LicenseWriter(out, PRODUCT_NAME, YEARS, COPYRIGHT_HOLDER);
	}

	@Test
	public void testPrintFile_nonExistentFile() {
		licenseWriter.printWithHeader("/NON_EXISTENT_FILE");
		verifyOutput(LicenseWriter.MINIMAL_TERMS);
	}
	
	@Test
	public void testPrintInstructions() {
		licenseWriter.printInteractiveInstructions();
		verifyOutput("This software comes with ABSOLUTELY NO WARRANTY; for details type 'warranty'.",
				     "type 'license' for details.");
	}
	
	@Test
	public void testPrintNonInteractiveInstructions() {
		licenseWriter.printNonInteractiveInstructions();
		verifyHeader();
		verifyOutput(LicenseWriter.MINIMAL_TERMS);
	}
	
	@Test
	public void testPrintTermsAndConditions() {
		licenseWriter.printTermsAndConditions();
		verifyOutput("GNU GENERAL PUBLIC LICENSE",
			         "Version 2, June 1991",
				     "0. This License applies to any program or other",
				     "12. IN NO EVENT UNLESS REQUIRED BY APPLICABLE LAW OR AGREED",
				     "END OF TERMS AND CONDITIONS");
	}
	
	@Test
	public void testPrintWarranty() {
		licenseWriter.printWarranty();
		verifyOutput("GNU GENERAL PUBLIC LICENSE",
			         "NO WARRANTY",
				     "Version 2, June 1991",
				     "12. IN NO EVENT UNLESS REQUIRED BY APPLICABLE LAW OR AGREED",
				     "END OF TERMS AND CONDITIONS");
		assertTrue(out.indexOf("0. This License applies to any program or other") < 0);
	}

	private void verifyOutput(String... expected) {
		verifyHeader();
		for (String s : expected)
			assertStringWritten(s);
		verifyFooter();
	}

	private void verifyHeader() {
		assertStringWritten("PROD, Copyright (C) 2099, 2100 M. Mouse.\n");
	}
	
	private void verifyFooter() {
		assertStringWritten(LicenseWriter.FOOTER);
	}
	
	private void assertStringWritten(String s) {
		assertTrue("Should contain '" + s + "'.", out.indexOf(s) >= 0);
	}
}
