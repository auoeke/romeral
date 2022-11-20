package net.auoeke.romanumerals;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class NumeralSystem extends AbstractList<Numeral> {
	public static final NumeralSystem standard = of(
		Numeral.of("I", 1),
		Numeral.of("V", 5),
		Numeral.of("X", 10),
		Numeral.of("L", 50),
		Numeral.of("C", 100),
		Numeral.of("D", 500),
		Numeral.of("M", 1000)
	);

	private final Numeral[] numerals;

	private NumeralSystem(Numeral[] numerals) {
		this.numerals = numerals;

		if (this.isEmpty()) {
			throw new IllegalArgumentException("numerals is empty");
		}

		Set.of(Stream.of(numerals).map(Numeral::roman).toArray());
		Set.of(Stream.of(numerals).map(Numeral::value).toArray());
	}

	public static NumeralSystem of(Numeral... numerals) {
		return new NumeralSystem(numerals);
	}

	public static NumeralSystem of(List<Numeral> numerals) {
		return new NumeralSystem(numerals.toArray(Numeral[]::new));
	}

	public String toRoman(long value) {
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

		for (var index = this.size() - 1; index >= 0; --index) {
			var numeral = this.numerals[index];
			var binary = numeral.value();

			while (value >= binary) {
				builder.append(numeral.roman());
				value -= binary;
			}

			var previousIndex = startsWith5(binary) ? index - 1 : index - 2;

			if (previousIndex >= 0) {
				var previousNumeral = this.numerals[previousIndex];
				var difference = binary - previousNumeral.value();

				if (value >= difference) {
					builder.append(previousNumeral.roman());
					builder.append(numeral.roman());
					value -= difference;
				}
			}
		}

		return builder.toString();
	}

	public NumeralSystem with(Numeral... numerals) {
		var combined = Arrays.copyOf(this.numerals, this.size() + numerals.length);
		System.arraycopy(numerals, 0, combined, this.size(), numerals.length);

		return new NumeralSystem(combined);
	}

	@Override public int size() {
		return this.numerals.length;
	}

	@Override public Iterator<Numeral> iterator() {
		return Arrays.asList(this.numerals).iterator();
	}

	@Override public Numeral[] toArray() {
		return this.numerals.clone();
	}

	@Override public Numeral get(int index) {
		return this.numerals[index];
	}

	@Override public boolean equals(Object o) {
		return this == o || o instanceof NumeralSystem system && Arrays.equals(this.numerals, system.numerals);
	}

	@Override public int hashCode() {
		return Objects.hash(this.numerals);
	}

	@Override public String toString() {
		return Stream.of(this.numerals).map(Numeral::toString).collect(Collectors.joining(", ", "[", "]"));
	}

	private static boolean startsWith5(long value) {
		while (value > 5) {
			value /= 10;
		}

		return value == 5;
	}
}
