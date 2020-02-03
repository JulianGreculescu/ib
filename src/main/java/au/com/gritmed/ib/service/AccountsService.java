package au.com.gritmed.ib.service;

import au.com.gritmed.ib.dao.UserAccountsRepository;
import au.com.gritmed.ib.domain.Account;
import au.com.gritmed.ib.domain.UiAccount;
import au.com.gritmed.ib.domain.UiTransaction;
import au.com.gritmed.ib.domain.UserAccount;
import au.com.gritmed.ib.exception.AccountsNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountsService {
    private final UserAccountsRepository userAccountsRepository;

    public List<UiAccount> getAccounts(long userId, boolean excludeLocked) {
        List<UserAccount> userAccounts = userAccountsRepository.findByUserId(userId);

        if (userAccounts.isEmpty()) {
            throw new AccountsNotFoundException("No accounts found for userId = " + userId);
        }
        return userAccounts.stream()
                .filter(ua -> !(excludeLocked && ua.isLocked()))
                .map(ua -> AccountConverter.toUiAccount(userId, ua.getAccount()))
                .collect(Collectors.toList());
    }

    public List<UiTransaction> getTransactions(long userId, long accountId) {
        Account account = userAccountsRepository.findByUserIdAndAccountId(userId, accountId)
                .orElseThrow(() -> new AccountsNotFoundException("No account found for userId = " + userId + " and accountId = " + accountId))
                .getAccount();

        return account.getTransactions().stream()
                .map(t -> TransactionConverter.toUiTransaction(account.getAccountNumber(), account.getAccountName(), account.getCurrency(), t))
                .collect(Collectors.toList());
    }
}
