package net.auoeke.romanumerals;

public class Romanumerals {
	private static final Numeral[] numerals = {
		new Numeral("I", 1),
		new Numeral("V", 5),
		new Numeral("X", 10),
		new Numeral("L", 50),
		new Numeral("C", 100),
		new Numeral("D", 500),
		new Numeral("M",  1000)
	};

	public static String toRoman(long value) {
		return toRoman(value, numerals);
	}

	public static String toRoman(long value, Numeral[] numerals) {
		if (value == 0) {
			return "nulla";
		}

		var builder = new StringBuilder();

		if (value < 0) {
			if (value == Long.MIN_VALUE) {
				throw new StackOverflowError("value == MIN_VALUE");
			}

			builder.append('-');
			value = -value;
		}

		for (var index = numerals.length - 1; index >= 0; --index) {
			var numeral = numerals[index];
			var binary = numeral.binary();

			while (value >= binary) {
				builder.append(numeral.roman());
				value -= binary;
			}

			var b = binary;

			while (b > 5) {
				b /= 10;
			}

			var previousIndex = b == 5 ? index - 1 : index - 2;

			if (previousIndex >= 0) {
				var previousNumeral = numerals[previousIndex];
				var difference = binary - previousNumeral.binary();

				if (value >= difference) {
					builder.append(previousNumeral.roman());
					builder.append(numeral.roman());
					value -= difference;
				}
			}
		}

		return builder.toString();
	}
}
