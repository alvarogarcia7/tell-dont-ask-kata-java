package it.gabrieletondi.telldontaskkata.useCase;

public class SellItemRequest {
    private int quantity;
    private String productName;

    public SellItemRequest (final String productName, final int quantity) {
        this.quantity = quantity;
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProductName() {
        return productName;
    }
}
