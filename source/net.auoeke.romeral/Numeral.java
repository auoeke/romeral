package net.auoeke.romeral;

import java.util.Objects;

public sealed class Numeral implements Comparable<Numeral> permits net.auoeke.romeral.NumeralElement {
	final String roman;
	final long value;
	final int firstDigit;

	Numeral(String roman, long value, int firstDigit) {
		this.roman = roman;
		this.value = value;
		this.firstDigit = firstDigit;
	}

	public static Numeral of(String roman, long value) {
		if (Objects.requireNonNull(roman, "roman is null").isEmpty()) {
			throw new IllegalArgumentException("roman is empty");
		}

		var firstDigit = value;

		while (firstDigit >= 10) {
			var quotient = firstDigit / 10;

			if (quotient * 10 != firstDigit) {
				throw new IllegalArgumentException(value + " is not 0 or 1 or 5 followed by /0*/");
			}

			firstDigit = quotient;
		}

		if (firstDigit != 0 && firstDigit != 1 && firstDigit != 5) {
			throw new IllegalArgumentException(value + " does not start with 0, 1 or 5");
		}

		return new Numeral(roman, value, (int) firstDigit);
	}

	public static Numeral of(char roman, long value) {
		return of(String.valueOf(roman), value);
	}

	public String roman() {
		return this.roman;
	}

	public long value() {
		return this.value;
	}

	public int firstDigit() {
		return this.firstDigit;
	}

	@Override public int compareTo(Numeral numeral) {
		return (int) (this.value() - numeral.value());
	}

	@Override public String toString() {
		return this.roman + " = " + this.value;
	}

	@Override public boolean equals(Object o) {
		return this == o || o instanceof Numeral numeral && numeral.roman.equals(this.roman) && this.value == numeral.value;
	}

	@Override public int hashCode() {
		return Objects.hash(this.roman, this.value);
	}
}
