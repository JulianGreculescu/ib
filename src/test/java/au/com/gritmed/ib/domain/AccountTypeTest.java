package au.com.gritmed.ib.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTypeTest {
    @Test
    void shouldOverrideToString() {
        assertEquals("Savings", AccountType.SAVINGS.toString());
        assertEquals("Current", AccountType.CURRENT.toString());
        assertEquals("HomeLoan", AccountType.HOME_LOAN.toString());
        assertEquals("PersonalLoan", AccountType.PERSONAL_LOAN.toString());
        assertEquals("TermDeposit", AccountType.TERM_DEPOSIT.toString());
        assertEquals("Business", AccountType.BUSINESS.toString());
    }
}
