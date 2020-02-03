package au.com.gritmed.ib.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity(name = "userAccounts")
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"userId", "accountId"})})
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
/**
 * Represents the mapping between an Internet Banking user and the accounts
 * he or she owns. It also stores a locked flag with the meaning if locked
 * the user cannot make transactions against that account (e.g. because of
 * a lost/stolen card linked to that account)
 */
public class UserAccount {
    @Id
    @GeneratedValue
    private long id;

    @NotEmpty(message = "User id cannot be empty")
    private long userId;

    private boolean locked;

    @ManyToOne
    @JoinColumn(name = "accountId", referencedColumnName = "id")
    private Account account;

    public UserAccount() {
    }
}
