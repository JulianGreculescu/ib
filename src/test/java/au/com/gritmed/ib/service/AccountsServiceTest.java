package au.com.gritmed.ib.service;

import au.com.gritmed.ib.dao.UserAccountsRepository;
import au.com.gritmed.ib.domain.*;
import au.com.gritmed.ib.exception.AccountsNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static au.com.gritmed.ib.domain.AccountType.CURRENT;
import static au.com.gritmed.ib.domain.AccountType.SAVINGS;
import static au.com.gritmed.ib.domain.Currency.AUD;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountsServiceTest {
    private static final long BOB = 123;
    private static final LocalDateTime CURRENT_TIME = LocalDateTime.now();
    private static final List<UserAccount> USER_ACCOUNTS = new ArrayList<>();
    static {
        USER_ACCOUNTS.add(new UserAccount(1, BOB, false,
                new Account(901, "123-456-001", "Savings 001", SAVINGS, AUD, LocalDate.now(), BigInteger.valueOf(12345),
                        new ArrayList<Transaction>() {{
                            add(new Transaction(1001, 901, CURRENT_TIME, BigInteger.valueOf(500000), "Salary"));
                            add(new Transaction(1002, 901, CURRENT_TIME, BigInteger.valueOf(-450), "Coffee"));
                        }})));
        USER_ACCOUNTS.add(new UserAccount(2, BOB, true,
                new Account(2, "123-456-002", "Current 001", CURRENT, AUD, LocalDate.now(), BigInteger.valueOf(54321), new ArrayList<>())));
        USER_ACCOUNTS.add(new UserAccount(3, BOB, false,
                new Account(3, "123-456-003", "Current 001", CURRENT, AUD, LocalDate.now(), BigInteger.valueOf(12321), new ArrayList<>())));
    }

    @Mock
    private UserAccountsRepository repository;

    @InjectMocks
    private AccountsService service;

    @Test
    void shouldRetrieveUserAccountsIncludingLockedOnes() {
        when(repository.findByUserId(BOB)).thenReturn(USER_ACCOUNTS);

        List<UiAccount> uiAccounts = service.getAccounts(BOB, false);

        assertEquals(3, uiAccounts.size());
        List<String> expectedAccountIds = new ArrayList<String>() {{
            add("123-456-001");
            add("123-456-002");
            add("123-456-003");
        }};

        uiAccounts.forEach(uiAccount -> assertTrue(expectedAccountIds.remove(uiAccount.getAccountNumber())));
    }

    @Test
    void shouldRetrieveUserAccountsExcludingLockedOnes() {
        when(repository.findByUserId(BOB)).thenReturn(USER_ACCOUNTS);

        List<UiAccount> uiAccounts = service.getAccounts(BOB, true);

        assertEquals(2, uiAccounts.size());
        List<String> expectedAccountIds = new ArrayList<String>() {{
            add("123-456-001");
            add("123-456-003");
        }};

        uiAccounts.forEach(uiAccount -> assertTrue(expectedAccountIds.remove(uiAccount.getAccountNumber())));
    }

    @Test
    void shouldThrowAccountsNotFoundExceptionWhenNotAccountsFoundForUser() {
        when(repository.findByUserId(BOB)).thenReturn(new ArrayList<>());

        Exception exception = assertThrows(AccountsNotFoundException.class, () -> service.getAccounts(BOB, false));

        assertEquals("No accounts found for userId = 123", exception.getMessage());
   }

    @Test
    void shouldRetrieveAccountTransactions() {
        when(repository.findByUserIdAndAccountId(BOB, 901)).thenReturn(Optional.of(USER_ACCOUNTS.get(0)));

        List<UiTransaction> transactions = service.getTransactions(BOB, 901);

        assertEquals(2, transactions.size());
        List<String> expectedNarratives = new ArrayList<String>() {{
            add("Salary");
            add("Coffee");
        }};

        transactions.forEach(transaction -> assertTrue(expectedNarratives.remove(transaction.getNarrative())));
    }

    @Test
    void shouldRetrieveAnEmptyListIfNoTransactions() {
        when(repository.findByUserIdAndAccountId(BOB, 902)).thenReturn(Optional.of(USER_ACCOUNTS.get(1)));

        List<UiTransaction> transactions = service.getTransactions(BOB, 902);

        assertTrue(transactions.isEmpty());
    }

    @Test
    void shouldThrowAccountsNotFoundExceptionWhenNotAccount() {
        when(repository.findByUserIdAndAccountId(BOB, 901)).thenReturn(Optional.empty());

        Exception exception = assertThrows(AccountsNotFoundException.class, () -> service.getTransactions(BOB, 901));

        assertEquals("No account found for userId = 123 and accountId = 901", exception.getMessage());
    }
}
