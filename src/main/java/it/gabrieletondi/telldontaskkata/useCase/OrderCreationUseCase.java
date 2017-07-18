package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.domain.Product;
import it.gabrieletondi.telldontaskkata.exception.UnknownProductException;
import it.gabrieletondi.telldontaskkata.repository.OrderRepository;

import static java.math.BigDecimal.valueOf;

public class OrderCreationUseCase {
    private final OrderRepository orderRepository;

    public OrderCreationUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void run(SellItemRequest.SellItemsRequest request) {
        Order order = new Order();


        for (SellItemRequest itemRequest : request.getRequests()) {
            Product product = itemRequest.getProduct();

            if (product == null) {
                throw new UnknownProductException();
            } else {
                order.add(product, itemRequest.getQuantity());
            }
        }

        orderRepository.save(order);
    }

}
