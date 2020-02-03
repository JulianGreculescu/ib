package au.com.gritmed.ib.utils;

import au.com.gritmed.ib.domain.AccountType;
import au.com.gritmed.ib.domain.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Component;

@Component
public class TestHelper {
    private static final String USER_ACCOUNT_INSERT =
            "INSERT INTO user_accounts(id, user_id, account_id, locked) VALUES (?, ?, ?, ?)";
    private static final String ACCOUNT_INSERT =
            "INSERT INTO accounts(id, account_number, account_name, account_type, balance_date, currency, opening_balance)"
          + "VALUES (?, ?, ?, ?, PARSEDATETIME(?, 'yyyy-MM-dd'), ?, ?)";
    private static final String TRANSACTION_INSERT =
            "INSERT INTO transactions(id, account_id, transaction_date_time, amount, narrative)"
          + "VALUES(?, ?, PARSEDATETIME(?, 'yyyy-MM-dd'), ?, ?)";
    private static final String USER_ACCOUNTS_TRUNCATE = "TRUNCATE TABLE user_accounts";
    private static final String ACCOUNTS_TRUNCATE = "DELETE FROM accounts";
    private static final String TRANSACTIONS_TRUNCATE = "TRUNCATE TABLE transactions";

    @Autowired
    private JdbcOperations jdbcOperations;

    public void userAccountInsert(long id, long userId, long accountId, boolean locked) {
        jdbcOperations.update(USER_ACCOUNT_INSERT, id, userId, accountId, locked);
    }

    public void accountInsert(long id, String accountNumber, String accountName, AccountType accountType, Currency currency, long openingBalance) {
        jdbcOperations.update(ACCOUNT_INSERT, id, accountNumber, accountName, accountType.name(), "2020-02-01", currency.name(), openingBalance);
    }

    public void transactionInsert(long id, long accountId, String transactionDateTime, long amount, String narrative) {
        jdbcOperations.update(TRANSACTION_INSERT, id, accountId, transactionDateTime, amount, narrative);
    }

    public void userAccountsTruncate() {
        jdbcOperations.update(USER_ACCOUNTS_TRUNCATE);
    }

    public void accountsTruncate() {
        jdbcOperations.update(ACCOUNTS_TRUNCATE);
    }

    public void transactionsTruncate() {
        jdbcOperations.update(TRANSACTIONS_TRUNCATE);
    }
}
