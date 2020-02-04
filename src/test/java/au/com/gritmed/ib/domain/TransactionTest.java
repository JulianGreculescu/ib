package au.com.gritmed.ib.domain;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TransactionTest {
    @Test
    void shouldCorrectlyEvaluateCreditAndDebitAmount() {
        assertEquals(BigInteger.valueOf(12345), new Transaction(1, 901, LocalDateTime.now(), BigInteger.valueOf(12345), "My coffee").getCreditAmount());
        assertEquals(BigInteger.valueOf(12345), new Transaction(1, 901, LocalDateTime.now(), BigInteger.valueOf(-12345), "My coffee").getDebitAmount());
        assertNull(new Transaction(1, 901, LocalDateTime.now(), BigInteger.valueOf(12345), "My coffee").getDebitAmount());
        assertNull(new Transaction(1, 901, LocalDateTime.now(), BigInteger.valueOf(-12345), "My coffee").getCreditAmount());
        assertNull(new Transaction(1, 901, LocalDateTime.now(), BigInteger.valueOf(0), "My coffee").getCreditAmount());
        assertNull(new Transaction(1, 901, LocalDateTime.now(), BigInteger.valueOf(0), "My coffee").getDebitAmount());
    }

    @Test
    void shouldCorrectlyEvaluateTransactionType() {
        assertEquals("credit", new Transaction(1, 901, LocalDateTime.now(), BigInteger.valueOf(12345), "My coffee").getTransactionType());
        assertEquals("debit", new Transaction(1, 901, LocalDateTime.now(), BigInteger.valueOf(-12345), "My coffee").getTransactionType());
        assertNull(new Transaction(1, 901, LocalDateTime.now(), BigInteger.valueOf(0), "My coffee").getTransactionType());    }
}
