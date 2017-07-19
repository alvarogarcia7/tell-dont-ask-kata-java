package it.gabrieletondi.telldontaskkata.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;

@ToString
@EqualsAndHashCode(exclude = "vatScheme")
public class OrderItem {
    private final VATScheme vatScheme;
    private final Product product;
    private int quantity;

    public OrderItem (final Product product, final int quantity) {
        this.product = product;
        this.quantity = quantity;
        vatScheme = new FormattedVATScheme(new VAT(product, quantity));
    }

    public BigDecimal getTaxedAmount() {
        return vatScheme.getTaxedAmount(product, quantity);
    }

    public BigDecimal getTax() {
        return vatScheme.getTax(product, quantity);
    }

    static class VAT implements VATScheme {

        private Tax tax;

        public VAT (final Product product, final int quantity) {
            this.tax = product.taxes().multiple(quantity);
        }

        @Override
        public BigDecimal getTaxedAmount (Product product, int quantity) {
            return tax.taxedAmount();
        }

        @Override
        public BigDecimal getTax (Product product, int quantity) {
            return tax.tax();
        }
    }

}
