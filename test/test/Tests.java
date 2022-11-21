package test;

import java.util.Map;
import java.util.stream.LongStream;
import net.auoeke.romeral.Numeral;
import net.auoeke.romeral.NumeralSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import static java.util.Map.*;
import static org.junit.jupiter.api.Assertions.*;

@Testable
public class Tests {
	@Test void test() {
		LongStream.of(2, 3, 4, 6, 7, 8, 9, 11, 12, 13, 14, 16, 17, 18, 19, 20, 30, 40, 60, 70, 80, 90, 101, 110, 1010, 1011, 1100, 1101, 11000)
			.forEach(value -> Assertions.assertThrows(IllegalArgumentException.class, () -> Numeral.of("A", value), String.valueOf(value)));

		LongStream.of(1, 5, 10, 50, 100, 500, 1000, 5000, 10000, 50000, 100000)
			.forEach(value -> Assertions.assertDoesNotThrow(() -> Numeral.of("A", value), String.valueOf(value)));

		Assertions.assertThrows(IllegalArgumentException.class, () -> NumeralSystem.of(Numeral.of("A", 1), Numeral.of("A", 5)));

		var system = NumeralSystem.standard.with(Numeral.of("A", 5000));

		Map.ofEntries(
			entry("IIII", 4),
			entry("VIIII", 9),
			entry("XIIII", 14),
			entry("XVIIII", 19)
		).forEach((roman, value) -> Assertions.assertThrows(NumberFormatException.class, () -> from(roman, value)));

		test("X̄V̄MDCLI", 16651, NumeralSystem.standard.with(Numeral.of("V̄", 5000), Numeral.of("X̄", 10000)));
		test("MDCCXXIX", 1729);

		test("N", 0);
		test("I", 1);
		test("II", 2);
		test("III", 3);
		test("IV", 4);
		test("V", 5);
		test("VI", 6);
		test("VII", 7);
		test("VIII", 8);
		test("IX", 9);
		test("X", 10);
		test("XI", 11);
		test("XII", 12);
		test("XIII", 13);
		test("XIV", 14);
		test("XV", 15);
		test("XVI", 16);
		test("XVII", 17);
		test("XVIII", 18);
		test("XIX", 19);
		test("XX", 20);
		test("XXI", 21);
		test("XXII", 22);
		test("XXIII", 23);
		test("XXIV", 24);
		test("XXV", 25);
		test("XXVI", 26);
		test("XXVII", 27);
		test("XXVIII", 28);
		test("XXIX", 29);
		test("XXX", 30);
		test("XXXI", 31);
		test("XXXII", 32);
		test("XXXIII", 33);
		test("XXXIV", 34);
		test("XXXV", 35);
		test("XXXVI", 36);
		test("XXXVII", 37);
		test("XXXVIII", 38);
		test("XXXIX", 39);
		test("XL", 40);
		test("XLI", 41);
		test("XLII", 42);
		test("XLIII", 43);
		test("XLIV", 44);
		test("XLV", 45);
		test("XLVI", 46);
		test("XLVII", 47);
		test("XLVIII", 48);
		test("XLIX", 49);
		test("L", 50);
		test("LX", 60);
		test("LXX", 70);
		test("LXXX", 80);
		test("XC", 90);
		test("C", 100);
		test("CC", 200);
		test("CCC", 300);
		test("CD", 400);
		test("D", 500);
		test("DC", 600);
		test("DCC", 700);
		test("DCCC", 800);
		test("CM", 900);
		test("M", 1000);
	}

	private static void test(String roman, long value) {
		test(roman, value, NumeralSystem.standard);
	}

	private static void test(String roman, long value, NumeralSystem system) {
		to(roman, value, system);
		from(roman, value, system);
	}

	private static void from(String roman, long value) {
		from(roman, value, NumeralSystem.standard);
	}

	private static void from(String roman, long value, NumeralSystem system) {
		assertEquals(value, system.fromRoman(roman));
	}

	private static void to(String roman, long value) {
		to(roman, value, NumeralSystem.standard);
	}

	private static void to(String roman, long value, NumeralSystem system) {
		assertEquals(roman, system.toRoman(value));
	}
}
