package au.com.gritmed.ib.domain;

import org.apache.commons.text.CaseUtils;

public enum AccountType {
    SAVINGS,
    CURRENT,
    HOME_LOAN,
    PERSONAL_LOAN,
    TERM_DEPOSIT,
    BUSINESS;


    @Override
    public String toString() {
        return CaseUtils.toCamelCase(name(), true, '_');
    }
}
