package au.com.gritmed.ib.service;

import au.com.gritmed.ib.domain.Account;
import au.com.gritmed.ib.domain.UiAccount;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;

import static au.com.gritmed.ib.domain.AccountType.SAVINGS;
import static au.com.gritmed.ib.domain.Currency.SGD;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountConverterTest {
    @Test
    void shouldConvertAccountToUiAccount() {
        LocalDate balanceDate = LocalDate.of(2020, 2, 1);
        UiAccount expected = new UiAccount(
                123, "585309208", "SGSavings726", "Savings",
                balanceDate, "SGD", new BigDecimal("1234.56"));

        Account account = new Account(987, "585309208", "SGSavings726", SAVINGS, SGD, balanceDate, new BigInteger("123456"), new ArrayList<>());
        assertEquals(expected, AccountConverter.toUiAccount(123, account));
    }
}
