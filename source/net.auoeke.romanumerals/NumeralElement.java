package net.auoeke.romanumerals;

final class NumeralElement extends Numeral {
	final int index;

	NumeralElement(Numeral numeral, int index) {
		super(numeral.roman, numeral.value, numeral.firstDigit);

		this.index = index;
	}
}
