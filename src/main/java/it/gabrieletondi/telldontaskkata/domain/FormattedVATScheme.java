package it.gabrieletondi.telldontaskkata.domain;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

public class FormattedVATScheme implements VATScheme {
    private final OrderItem.VAT vat;

    public FormattedVATScheme (final OrderItem.VAT vat) {
        this.vat = vat;
    }

    @Override
    public BigDecimal getTaxedAmount (final Product product, final int quantity) {
        return format(vat.getTaxedAmount(product, quantity));
    }

    @Override
    public BigDecimal getTax (final Product product, final int quantity) {
        return format(vat.getTax(product, quantity));
    }

    private BigDecimal format (final BigDecimal value) {
        return value.setScale(2, HALF_UP);
    }
}
