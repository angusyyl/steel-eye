package com.steeleye.codetest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collection;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;

@RunWith(JUnitParamsRunner.class)
public class CONCATTest {
	String firstname;

	String surname;

	String expected;

	JSONObject userModel = new JSONObject();
	
	public static Collection<Object[]> parametersForTestCONCATRemoveTitles() {
		return Arrays.asList(
				new Object[][] { 
					{ "gov John", "John" },
					{ "sir Ludwig", "Ludwig" },
					{ "ph.d Victor", "Victor" },
					{ "phed Victor", "phed Victor" },
					{ "madam(e) Eli", "Eli" },
					{ "FR Willeke", "Willeke" },
					{ "DR Jon Ian", "Jon Ian" },
					{ "dame Amy-Ally", "Amy-Ally" },
					{ "rr dame Amy-Ally", "rr dame Amy-Ally" },
					{ "DAme dame Amy-Ally", "dame Amy-Ally" },
					}
				);
	}
	
	public static Collection<Object[]> parametersForTestCONCATRemovePrefixes() {
		return Arrays.asList(
				new Object[][] { 
					{ "O'Brian", "O'Brian" },
					{ "mic O'Brian", "mic O'Brian" },
					{ "van de MacChrystal", "van de MacChrystal" },
					{ "van de O'Neal", "van de O'Neal" },
					{ "van de McDonald", "van de McDonald" },
					{ "VAN DE McDonald", "VAN DE McDonald" },
					{ "VAN DE     McDonald", "VAN DE     McDonald" },
					{ "VAN DEMcDonald", "DEMcDonald" },
					{ "de l’ ERRDonald", "ERRDonald" },
					{ "de   ERRDonald", "ERRDonald" },
					{ "ó ERRDonald", "ERRDonald" },
					{ "rrrrr Rohe", "rrrrr Rohe" },
					{ "aus der Rohe", "Rohe" },
					{ "da da Rohe", "da Rohe" },
					{ "Van De Rohe", "Rohe" },
					}
				);
	}
	
	public static Collection<Object[]> parametersForTestCONCATTransliteration() {
		return Arrays.asList(
				new Object[][] { 
					{ "O'Brian", "OBrian" },
					{ "Rohe", "Rohe" },
					{ "Ødegård", "OdegArd" },
					{ "L-Bruijn", "LBruijn" },
					{ "D ewitt", "Dewitt" },
					{ "Garção de Magalhães", "GarCAodeMagalhAes" },
					{ "?Santos", "Santos" },
					{ "?Sa$#ntos", "Santos" }
					}
				);
	}

	public static Collection<Object[]> parametersForTestCONCATGenCONCAT() {
		return Arrays.asList(
				new Object[][] { 
					{ "John", "O'Brian", "JOHN#OBRIA" },
					{ "Ludwig", "Van der Rohe", "LUDWIROHE#" },
					{ "Victor", "Vandenberg", "VICTOVANDE" },
					{ "Eli", "Ødegård", "ELI##ODEGA" },
					{ "Willeke", "de Bruijn", "WILLEBRUIJ" },
					{ "Jon Ian", "Dewitt", "JON##DEWIT" },
					{ "Amy-Ally", "Garção de Magalhães", "AMYALGARCA" },
					{ "Giovani", "dos Santos", "GIOVASANTO" },
					{ "Günter", "Voẞ", "GUNTEVOS##" }
					}
				);
	}

	public static Collection<Object[]> parametersForNullCheck() {
		return Arrays.asList(
				new Object[][] { 
					{ "Victor", null },
					{ null, null },
					{ null, "Vandenberg" }
					}
				);
	}
	
	@Test
	@Parameters(method = "parametersForTestCONCATRemoveTitles")
    @TestCaseName("firstname: {0}, expected: {1}")
	public void testCONCATRemoveTitles(String firstname, String expected) {
		assertEquals(expected, CONCAT.removeTitle(firstname));
	}
	
	@Test
	@Parameters(method = "parametersForTestCONCATRemovePrefixes")
    @TestCaseName("surname: {0}, expected: {1}")
	public void testCONCATRemovePrefixes(String surname, String expected) {
		assertEquals(expected, CONCAT.removePrefix(surname));
	}

	@Test
	@Parameters(method = "parametersForTestCONCATTransliteration")
    @TestCaseName("name: {0}, expected: {1}")
	public void testCONCATTransliteration(String name, String expected) {
		assertEquals(expected, CONCAT.transliteration(name));
	}

	@Test
	@Parameters(method = "parametersForTestCONCATGenCONCAT")
    @TestCaseName("firstname: {0}, surname: {1}, expected: {2}")
	public void testCONCATGenCONCAT(String firstname, String surname, String expected) {
		userModel.put("firstname", firstname);
		userModel.put("surname", surname);
		assertEquals(expected, CONCAT.genCONCAT(userModel));
	}

	@Test
	@Parameters(method = "parametersForNullCheck")
    @TestCaseName("firstname: {0}, surname: {1}")
	public void testCONCATCONNullCheck(String firstname, String surname) {
		userModel.put("firstname", firstname);
		userModel.put("surname", surname);
		assertNull(CONCAT.genCONCAT(userModel));
	}
}
