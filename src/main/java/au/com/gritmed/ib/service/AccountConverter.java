package au.com.gritmed.ib.service;

import au.com.gritmed.ib.domain.Account;
import au.com.gritmed.ib.domain.Currency;
import au.com.gritmed.ib.domain.UiAccount;

import java.math.BigDecimal;

/**
 * Utility class to convert an @{@link Account} class as it comes from the database
 * into an @{@link UiAccount} as needed in the client UI.
 */
public final class AccountConverter {
    private AccountConverter() {
    }

    public static UiAccount toUiAccount(long userId, Account account) {
        Currency currency = account.getCurrency();

        return new UiAccount(userId, account.getAccountNumber(), account.getAccountName(),
                account.getAccountType().toString(), account.getBalanceDate(), currency.name(),
                new BigDecimal(account.getOpeningBalance(), currency.getDecimalPlaces()));
    }
}
