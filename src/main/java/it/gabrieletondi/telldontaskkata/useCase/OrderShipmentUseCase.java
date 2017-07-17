package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.repository.OrderRepository;
import it.gabrieletondi.telldontaskkata.service.ShipmentService;

public class OrderShipmentUseCase {
    private final OrderRepository orderRepository;
    private final ShipmentService shipmentService;

    public OrderShipmentUseCase(OrderRepository orderRepository, ShipmentService shipmentService) {
        this.orderRepository = orderRepository;
        this.shipmentService = shipmentService;
    }

    @Deprecated
    /**
     * Prefer using run(Order)
     */
    public void run(OrderShipmentRequest request) {
        final Order order = orderRepository.getById(request.getOrderId());
        run(order);
    }


    public void run (final Order order) {
        order.validateShip();
        shipmentService.ship(order);
        order.ship();
        orderRepository.save(order);
    }

    @Deprecated
    public static class OrderShipmentRequest {
        private int orderId;

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public int getOrderId() {
            return orderId;
        }
    }
}
