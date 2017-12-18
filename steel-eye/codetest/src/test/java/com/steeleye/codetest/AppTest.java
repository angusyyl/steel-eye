package com.steeleye.codetest;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;

@RunWith(JUnitParamsRunner.class)
public class AppTest {
	String firstname;

	String surname;

	String expected;

	JSONObject userModel = new JSONObject();
	
	public static Collection<Object[]> parametersForTestAppRemoveTitles() {
		return Arrays.asList(
				new Object[][] { 
					{ "gov John", "John" },
					{ "sir Ludwig", "Ludwig" },
					{ "ph.d Victor", "Victor" },
					{ "madam(e) Eli", "Eli" },
					{ "FR Willeke", "Willeke" },
					{ "DR Jon Ian", "Jon Ian" },
					{ "dame Amy-Ally", "Amy-Ally" },
					{ null, null }
					}
				);
	}
	
	public static Collection<Object[]> parametersForTestAppRemovePrefixes() {
		return Arrays.asList(
				new Object[][] { 
					{ "O'Brian", "O'Brian" },
					{ "mic O'Brian", "mic O'Brian" },
					{ "van de MacChrystal", "van de O'Brian" },
					{ "van de O'Neal", "van de O'Neal" },
					{ "van de McDonald", "van de McDonald" },
					{ "VAN DE McDonald", "VAN DE McDonald" },
					{ "Van De McDonald", "Van De McDonald" },
					{ "rrrrr Rohe", "rrrrr Rohe" },
					{ "aus der Rohe", "Rohe" },
					{ "da da Rohe", "da Rohe" },
					{ "Van De Rohe", "Rohe" },
					{ null, null }
					}
				);
	}
	
	public static Collection<Object[]> parametersForTestAppTransliteration() {
		return Arrays.asList(
				new Object[][] { 
					{ "O'Brian", "OBrian" },
					{ "Rohe", "Rohe" },
					{ "Ødegård", "OdegArd" },
					{ "L-Bruijn", "LBruijn" },
					{ "D ewitt", "Dewitt" },
					{ "Garção de Magalhães", "GarCAo de MagalhAes" },
					{ "?Santos", "Santos" },
					{ "?Sa$#ntos", "Santos" },
					{ null, null }
					}
				);
	}

	public static Collection<Object[]> parametersForTestAppCONCAT() {
		return Arrays.asList(
				new Object[][] { 
					{ "John", "O'Brian", "JOHN#OBRIA" },
					{ "Ludwig", "Van der Rohe", "LUDWIROHE##" },
					{ "Victor", "Vandenberg", "VICTOVANDE" },
					{ "Eli", "Ødegård", "ELI##ODEGA" },
					{ "Willeke", "de Bruijn", "WILLEBRUIJ" },
					{ "Jon Ian", "Dewitt", "JON##DEWIT" },
					{ "Amy-Ally", "Garção de Magalhães", "AMYALGARCA" },
					{ "Giovani", "dos Santos", "GIOVASANTO" },
					{ "Günter", "Voẞ", "GUNTEVOS##" },
					{ "Victor", null, null },
					{ null, null, null },
					{ null, "Vandenberg", null }
					}
				);
	}

	@Test
	@Parameters(method = "parametersForTestAppRemoveTitles")
    @TestCaseName("firstname: {0}, expected: {1}")
	public void testAppRemoveTitles(String firstname, String expected) {
		assertEquals(expected, App.removeTitle(firstname));
	}
	
	@Test
	@Parameters(method = "parametersForTestAppRemovePrefixes")
    @TestCaseName("surname: {0}, expected: {1}")
	public void testAppRemovePrefixes(String surname, String expected) {
		assertEquals(expected, App.removePrefix(surname));
	}

	@Test
	@Parameters(method = "parametersForTestAppTransliteration")
    @TestCaseName("name: {0}, expected: {1}")
	public void testAppTransliteration(String name, String expected) {
		assertEquals(expected, App.transliteration(name));
	}
	
	@Test
	@Parameters(method = "parametersForTestAppCONCAT")
    @TestCaseName("firstname: {0}, surname: {1}, expected: {2}")
	public void testAppCONCAT(String firstname, String surname, String expected) {
		userModel.put("firstname", firstname);
		userModel.put("surname", surname);
		assertEquals(expected, App.genCONCAT(userModel));
	}
}
