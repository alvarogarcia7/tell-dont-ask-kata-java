package it.gabrieletondi.telldontaskkata.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@EqualsAndHashCode
@ToString
public class Product {
    private String name;
    private BigDecimal price;
    private Category category;

    private Product (final String name, final BigDecimal price, final Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public static Product aNew (final String name, final BigDecimal price, final Category category) {
        return new Product(name, price, category);
    }
}
