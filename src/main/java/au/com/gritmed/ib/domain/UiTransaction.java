package au.com.gritmed.ib.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UiTransaction {
    private final String accountNumber;
    private final String accountName;
    private final LocalDate valueDate;
    private final String currency;
    private final BigDecimal debitAmount;
    private final BigDecimal creditAmount;
    private final String amountType;
    private final String narrative;
}
