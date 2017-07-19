package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.domain.OrderItem;
import it.gabrieletondi.telldontaskkata.repository.OrderRepository;
import org.mockito.internal.matchers.Or;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class OrderCreationUseCase {
    private final OrderRepository orderRepository;

    public OrderCreationUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void run(SellItemRequest.SellItemsRequest request) {
        BinaryOperator<Order> combiner = (order1, order2) -> order2;
        Order order = request.getRequests().stream().reduce(new Order(), add(), combiner);

        orderRepository.save(order);
    }

    private BiFunction<Order, SellItemRequest, Order> add () {
        return (order1, sellItemRequest) ->
        {
            order1.add(sellItemRequest.getRequest());
            return order1;
        };
    }

}
