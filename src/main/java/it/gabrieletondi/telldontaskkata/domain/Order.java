package it.gabrieletondi.telldontaskkata.domain;

import it.gabrieletondi.telldontaskkata.exception.ApprovedOrderCannotBeRejectedException;
import it.gabrieletondi.telldontaskkata.exception.OrderCannotBeShippedException;
import it.gabrieletondi.telldontaskkata.exception.OrderCannotBeShippedTwiceException;
import it.gabrieletondi.telldontaskkata.exception.RejectedOrderCannotBeApprovedException;
import it.gabrieletondi.telldontaskkata.exception.ShippedOrdersCannotBeChangedException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static it.gabrieletondi.telldontaskkata.domain.OrderStatus.CREATED;
import static it.gabrieletondi.telldontaskkata.domain.OrderStatus.REJECTED;
import static it.gabrieletondi.telldontaskkata.domain.OrderStatus.SHIPPED;
import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;

public class Order {
    private final String currency;
    private final List<OrderItem> items;
    private OrderStatus status;
    private int id;

    public Order () {
        this.status = OrderStatus.CREATED;
        this.currency = "EUR";
        this.items = new ArrayList<>();
    }

    public String getCurrency() {
        return currency;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void reject(){
        if (isApproved()) {
            throw new ApprovedOrderCannotBeRejectedException();
        }

        if (isShipped()) {
            throw new ShippedOrdersCannotBeChangedException();
        }

        if(isCreated()) {
            status = OrderStatus.REJECTED;
        }
    }

    public void approve(){
        if (isRejected()) {
            throw new RejectedOrderCannotBeApprovedException();
        }

        if (isShipped()) {
            throw new ShippedOrdersCannotBeChangedException();
        }

        if(isCreated()){
            status = OrderStatus.APPROVED;
        }

    }

    private boolean isCreated () {
        return status.equals(OrderStatus.CREATED);
    }

    public void ship () {
        this.status = OrderStatus.SHIPPED;
    }

    public boolean isShipped () {
        return status.equals(OrderStatus.SHIPPED);
    }

    public boolean isRejected () {
        return status.equals(OrderStatus.REJECTED);
    }

    public boolean isApproved () {
        return status.equals(OrderStatus.APPROVED);
    }

    public void validateShip () {
        if (isCreated() || isRejected()) {
            throw new OrderCannotBeShippedException();
        }

        if (isShipped()) {
            throw new OrderCannotBeShippedTwiceException();
        }
    }

    public void add (final Product product, final int quantity) {
        final OrderItem orderItem = new OrderItem(product, quantity);
        getItems().add(orderItem);
    }

    public BigDecimal getTax () {
        BigDecimal taxTotal = BigDecimal.ZERO;
        for (OrderItem item: items){
            taxTotal = taxTotal.add(item.getTax());
        }
        return taxTotal;
    }

    public BigDecimal getTotal () {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item: items){
            total = total.add(item.getTaxedAmount());
        }
        return total;
    }
}
