package au.com.gritmed.ib.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

;

class CurrencyTest {
    @Test
    void shouldSupportMultipleDecimalsPlaces() {
        assertEquals(2, Currency.AUD.getDecimalPlaces());
        assertEquals(2, Currency.USD.getDecimalPlaces());
        assertEquals(2, Currency.SGD.getDecimalPlaces());
        assertEquals(3, Currency.BHD.getDecimalPlaces());
        assertEquals(0, Currency.BIF.getDecimalPlaces());
    }
}