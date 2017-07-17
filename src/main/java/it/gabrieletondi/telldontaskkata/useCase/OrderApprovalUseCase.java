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
        run(order, request.isApproved());
    }

    public void run (final Order order, final boolean isApproved) {
        if (order.isShipped()) {
            throw new ShippedOrdersCannotBeChangedException();
        }

        if (isApproved && order.isRejected()) {
            throw new RejectedOrderCannotBeApprovedException();
        }

        if (!isApproved && order.isApproved()) {
            throw new ApprovedOrderCannotBeRejectedException();
        }

        if (isApproved) {
            order.approve();
        } else {
            order.reject();
        }
        orderRepository.save(order);
    }
}
