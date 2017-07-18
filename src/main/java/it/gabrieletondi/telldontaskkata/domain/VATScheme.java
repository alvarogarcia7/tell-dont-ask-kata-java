package it.gabrieletondi.telldontaskkata.domain;

import java.math.BigDecimal;

public interface VATScheme {
    BigDecimal getTaxedAmount (Product product, int quantity);

    BigDecimal getTax (Product product, int quantity);
}
