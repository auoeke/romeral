package net.auoeke.romeral;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class NumeralSystem extends AbstractList<Numeral> {
	public static final NumeralSystem standard = of(
		Numeral.of("N", 0),
		Numeral.of("I", 1),
		Numeral.of("V", 5),
		Numeral.of("X", 10),
		Numeral.of("L", 50),
		Numeral.of("C", 100),
		Numeral.of("D", 500),
		Numeral.of("M", 1000)
	);

	private final int minIndex;
	private final NumeralElement zero;
	private final NumeralElement[] numerals;
	private final NumeralElement[] fromRomanOrder;

	@SuppressWarnings("StringEquality")
	private NumeralSystem(Numeral[] numerals) {
		if (numerals.length == 0) {
			throw new IllegalArgumentException("numerals is empty");
		}

		Set.of(Stream.of(numerals).map(Numeral::roman).toArray());
		Set.of(Stream.of(numerals).map(Numeral::value).toArray());

		Arrays.sort(numerals);
		this.numerals = IntStream.range(0, numerals.length).mapToObj(index -> new NumeralElement(numerals[index], index)).toArray(NumeralElement[]::new);
		var first = this.numerals[0];
		this.minIndex = first.value == 0 ? 1 : 0;
		this.zero = first.value == 0 ? first : standard.zero;

		this.fromRomanOrder = this.numerals.clone();
		Arrays.sort(this.fromRomanOrder, Comparator.comparing(Numeral::roman, (a, b) -> {
			var max = a.length() >= b.length() ? a : b;
			return max.startsWith(max == a ? b : a) ? -1 : a.compareTo(b);
		}));
	}

	public static NumeralSystem of(Numeral... numerals) {
		return new NumeralSystem(numerals.clone());
	}

	public static NumeralSystem of(Collection<Numeral> numerals) {
		return new NumeralSystem(numerals.toArray(Numeral[]::new));
	}

	public Numeral numeral(long value) {
		for (var numeral : this) {
			if (numeral.value == value) {
				return numeral;
			}
		}

		return null;
	}

	public Numeral numeral(CharSequence roman) {
		for (var numeral : this) {
			if (numeral.roman.contentEquals(roman)) {
				return numeral;
			}
		}

		return null;
	}

	public String toRoman(long value) {
		if (value == 0) {
			return this.zero.roman;
		}

		var builder = new StringBuilder();

		if (value < 0) {
			if (value == Long.MIN_VALUE) {
				throw new StackOverflowError("value == MIN_VALUE");
			}

			builder.append('-');
			value = -value;
		}

		for (var index = this.size() - 1; index >= this.minIndex; --index) {
			var numeral = this.get(index);
			var numeralValue = numeral.value;

			while (value >= numeralValue) {
				builder.append(numeral.roman);
				value -= numeralValue;
			}

			var previousIndex = numeral.firstDigit() == 5 ? index - 1 : index - 2;

			if (previousIndex >= this.minIndex) {
				var previousNumeral = this.get(previousIndex);
				var difference = numeralValue - previousNumeral.value;

				if (value >= difference) {
					builder.append(previousNumeral.roman);
					builder.append(numeral.roman);
					value -= difference;
				}
			}
		}

		return builder.toString();
	}

	public long fromRoman(String roman) {
		if (roman.isEmpty()) {
			throw new IllegalArgumentException("roman is empty");
		}

		var value = 0L;
		var nextNumeral = this.numeralPrefix(roman, 0);
		var consecutive = nextNumeral;
		var consecutiveCount = 0;
		NumeralElement beforeConsecutive = null;

		for (var index = 0; index < roman.length();) {
			if (nextNumeral == null) {
				throw new NumberFormatException("no valid numeral at \"%s\"[%s]".formatted(roman, index));
			}

			var numeral = nextNumeral;
			index += numeral.roman().length();

			if (numeral == this.zero && index < roman.length()) {
				throw new NumberFormatException(numeral + " is not the entire string");
			}

			if (numeral == consecutive) {
				if (++consecutiveCount == 4 && consecutive.index + 1 < this.size()) {
					var largerNumeral = this.get(consecutive.index + 1);
					var quotient = largerNumeral.value / consecutive.value;

					if (quotient == 5 || quotient == 10 && beforeConsecutive != null && beforeConsecutive.index == consecutive.index + 1 && beforeConsecutive.value / consecutive.value == 5) {
						throw new NumberFormatException("too many consecutive occurences of \"%s\"".formatted(consecutive.roman));
					}

					consecutiveCount = Integer.MIN_VALUE;
				}
			} else {
				consecutive = numeral;
				consecutiveCount = 1;
			}

			a: if (index < roman.length()) {
				if (consecutiveCount == 1) {
					beforeConsecutive = nextNumeral;
				}

				nextNumeral = this.numeralPrefix(roman, index);

				if (nextNumeral == null) {
					continue;
				}

				if (numeral.firstDigit() != 5) {
					var quotient = nextNumeral.value / numeral.value;

					if (quotient < Integer.MAX_VALUE) {
						switch ((int) quotient) {
							case 0, 1, 2 -> {
								break a;
							}
							case 5, 10 -> {
								value += nextNumeral.value - numeral.value;
								index += numeral.roman.length();
								nextNumeral = this.numeralPrefix(roman, index);

								continue;
							}
						}
					}

					throw new NumberFormatException("%s (\"%s\"[%s]) found after %s".formatted(nextNumeral.roman, roman, index, numeral.roman));
				}
			}

			value += numeral.value;
		}

		return value;
	}

	public NumeralSystem with(Numeral... numerals) {
		var combined = Arrays.copyOf(this.numerals, this.size() + numerals.length, Numeral[].class);
		System.arraycopy(numerals, 0, combined, this.size(), numerals.length);

		return new NumeralSystem(combined);
	}

	@Override public int size() {
		return this.numerals.length;
	}

	@Override public Iterator<Numeral> iterator() {
		return (Iterator) Arrays.asList(this.numerals).iterator();
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
		return Arrays.hashCode(this.numerals);
	}

	private NumeralElement numeralPrefix(String roman, int offset) {
		for (var numeral : this.fromRomanOrder) {
			if (roman.startsWith(numeral.roman, offset)) {
				return numeral;
			}
		}

		return null;
	}
}
