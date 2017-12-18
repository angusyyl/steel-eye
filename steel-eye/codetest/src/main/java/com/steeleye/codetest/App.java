package com.steeleye.codetest;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class App {
	List<String> titleList = new ArrayList<String>();

	public static void main(String[] args) {
		String titles = "atty, coach, dame, dr, fr, gov, honorable, madam(e), maid, master, miss, monsieur, mr, mrs, ms, mx, ofc, ph.d, pres, prof, rev, sir";

		// titleListtitles.split(",")
	}

	/***
	 * Any prefixes to the names that denote titles, position, profession or
	 * academic qualifications, are to be removed. This includes, but is not limited
	 * to the following list; this list is not case sensitive: atty, coach, dame,
	 * dr, fr, gov, honorable, madam(e), maid, master, miss, monsieur, mr, mrs, ms,
	 * mx, ofc, ph.d, pres, prof, rev, sir
	 * 
	 * @param name
	 * @return
	 */
	public static String removeTitle(String name) {
		return null;
	}

	/***
	 * am, auf, auf dem, aus der, d, da, de, de l’, del, de la, de le, di, do, dos,
	 * du, im, la, le, mac, mc, mhac, mhíc, mhic giolla, mic, ni, ní, níc, o, ó, ua,
	 * ui, uí, van, van de, van den, van der, vom, von, von dem, von den, von der
	 * Prefixes to surnames that are not included above, or prefixes attached to the
	 * name, i.e. McDonald, MacChrystal, O'Brian, O'Neal, should not be removed;
	 * 
	 * @param name
	 * @return
	 */
	public static String removePrefix(String name) {
		return null;
	}

	/***
	 * Leaves any English A-Z or a-z character untouched and removes all the
	 * diacritics, apostrophes, hyphens, punctuation marks and spaces.
	 * 
	 * @param name
	 * @return
	 */
	public static String transliteration(String name) {
		return null;
	}

	/***
	 * Generate CONCAT code
	 * @param userModel
	 * @return
	 */
	public static String genCONCAT(JSONObject userModel) {

		return null;
	}
}
