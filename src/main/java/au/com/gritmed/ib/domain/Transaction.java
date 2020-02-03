package au.com.gritmed.ib.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity(name = "transactions")
@Table
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Transaction {
    @Id
    @GeneratedValue
    private long id;

    @NotEmpty(message = "Account id cannot be empty")
    private long accountId;

    @NotEmpty(message = "Transaction date time cannot be empty")
    @PastOrPresent(message = "Transaction date time must not be in the future")
    private LocalDateTime transactionDateTime;

    @NotEmpty(message = "Transaction amount cannot be empty")
    @Digits(integer = 15, fraction = 0, message = "Transaction amount must be up to 15 digits")
    private BigInteger amount;

    @NotEmpty(message = "Transaction narrative cannot be empty")
    private String narrative;

    public Transaction() {
    }

    public BigInteger getCreditAmount() {
        return amount.compareTo(BigInteger.valueOf(0)) > 0 ? amount : null;
    }

    public BigInteger getDebitAmount() {
        return amount.compareTo(BigInteger.valueOf(0)) < 0 ? amount.multiply(BigInteger.valueOf(-1)) : null;
    }

    public String getTransactionType() {
        if (getCreditAmount() != null) {
            return "credit";
        } else if (getDebitAmount() != null) {
            return "debit";
        } else {
            return null;
        }
    }
}
