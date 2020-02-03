package au.com.gritmed.ib.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UiAccount {
    private final long userId;
    private final String accountNumber;
    private final String accountName;
    private final String accountType;
    private final LocalDate balanceDate;
    private final String currency;
    private final BigDecimal openingAvailableBalance;
}
