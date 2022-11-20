package net.auoeke.romanumerals;

import java.util.Objects;

public record Numeral(String roman, long value) {
	public Numeral {
		if (Objects.requireNonNull(roman, "roman is null").isEmpty()) {
			throw new IllegalArgumentException("roman is empty");
		}

		var copy = value;

		while (copy >= 10) {
			var quotient = copy / 10;

			if (quotient * 10 != copy) {
				throw new IllegalArgumentException(value + " is not 0 or 1 or 5 followed by /0*/");
			}

			copy = quotient;
		}

		if (copy != 0 && copy != 1 && copy != 5) {
			throw new IllegalArgumentException("value does not with 0, 1 or 5");
		}
	}

	public static Numeral of(String roman, long value) {
		return new Numeral(roman, value);
	}

	public static Numeral of(char roman, long value) {
		return new Numeral(String.valueOf(roman), value);
	}

	@Override public String toString() {
		return this.roman + " = " + this.value;
	}
}
