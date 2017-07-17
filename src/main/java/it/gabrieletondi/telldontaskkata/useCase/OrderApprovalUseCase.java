package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.repository.OrderRepository;

public class OrderApprovalUseCase {
    private final OrderRepository orderRepository;

    public OrderApprovalUseCase (OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Deprecated
    public void run (OrderApprovalRequest request) {
        final Order order = orderRepository.getById(request.getOrderId());
        run(order, request);
    }

    public void run (final Order order, final OrderApprovalRequest request) {
        if (order.isShipped()) {
            throw new ShippedOrdersCannotBeChangedException();
        }

        if (request.isApproved() && order.isRejected()) {
            throw new RejectedOrderCannotBeApprovedException();
        }

        if (!request.isApproved() && order.isApproved()) {
            throw new ApprovedOrderCannotBeRejectedException();
        }

        request.process(order);
        orderRepository.save(order);
    }
}
