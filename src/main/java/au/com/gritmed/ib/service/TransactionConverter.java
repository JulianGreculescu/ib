package au.com.gritmed.ib.service;

import au.com.gritmed.ib.domain.Currency;
import au.com.gritmed.ib.domain.Transaction;
import au.com.gritmed.ib.domain.UiTransaction;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Utility class to convert an @{@link au.com.gritmed.ib.domain.Transaction} class as it comes from the database
 * into an @{@link au.com.gritmed.ib.domain.UiTransaction} as needed in the client UI.
 */
public final class TransactionConverter {
    private TransactionConverter() {
    }

    public static UiTransaction toUiTransaction(String accountNumber, String accountName, Currency currency, Transaction transaction) {

        return new UiTransaction(accountNumber, accountName, transaction.getTransactionDateTime().toLocalDate(),
                currency.name(), asBigDecimal(transaction.getDebitAmount(), currency.getDecimalPlaces()),
                asBigDecimal(transaction.getCreditAmount(), currency.getDecimalPlaces()),
                transaction.getTransactionType(), transaction.getNarrative());
    }

    private static BigDecimal asBigDecimal(BigInteger amount, int scale) {
        return amount == null ? null : new BigDecimal(amount, scale);
    }
}
