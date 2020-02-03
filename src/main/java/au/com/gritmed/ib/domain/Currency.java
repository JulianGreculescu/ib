package au.com.gritmed.ib.domain;

import lombok.Getter;

public enum Currency {
    AUD,
    USD,
    SGD,
    BHD(3),
    BIF(0);


    @Getter
    private int decimalPlaces;

    Currency() {
        this(2);
    }

    Currency(int decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }
}
