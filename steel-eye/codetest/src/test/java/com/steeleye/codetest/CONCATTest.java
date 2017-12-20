package com.steeleye.codetest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import junitparams.naming.TestCaseName;

/***
 * 
 * @author Angus
 *
 */
@RunWith(JUnitParamsRunner.class)
public class CONCATTest {
	String firstname;

	String surname;

	String expected;

	JsonObject userModel;
	
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
	
	public static Collection<Object[]> parametersForTestCONCATRemoveOtherNameInFirstname() {
		return Arrays.asList(
				new Object[][] { 
					{ "Jon", "Jon" },
					{ "Jon ", "Jon" },
					{ "Jon lan", "Jon" },
					{ "Ludwig re fd c", "Ludwig" },
					{ "Eli zll !", "Eli" },
					{ "ó ERRDonald", "ó" },
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

	public static Collection<Object[]> parametersForNoMappingCheck() {
		HashMap<String, String> userObj1 = new HashMap<String, String>();
		userObj1.put("firstname", "only firstname");

		HashMap<String, String> userObj2 = new HashMap<String, String>();
		userObj2.put("surname", "only surname");

		HashMap<String, String> userObj3 = new HashMap<String, String>();
		userObj3.put("tel", "+44 (0) 1231231234");
		userObj3.put("address", "London");

		HashMap<String, String> userObj4 = new HashMap<String, String>();
		userObj4.put("surname", "surname and other information");
		userObj4.put("tel", "+44 (0) 998877665");
		userObj4.put("address", "London");
		
		return Arrays.asList(
				new Object[][] { 
					{ userObj1 },
					{ userObj2 },
					{ userObj3 },
					{ userObj4 }
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
	@Parameters(method = "parametersForTestCONCATRemoveOtherNameInFirstname")
    @TestCaseName("firstname: {0}, expected: {1}")
	public void testCONCATRemoveOtherNameInFirstname(String firstname, String expected) {
		assertEquals(expected, CONCAT.removeOtherNameInFirstname(firstname));	}
	
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
		userModel = Json.createObjectBuilder().add("firstname", firstname).add("surname", surname).build();
		assertEquals(expected, CONCAT.genCONCAT(userModel));
	}

	@Test
	@Parameters(method = "parametersForNullCheck")
    @TestCaseName("firstname: {0}, surname: {1}")
	public void testCONCATCONNullCheck(String firstname, String surname) {
		JsonObjectBuilder job = Json.createObjectBuilder();
		if (firstname == null) {
			job = job.add("firstname", JsonObject.NULL);
		} else {
			job = job.add("firstname", firstname);
		}
		if (surname == null) {
			job = job.add("surname", JsonObject.NULL);
		} else {
			job = job.add("surname", surname);
		}
		userModel = job.build();
		assertNull(CONCAT.genCONCAT(userModel));
	}
	
	@Test
	@Parameters(method = "parametersForNoMappingCheck")
    @TestCaseName("userObj: {0}")
	public void testCONCATCONNoMappingCheck(HashMap<String, String> map) {
		JsonObjectBuilder job = Json.createObjectBuilder();
		for (String key : map.keySet()) {
			job.add(key, map.get(key));
		}
		userModel = job.build();
		assertNull(CONCAT.genCONCAT(userModel));
	}
	
	@Test
	public void testCONCATCONNormalMappingCheck() {
		userModel = Json.createObjectBuilder().add("firstname", "only firstname").add("surname", "only surname")
									.add("tel", "+44 (0) 1231231234").add("address", "London").build();
		assertEquals("ONLY#ONLYS", CONCAT.genCONCAT(userModel));
	}

}
