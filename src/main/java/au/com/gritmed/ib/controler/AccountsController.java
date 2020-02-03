package au.com.gritmed.ib.controler;

import au.com.gritmed.ib.domain.UiAccount;
import au.com.gritmed.ib.domain.UiTransaction;
import au.com.gritmed.ib.service.AccountsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountsController {
    private final AccountsService accountsService;

    @GetMapping
    @ResponseBody
    public List<UiAccount> getUserAccounts(@RequestParam(name = "userId") long userId,
            @RequestParam(name = "excludeLocked", required = false) Optional<Boolean> excludeLocked) {
        return accountsService.getAccounts(userId, excludeLocked.orElse(false));
    }

    @GetMapping("/{accountId}")
    @ResponseBody
    public List<UiTransaction> getAccountTransactions(@PathVariable long accountId,
            @RequestParam(name = "userId") long userId) {
        return accountsService.getTransactions(userId, accountId);
    }
}
