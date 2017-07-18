package it.gabrieletondi.telldontaskkata.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;

@ToString
@EqualsAndHashCode
public class OrderItem {
    private Product product;
    private int quantity;
    private BigDecimal taxedAmount;
    private BigDecimal tax;

    public OrderItem (final Product product, final int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTaxedAmount() {
        final BigDecimal unitaryTaxedAmount = product.getPrice().add(unitaryTax()).setScale(2, HALF_UP);
        return unitaryTaxedAmount.multiply(valueOf(quantity)).setScale(2, HALF_UP);
    }

    public void setTaxedAmount(BigDecimal taxedAmount) {
        this.taxedAmount = taxedAmount;
    }

    public BigDecimal getTax() {
        final BigDecimal unitaryTax = unitaryTax();
        return unitaryTax.multiply(valueOf(quantity));
    }

    private BigDecimal unitaryTax () {
        return product.getPrice().divide(valueOf(100)).multiply(product.getCategory().getTaxPercentage()).setScale(2, HALF_UP);
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }
}
