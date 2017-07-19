package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.OrderItem;
import it.gabrieletondi.telldontaskkata.domain.Product;
import it.gabrieletondi.telldontaskkata.exception.UnknownProductException;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class SellItemRequest {

    @Getter
    private final OrderItem request;

    public SellItemRequest (final Product product, final int quantity) throws UnknownProductException {
        if(product == null){
            throw new UnknownProductException();
        }
        this.request = new OrderItem(product, quantity);
    }

    public SellItemRequest (final OrderItem orderItem) {
        this.request = orderItem;
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
