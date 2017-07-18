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
    private BigDecimal total;
    private String currency;
    private List<OrderItem> items;
    private BigDecimal tax;
    private OrderStatus status;
    private int id;

    public Order () {
        this.status = OrderStatus.CREATED;
        this.currency = "EUR";
        this.total = new BigDecimal("0.00");
        this.tax = new BigDecimal("0.00");
        this.items = new ArrayList<>();
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getCurrency() {
        return currency;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public BigDecimal getTax() {
        return tax;
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
        if (getStatus().equals(CREATED) || getStatus().equals(REJECTED)) {
            throw new OrderCannotBeShippedException();
        }

        if (getStatus().equals(SHIPPED)) {
            throw new OrderCannotBeShippedTwiceException();
        }
    }

    public void add (final Product product, final int quantity) {
        final OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);
        final BigDecimal unitaryTax = product.getPrice().divide(valueOf(100)).multiply(product.getCategory().getTaxPercentage()).setScale(2, HALF_UP);
        final BigDecimal taxAmount = unitaryTax.multiply(valueOf(quantity));
        orderItem.setTax(taxAmount);
        final BigDecimal unitaryTaxedAmount = product.getPrice().add(unitaryTax).setScale(2, HALF_UP);
        final BigDecimal taxedAmount = unitaryTaxedAmount.multiply(valueOf(quantity)).setScale(2, HALF_UP);
        orderItem.setTaxedAmount(taxedAmount);
        getItems().add(orderItem);

        this.total = getTotal().add(taxedAmount);
        this.tax = getTax().add(taxAmount);
    }
}
