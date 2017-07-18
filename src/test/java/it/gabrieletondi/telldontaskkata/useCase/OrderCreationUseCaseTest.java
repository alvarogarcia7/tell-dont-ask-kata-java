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
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OrderCreationUseCaseTest {
    private final TestOrderRepository orderRepository = new TestOrderRepository();
    private Category food = new Category("food") {{
        setTaxPercentage(new BigDecimal("10"));
        assertThat(getName(), is("food"));
    }};;
    List<Product> productList = Arrays.asList(
            new Product() {{
                setName("salad");
                setPrice(new BigDecimal("3.56"));
                setCategory(food);
            }},
            new Product() {{
                setName("tomato");
                setPrice(new BigDecimal("4.65"));
                setCategory(food);
            }});

    private final ProductCatalog productCatalog = new InMemoryProductCatalog(productList);
    private final OrderCreationUseCase useCase = new OrderCreationUseCase(orderRepository, productCatalog);

    @Test
    public void sellMultipleItems() throws Exception {
        SellItemRequest saladRequest = new SellItemRequest("salad", 2);

        SellItemRequest tomatoRequest = new SellItemRequest("tomato", 3);

        final SellItemRequest.SellItemsRequest request = new SellItemRequest.SellItemsRequest();
        request.setRequests(new ArrayList<>());
        request.getRequests().add(saladRequest);
        request.getRequests().add(tomatoRequest);

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
        SellItemRequest.SellItemsRequest request = new SellItemRequest.SellItemsRequest();
        request.setRequests(new ArrayList<>());
        SellItemRequest unknownProductRequest = new SellItemRequest("unknown product", 0);
        request.getRequests().add(unknownProductRequest);

        useCase.run(request);
    }

    private List<OrderItem> values () {
        Product product1 = new Product();
        product1.setName("salad");
        product1.setPrice(new BigDecimal("3.56"));
        product1.setCategory(food);
        final OrderItem element1=new OrderItem(product1, 2);
        element1.setProduct(product1);
        Product product2 = new Product();
        product2.setName("tomato");
        product2.setPrice(new BigDecimal("4.65"));
        product2.setCategory(food);
        final OrderItem element2 = new OrderItem(product2, 3);
        element2.setQuantity(3);
        element2.setProduct(product2);
        return Arrays.asList(element1, element2);
    }
}
