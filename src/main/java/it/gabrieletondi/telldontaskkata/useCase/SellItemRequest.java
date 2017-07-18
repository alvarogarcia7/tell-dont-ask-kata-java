package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Product;

import java.util.Arrays;
import java.util.List;

public class SellItemRequest {
    private final Product product;
    private int quantity;

    public SellItemRequest (final Product product, final int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct () {
        return product;
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
