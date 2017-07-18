package it.gabrieletondi.telldontaskkata.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@EqualsAndHashCode
@ToString
public class Category {
    private final String name;
    private final BigDecimal taxPercentage;

    public Category (final String name, final BigDecimal taxPercentage) {
        this.name = name;
        this.taxPercentage = taxPercentage;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getTaxPercentage() {
        return taxPercentage;
    }
}
