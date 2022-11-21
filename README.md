```java
var system = NumeralSystem.standard;
assert "MDCCXXIX".equals(system.toRoman(1729));
assert 1729 == system.fromRoman("MDCCXXIX");
	// "V̄" and "X̄" are 2 characters long.
system = system.with(Numeral.of("V̄", 5000), Numeral.of("X̄", 10000));
assert "X̄V̄MDCLI".equals(system.toRoman(16651));
assert 16651 == system.fromRoman("X̄V̄MDCLI");
```
