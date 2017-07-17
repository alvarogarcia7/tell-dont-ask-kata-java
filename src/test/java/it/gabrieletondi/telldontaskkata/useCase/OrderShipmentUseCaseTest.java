package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.domain.OrderStatus;
import it.gabrieletondi.telldontaskkata.doubles.TestOrderRepository;
import it.gabrieletondi.telldontaskkata.doubles.TestShipmentService;
import it.gabrieletondi.telldontaskkata.exception.OrderCannotBeShippedException;
import it.gabrieletondi.telldontaskkata.exception.OrderCannotBeShippedTwiceException;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class OrderShipmentUseCaseTest {
    private final TestOrderRepository orderRepository = new TestOrderRepository();
    private final TestShipmentService shipmentService = new TestShipmentService();
    private final OrderShipmentUseCase useCase = new OrderShipmentUseCase(orderRepository, shipmentService);

    @Test
    public void shipApprovedOrder() throws Exception {
        Order initialOrder = new Order();
        initialOrder.setId(1);
        initialOrder.approve();
        orderRepository.addOrder(initialOrder);

        OrderShipmentUseCase.OrderShipmentRequest request = new OrderShipmentUseCase.OrderShipmentRequest();
        request.setOrderId(1);

        useCase.run(request);

        int id = initialOrder.getId();
        assertThat(orderRepository.getById(id).getStatus(), is(OrderStatus.SHIPPED));
        assertThat(shipmentService.getShippedOrder(), is(initialOrder));
    }

    @Test(expected = OrderCannotBeShippedException.class)
    public void createdOrdersCannotBeShipped() throws Exception {
        Order initialOrder = new Order();
        initialOrder.setId(1);
        orderRepository.addOrder(initialOrder);

        OrderShipmentUseCase.OrderShipmentRequest request = new OrderShipmentUseCase.OrderShipmentRequest();
        request.setOrderId(1);

        useCase.run(request);

        int id = initialOrder.getId();
        assertThat(orderRepository.getById(id), is(nullValue()));
        assertThat(shipmentService.getShippedOrder(), is(nullValue()));
    }

    @Test(expected = OrderCannotBeShippedException.class)
    public void rejectedOrdersCannotBeShipped() throws Exception {
        Order initialOrder = new Order();
        initialOrder.setId(1);
        initialOrder.reject();
        orderRepository.addOrder(initialOrder);

        OrderShipmentUseCase.OrderShipmentRequest request = new OrderShipmentUseCase.OrderShipmentRequest();
        request.setOrderId(1);

        useCase.run(request);

        int id = initialOrder.getId();
        assertThat(orderRepository.getById(id), is(nullValue()));
        assertThat(shipmentService.getShippedOrder(), is(nullValue()));
    }

    @Test(expected = OrderCannotBeShippedTwiceException.class)
    public void shippedOrdersCannotBeShippedAgain() throws Exception {
        Order initialOrder = new Order();
        initialOrder.setId(1);
        initialOrder.ship();
        orderRepository.addOrder(initialOrder);

        OrderShipmentUseCase.OrderShipmentRequest request = new OrderShipmentUseCase.OrderShipmentRequest();
        request.setOrderId(1);

        useCase.run(request);

        int id = initialOrder.getId();
        assertThat(orderRepository.getById(id), is(nullValue()));
        assertThat(shipmentService.getShippedOrder(), is(nullValue()));
    }
}
