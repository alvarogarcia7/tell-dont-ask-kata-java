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
            OrderItem request1 = itemRequest.getRequest();
            order.add(request1);
        }

        orderRepository.save(order);
    }

}
