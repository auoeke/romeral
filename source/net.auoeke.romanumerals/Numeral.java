package net.auoeke.romanumerals;

public record Numeral(String roman, long binary) {
	public static Numeral of(String roman, long binary) {
		return new Numeral(roman, binary);
	}

	public static Numeral of(char roman, long binary) {
		return new Numeral(String.valueOf(roman), binary);
	}
}
