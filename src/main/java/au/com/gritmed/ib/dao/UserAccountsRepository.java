package au.com.gritmed.ib.dao;

import au.com.gritmed.ib.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAccountsRepository extends JpaRepository<UserAccount, Long> {
    List<UserAccount> findByUserId(long userId);
    Optional<UserAccount> findByUserIdAndAccountId(long userId, long accountId);
}
