package it.gabrieletondi.telldontaskkata.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(exclude = "vatScheme")
public class OrderItem {
    private final Product product;
    private int quantity;

    public OrderItem (final Product product, final int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Tax tax () {
        return product.taxes().multiple(quantity);
    }
}
