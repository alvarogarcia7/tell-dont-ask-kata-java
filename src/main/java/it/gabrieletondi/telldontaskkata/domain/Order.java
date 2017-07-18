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

public class Order {
    private BigDecimal total;
    private String currency;
    private List<OrderItem> items;
    private BigDecimal tax;
    private OrderStatus status;
    private int id;

    public Order () {
        this.status = OrderStatus.CREATED;
        this.setCurrency("EUR");
        this.setTotal(new BigDecimal("0.00"));
        this.setTax(new BigDecimal("0.00"));
        this.setItems(new ArrayList<>());
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
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
        setStatus(OrderStatus.SHIPPED);
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
        if (getStatus().equals(CREATED) || getStatus().equals(REJECTED)) {
            throw new OrderCannotBeShippedException();
        }

        if (getStatus().equals(SHIPPED)) {
            throw new OrderCannotBeShippedTwiceException();
        }
    }
}
