package au.com.gritmed.ib.dao;

import au.com.gritmed.ib.domain.Account;
import au.com.gritmed.ib.domain.UserAccount;
import au.com.gritmed.ib.utils.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static au.com.gritmed.ib.domain.AccountType.CURRENT;
import static au.com.gritmed.ib.domain.AccountType.SAVINGS;
import static au.com.gritmed.ib.domain.Currency.AUD;
import static au.com.gritmed.ib.domain.Currency.SGD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class UserAccountsRepositoryTest {
    @Autowired
    private UserAccountsRepository userAccountsRepository;
    @Autowired
    private TestHelper helper;

    @BeforeEach
    void setUp() {
        helper.accountInsert(901, "123-456-001", "Savings 001", SAVINGS, AUD, 100001);
        helper.accountInsert(902, "123-456-002", "Current 002", CURRENT, SGD, 200002);
        helper.accountInsert(903, "123-456-003", "Current 003", CURRENT, AUD, 300003);

        helper.userAccountInsert(1,101, 901, false);
        helper.userAccountInsert(2,101, 902, false);
        helper.userAccountInsert(3,101, 903, true);
        helper.userAccountInsert(4,102, 903, false);
    }

    @Test
    void shouldReturnEmptyListIfNoAccounts() {
        assertTrue(userAccountsRepository.findByUserId(999).isEmpty());
    }

    @Test
    void shouldBeAbleToRetrieveAllUserAccounts() {
        List<UserAccount> userAccounts = userAccountsRepository.findByUserId(101);

        assertEquals(3, userAccounts.size());
        List<Long> expectedAccountIds = new ArrayList<Long>() {{
            add(901L);
            add(902L);
            add(903L);
        }};

        userAccounts.forEach(ua -> assertTrue(expectedAccountIds.remove(ua.getAccount().getId())));
    }

    @Test
    void shouldBeAbleToReturnExistingAccount() {
        Optional<UserAccount> oUserAccount = userAccountsRepository.findByUserIdAndAccountId(101, 901);
        assertTrue(oUserAccount.isPresent());
        assertEquals(
                new UserAccount(1, 101, false, new Account(901, "123-456-001", "Savings 001", SAVINGS, AUD, LocalDate.of(2020, 2, 1), BigInteger.valueOf(100001), new ArrayList<>())),
                oUserAccount.get()
        );
    }
}