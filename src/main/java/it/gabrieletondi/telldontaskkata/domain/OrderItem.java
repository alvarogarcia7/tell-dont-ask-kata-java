package it.gabrieletondi.telldontaskkata.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;

@ToString
@EqualsAndHashCode(exclude = "vatScheme")
public class OrderItem {
    private final VATScheme vatScheme;
    private final Product product;
    private int quantity;

    public OrderItem (final Product product, final int quantity) {
        this.product = product;
        this.quantity = quantity;
        vatScheme = new FormattedVATScheme(new VAT());
    }

    public BigDecimal getTaxedAmount() {
        return vatScheme.getTaxedAmount(product, quantity);
    }

    public BigDecimal getTax() {
        return vatScheme.getTax(product, quantity);
    }

    static class VAT implements VATScheme {
        @Override
        public BigDecimal getTaxedAmount (Product product, int quantity) {
            return product.taxes().taxedAmount().multiply(valueOf(quantity));
        }

        @Override
        public BigDecimal getTax (Product product, int quantity) {
            return unitaryTax(product).multiply(valueOf(quantity));
        }

        private BigDecimal unitaryTax (Product product) {
            return product.getPrice().divide(valueOf(100)).multiply(product.getCategory().getTaxPercentage()).setScale(2, HALF_UP);
        }
    }

}
