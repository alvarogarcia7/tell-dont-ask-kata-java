package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.domain.OrderItem;
import it.gabrieletondi.telldontaskkata.repository.OrderRepository;

public class OrderCreationUseCase {
    private final OrderRepository orderRepository;

    public OrderCreationUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void run(SellItemRequest.SellItemsRequest request) {
        Order order = new Order();


        for (SellItemRequest itemRequest : request.getRequests()) {
            order.add(itemRequest.getRequest());
        }

        orderRepository.save(order);
    }

}
