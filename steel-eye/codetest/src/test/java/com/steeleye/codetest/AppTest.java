package com.steeleye.codetest;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class AppTest {
	String firstname;

	String surname;

	String expected;

	JSONObject userModel = new JSONObject();
	
	@Parameterized.Parameters
	public static Collection<Object[]> primeNumbers() {
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
					{ null, "Vandenberg", null }
					}
				);
	}

	public AppTest(String firstname, String surname, String expected) {
		this.firstname = firstname;
		this.surname = surname;
		
		userModel.put("firstname", firstname);
		userModel.put("surname", surname);
		
		this.expected = expected;
	}

	@Test
	public void testAppEx1() {
		assertEquals(expected, App.genCONCAT(userModel));
	}
}
