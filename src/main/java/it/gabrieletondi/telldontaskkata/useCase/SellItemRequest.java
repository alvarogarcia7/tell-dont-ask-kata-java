package it.gabrieletondi.telldontaskkata.useCase;

import java.util.List;

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

    public static class SellItemsRequest {
        private List<SellItemRequest> requests;

        public void setRequests(List<SellItemRequest> requests) {
            this.requests = requests;
        }

        public List<SellItemRequest> getRequests() {
            return requests;
        }
    }
}
