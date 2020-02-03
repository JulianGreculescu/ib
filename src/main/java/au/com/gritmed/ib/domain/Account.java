package au.com.gritmed.ib.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "accounts")
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"accountNumber"})})
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Account {
    @Id
    @GeneratedValue
    private long id;

    @NotEmpty(message = "Account number cannot be empty")
    @Digits(integer = 10, fraction = 0, message = "Account number must be up to 10 digits only")
    private String accountNumber;

    @NotEmpty(message = "Account name cannot be empty")
    private String accountName;

    @NotEmpty(message = "Account type cannot be empty")
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AccountType accountType;

    @NotEmpty(message = "Account currency cannot be empty")
    @Enumerated(EnumType.STRING)
    @Column(length = 3)
    private Currency currency;

    @NotEmpty(message = "Balance date cannot be empty")
    @PastOrPresent(message = "Balance date must not be in the future")
    private LocalDate balanceDate;

    @NotEmpty(message = "Account opening balance cannot be empty")
    @Digits(integer = 15, fraction = 0, message = "Account opening balance must be up to 15 digits")
    private BigInteger openingBalance;

    @OneToMany(mappedBy = "accountId", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    public Account() {
    }
}
