package it.gabrieletondi.telldontaskkata.domain;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;

public class Tax {
    private final BigDecimal taxedAmount;
    private final BigDecimal taxes;

    private Tax (final BigDecimal taxedAmount, final BigDecimal taxes) {
        this.taxedAmount = taxedAmount;
        this.taxes = taxes;
    }

    public BigDecimal taxedAmount () {
        return taxedAmount;
    }

    public static Tax byPercentage (final BigDecimal price, final BigDecimal percentage) {
        final BigDecimal taxes = price.divide(valueOf(100)).multiply(percentage).setScale(2, HALF_UP);
        return new Tax(price.add(taxes), taxes);
    }

    public Tax multiple (final int quantity) {
        return Tax.multiple(this, quantity);
    }

    private static Tax multiple (final Tax tax, final int quantity) {
        BigDecimal multiplier = BigDecimal.valueOf(quantity);
        return new Tax(
                tax.taxedAmount.multiply(multiplier),
                tax.taxes.multiply(multiplier));
    }

    public BigDecimal tax () {
        return taxes;
    }
}
