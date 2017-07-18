package it.gabrieletondi.telldontaskkata.useCase;

import java.util.Arrays;
import java.util.Collections;
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

        private final List<SellItemRequest> values;

        private SellItemsRequest (final List<SellItemRequest> sellItemRequests) {
            this.values = sellItemRequests;
        }

        public static SellItemsRequest of(SellItemRequest... requests) {
            return new SellItemsRequest(Arrays.asList(requests));
        }

        public List<SellItemRequest> getRequests () {
            return values;
        }
    }
}
