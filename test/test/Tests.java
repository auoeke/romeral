package test;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import static net.auoeke.romanumerals.Romanumerals.*;
import static org.junit.jupiter.api.Assertions.*;

@Testable
public class Tests {
	@Test void test() {
		eq("nulla", 0);
		eq("I", 1);
		eq("II", 2);
		eq("III", 3);
		eq("IV", 4);
		eq("V", 5);
		eq("VI", 6);
		eq("VII", 7);
		eq("VIII", 8);
		eq("IX", 9);
		eq("X", 10);
		eq("XI", 11);
		eq("XII", 12);
		eq("XIII", 13);
		eq("XIV", 14);
		eq("XV", 15);
		eq("XVI", 16);
		eq("XVII", 17);
		eq("XVIII", 18);
		eq("XIX", 19);
		eq("XX", 20);
		eq("XXI", 21);
		eq("XXII", 22);
		eq("XXIII", 23);
		eq("XXIV", 24);
		eq("XXV", 25);
		eq("XXVI", 26);
		eq("XXVII", 27);
		eq("XXVIII", 28);
		eq("XXIX", 29);
		eq("XXX", 30);
		eq("XXXI", 31);
		eq("XXXII", 32);
		eq("XXXIII", 33);
		eq("XXXIV", 34);
		eq("XXXV", 35);
		eq("XXXVI", 36);
		eq("XXXVII", 37);
		eq("XXXVIII", 38);
		eq("XXXIX", 39);
		eq("XL", 40);
		eq("XLI", 41);
		eq("XLII", 42);
		eq("XLIII", 43);
		eq("XLIV", 44);
		eq("XLV", 45);
		eq("XLVI", 46);
		eq("XLVII", 47);
		eq("XLVIII", 48);
		eq("XLIX", 49);
		eq("L", 50);
		eq("LX", 60);
		eq("LXX", 70);
		eq("LXXX", 80);
		eq("XC", 90);
		eq("C", 100);
		eq("CC", 200);
		eq("CCC", 300);
		eq("CD", 400);
		eq("D", 500);
		eq("DC", 600);
		eq("DCC", 700);
		eq("DCCC", 800);
		eq("CM", 900);
		eq("M", 1000);
		eq("MDCCXXIX", 1729);
	}

	private static void eq(String roman, long binary) {
		assertEquals(roman, toRoman(binary));
	}
}
