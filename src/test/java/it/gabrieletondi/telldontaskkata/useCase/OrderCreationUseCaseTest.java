package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Category;
import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.domain.OrderItem;
import it.gabrieletondi.telldontaskkata.domain.OrderStatus;
import it.gabrieletondi.telldontaskkata.domain.Product;
import it.gabrieletondi.telldontaskkata.doubles.InMemoryProductCatalog;
import it.gabrieletondi.telldontaskkata.doubles.TestOrderRepository;
import it.gabrieletondi.telldontaskkata.exception.UnknownProductException;
import it.gabrieletondi.telldontaskkata.repository.ProductCatalog;
import it.gabrieletondi.telldontaskkata.useCase.SellItemRequest.SellItemsRequest;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.gabrieletondi.telldontaskkata.domain.Product.aNew;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OrderCreationUseCaseTest {
    private final TestOrderRepository orderRepository = new TestOrderRepository();
    private Category food = new Category("food") {{
        setTaxPercentage(new BigDecimal("10"));
        assertThat(getName(), is("food"));
    }};;
    List<Product> productList = Arrays.asList(
            Product.aNew("salad", new BigDecimal("3.56"), food),
            Product.aNew("tomato", new BigDecimal("4.65"), food));

    private final ProductCatalog productCatalog = new InMemoryProductCatalog(productList);
    private final OrderCreationUseCase useCase = new OrderCreationUseCase(orderRepository, productCatalog);

    @Test
    public void sellMultipleItems() throws Exception {
        SellItemRequest saladRequest = new SellItemRequest("salad", 2);
        SellItemRequest tomatoRequest = new SellItemRequest("tomato", 3);
        final SellItemsRequest request = SellItemsRequest.of(saladRequest, tomatoRequest);

        useCase.run(request);

        final Order insertedOrder = orderRepository.getSavedOrder();
        assertThat(insertedOrder.getStatus(), is(OrderStatus.CREATED));
        assertThat(insertedOrder.getTotal(), is(new BigDecimal("23.20")));
        assertThat(insertedOrder.getTax(), is(new BigDecimal("2.13")));
        assertThat(insertedOrder.getCurrency(), is("EUR"));
        assertThat(insertedOrder.getItems(), is(values()));
    }

    @Test(expected = UnknownProductException.class)
    public void unknownProduct() throws Exception {
        SellItemRequest unknownProductRequest = new SellItemRequest("unknown product", 0);
        SellItemsRequest request = SellItemsRequest.of(unknownProductRequest);

        useCase.run(request);
    }

    private List<OrderItem> values () {
        Product product1 = aNew("salad", new BigDecimal("3.56"), food);
        final OrderItem element1=new OrderItem(product1, 2);
        Product product2 = aNew("tomato", new BigDecimal("4.65"), food);
        final OrderItem element2 = new OrderItem(product2, 3);
        return Arrays.asList(element1, element2);
    }
}
