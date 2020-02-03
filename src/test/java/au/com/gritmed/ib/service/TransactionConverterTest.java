package au.com.gritmed.ib.service;

import au.com.gritmed.ib.domain.Transaction;
import au.com.gritmed.ib.domain.UiTransaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static au.com.gritmed.ib.domain.Currency.AUD;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionConverterTest {
    private static final LocalDateTime CURRENT_TIME = LocalDateTime.now();
    private static final LocalDate CURRENT_DATE = CURRENT_TIME.toLocalDate();

    @Test
    void shouldConvertToUiTransaction() {
        assertEquals(
                new UiTransaction("123", "AUCurrent123", CURRENT_DATE, "AUD", null, new BigDecimal("123.45"), "credit", "Salary"),
                TransactionConverter.toUiTransaction("123", "AUCurrent123", AUD, new Transaction(1, 901, CURRENT_TIME, BigInteger.valueOf(12345), "Salary"))
        );

        assertEquals(
                new UiTransaction("123", "AUCurrent123", CURRENT_DATE, "AUD", new BigDecimal("123.45"), null, "debit", "Salary"),
                TransactionConverter.toUiTransaction("123", "AUCurrent123", AUD, new Transaction(1, 901, CURRENT_TIME, BigInteger.valueOf(-12345), "Salary"))
        );
    }
}
