package it.gabrieletondi.telldontaskkata.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@EqualsAndHashCode
@ToString
public class Category {
    private final String name;
    private BigDecimal taxPercentage;

    public Category (final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(BigDecimal taxPercentage) {
        this.taxPercentage = taxPercentage;
    }
}
