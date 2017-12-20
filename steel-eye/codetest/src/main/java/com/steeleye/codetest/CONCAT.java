package com.steeleye.codetest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.apache.commons.lang3.StringUtils;

/***
 * Requirement Assumptions: A user object is received as a JSON document with
 * any number of arbitrary field A user object may or may not contain all the
 * necessary fields required to generate a CONCAT code. If the user object
 * contains all the fields necessary for the code, the code should be generated
 * and returned. Else a null code may be returned.
 * 
 * Self-defined Assumptions: 1) "firstname" and "surname" in JSON object
 * correspond to first name and surname of an user. 2) Received JSON document
 * named "UserObjects.json" is stored in "resource" working directory
 * 
 * @author Angus
 *
 */
public class CONCAT {
	static final String titlePatterns = "(?i)^(atty|coach|dame|dr|fr|gov|honorable|madam\\(e\\)|maid|master|miss|monsieur|mr|mrs|ms|mx|ofc|ph\\.d|pres|prof|rev|sir)\\s+";
	static final String otherNameInFirstnamePattern = "\\s+.*";
	static final String matchPrefixPatterns = "(?i)^(am|auf dem|auf|aus der|d|da|de l’|de|del|de la|de le|di|do|dos|du|im|la|le|mac|mc|mhac|mhic giolla|mhíc|mic|ni|ní|níc|o|ó|ua|ui|uí|van de|van den|van der|van|vom|von dem|von den|von der|von)\\s+.+";
	static final String replacePrefixPatterns = "(?i)^(am|auf dem|auf|aus der|d|da|de l’|de|del|de la|de le|di|do|dos|du|im|la|le|mac|mc|mhac|mhic giolla|mhíc|mic|ni|ní|níc|o|ó|ua|ui|uí|van de|van den|van der|van|vom|von dem|von den|von der|von)\\s+";
	static final String excludedNamePatterns = "(?i).*\\s+(McDonald|MacChrystal|O'Brian|O'Neal).*$";
	static final String transliterationPatterns = "[^a-zA-Z]";

	static final String APattern = "\\u00C4|\\u00E4|\\u00C0|\\u00E0|\\u00C1|\\u00E1|\\u00C2|\\u00E2|\\u00C3|\\u00E3|\\u00C5|\\u00E5|\\u01CD|\\u01CE|\\u0104|\\u0105|\\u0102|\\u0103|\\u00C6|\\u00E6";
	static final String CPattern = "\\u00C7|\\u00E7|\\u0106|\\u0107|\\u0108|\\u0109|\\u010C|\\u010D";
	static final String DPattern = "\\u010E|\\u0111|\\u0110|\\u010F|\\u00F0";
	static final String EPattern = "\\u00C8|\\u00E8|\\u00C9|\\u00E9|\\u00CA|\\u00EA|\\u00CB|\\u00EB|\\u011A|\\u011B|\\u0118|\\u0119";
	static final String GPattern = "\\u011C|\\u011D|\\u0122|\\u0123|\\u011E|\\u011F";
	static final String HPattern = "\\u0124|\\u0125";
	static final String IPattern = "\\u00CC|\\u00EC|\\u00CD|\\u00ED|\\u00CE|\\u00EE|\\u00CF|\\u00EF|\\u0131";
	static final String JPattern = "\\u0134|\\u0135";
	static final String KPattern = "\\u0136|\\u0137";
	static final String LPattern = "\\u0139|\\u013A|\\u013B|\\u013C|\\u0141|\\u0142|\\u013D|\\u013E";
	static final String NPattern = "\\u00D1|\\u00F1|\\u0143|\\u0144|\\u0147|\\u0148";
	static final String OPattern = "\\u00D6|\\u00F6|\\u00D2|\\u00F2|\\u00D3|\\u00F3|\\u00D4|\\u00F4|\\u00D5|\\u00F5|\\u0150|\\u0151|\\u00D8|\\u00F8|\\u0152|\\u0153";
	static final String RPattern = "\\u0154|\\u0155|\\u0158|\\u0159";
	static final String SPattern = "\\u1E9E|\\u00DF|\\u015A|\\u015B|\\u015C|\\u015D|\\u015E|\\u015F|\\u0160|\\u0161|\\u0218|\\u0219";
	static final String TPattern = "\\u0164|\\u0165|\\u0162|\\u0163|\\u00DE|\\u00FE|\\u021A|\\u021B";
	static final String UPattern = "\\u00DC|\\u00FC|\\u00D9|\\u00F9|\\u00DA|\\u00FA|\\u00DB|\\u00FB|\\u0170|\\u0171|\\u0168|\\u0169|\\u0172|\\u0173|\\u016E|\\u016F";
	static final String WPattern = "\\u0174|\\u0175";
	static final String YPattern = "\\u00DD|\\u00FD|\\u0178|\\u00FF|\\u0176|\\u0177";
	static final String ZPattern = "\\u0179|\\u017A|\\u017D|\\u017E|\\u017B|\\u017C";

	public static void main(String[] args) {
		InputStream is = null;
		JsonReader reader = null;

		try {
			is = new FileInputStream(new File("resources" + File.separator + "UserObjects.json"));

			reader = Json.createReader(is);

			JsonObject jsonObj = reader.readObject();
			JsonArray userArray = jsonObj.getJsonArray("users");
			for (int i = 0; i < userArray.size(); i++) {
				JsonObject userModel = userArray.getJsonObject(i);
				System.out.println("CONCAT: " + genCONCAT(userModel));
			}
			// System.out.println(userArray.toString());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/***
	 * Any prefixes to the names that denote titles, position, profession or
	 * academic qualifications, are to be removed. This includes, but is not limited
	 * to the following list; this list is not case sensitive: atty, coach, dame,
	 * dr, fr, gov, honorable, madam(e), maid, master, miss, monsieur, mr, mrs, ms,
	 * mx, ofc, ph.d, pres, prof, rev, sir
	 * 
	 * @param firstname
	 * @return
	 */
	public static String removeTitle(String firstname) {
		String result = firstname.replaceFirst(titlePatterns, "");
		if (result != null) {
			return result.trim();
		} else {
			return result;
		}
	}

	/***
	 * Retain only the first word in firstname field
	 * 
	 * @param firstname
	 * @return
	 */
	public static String removeOtherNameInFirstname(String firstname) {
		// Pattern p = Pattern.compile(otherNameInFirstnamePattern);
		// return p.matcher(firstname).replaceAll("");
		return firstname.replaceAll(otherNameInFirstnamePattern, "");
	}

	/***
	 * am, auf, auf dem, aus der, d, da, de, de l’, del, de la, de le, di, do, dos,
	 * du, im, la, le, mac, mc, mhac, mhíc, mhic giolla, mic, ni, ní, níc, o, ó, ua,
	 * ui, uí, van, van de, van den, van der, vom, von, von dem, von den, von der
	 * Prefixes to surnames that are not included above, or prefixes attached to the
	 * name, i.e. McDonald, MacChrystal, O'Brian, O'Neal, should not be removed;
	 * 
	 * @param surname
	 * @return
	 */
	public static String removePrefix(String surname) {
		if (surname.matches(matchPrefixPatterns) && !surname.matches(excludedNamePatterns)) {
			String result = surname.replaceFirst(replacePrefixPatterns, "");
			if (result != null) {
				return result.trim();
			} else {
				return result;
			}
		} else {
			return surname;
		}
	}

	/***
	 * Leaves any English A-Z or a-z character untouched and removes all the
	 * diacritics, apostrophes, hyphens, punctuation marks and spaces.
	 * 
	 * @param name
	 * @return
	 */
	public static String transliteration(String name) {
		String result = name.replaceAll(APattern, "A").replaceAll(CPattern, "C").replaceAll(DPattern, "D")
				.replaceAll(EPattern, "E").replaceAll(GPattern, "G").replaceAll(HPattern, "H").replaceAll(IPattern, "I")
				.replaceAll(JPattern, "J").replaceAll(KPattern, "K").replaceAll(LPattern, "L").replaceAll(NPattern, "N")
				.replaceAll(OPattern, "O").replaceAll(RPattern, "R").replaceAll(SPattern, "S").replaceAll(TPattern, "T")
				.replaceAll(UPattern, "U").replaceAll(WPattern, "W").replaceAll(YPattern, "Y")
				.replaceAll(ZPattern, "Z");
		result = result.replaceAll(transliterationPatterns, "");
		return result;
	}

	/***
	 * Generate CONCAT code
	 * 
	 * @param userModel
	 * @return
	 */
	public static String genCONCAT(JsonObject userModel) {
		String firstname = null;
		String surname = null;

		try {

			firstname = userModel.getString("firstname");
			surname = userModel.getString("surname");

			if (firstname == null || surname == null) {
				return null;
			}
			firstname = firstname.toUpperCase();
			surname = surname.toUpperCase();

			firstname = removeTitle(firstname);
			firstname = removeOtherNameInFirstname(firstname);
			surname = removePrefix(surname);

			firstname = transliteration(firstname);
			surname = transliteration(surname);

			firstname = StringUtils.rightPad(firstname, 5, "#");
			surname = StringUtils.rightPad(surname, 5, "#");

			firstname = StringUtils.left(firstname, 5);
			surname = StringUtils.left(surname, 5);

			return firstname.concat(surname);
		} catch (ClassCastException cce) {
			System.out.println(String.format(cce.toString() + " (firstname: %s, surname: %s)", firstname, surname));
			return null;
		} catch (NullPointerException npe) {
			System.out.println("the specified json key name doesn't have any mapping");
			return null;
		}
	}
}
