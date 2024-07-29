package uk.danbrown.apprenticeshipchineserestaurantbackend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.Location;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.MenuItem;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Order;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.OrdersService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrdersControllerTest {

    @Mock
    private OrdersService ordersService;

    private OrdersController ordersController;

    @BeforeEach
    void setUp() {
        ordersController = new OrdersController(ordersService);
    }

    @Test
    void createOrder_givenOrder_shouldCallOrdersService() throws FailureInsertingEntityException {
        when(ordersService.insertOrder(any())).thenReturn(getOrder());

        ResponseEntity<Order> result = ordersController.createOrder(getOrder());

        verify(ordersService).insertOrder(getOrder());
        assertThat(result).isEqualTo(ResponseEntity.status(201).body(getOrder()));
    }

    @Test
    void getOrdersByUserId_givenUsername_shouldCallOrdersService() {
        when(ordersService.getOrdersByUserId(any())).thenReturn(List.of(getOrder(), getOrder2()));

        ResponseEntity<List<Order>> result = ordersController.getOrdersByUserId("dan");

        verify(ordersService).getOrdersByUserId("dan");
        assertThat(result).isEqualTo(ResponseEntity.ok(List.of(getOrder2(), getOrder())));
    }

    @Test
    void getOrders_shouldCallOrdersService() {
        when(ordersService.getOrders()).thenReturn(List.of(getOrder(), getOrder2()));

        ResponseEntity<List<Order>> result = ordersController.getOrders();

        verify(ordersService).getOrders();
        assertThat(result).isEqualTo(ResponseEntity.ok(List.of(getOrder2(), getOrder())));
    }

    private Order getOrder2() {
        return new Order(List.of(new MenuItem("Fried Rice", "Chicken", 10, true)), getAddress(), "dan2", true);
    }

    private Order getOrder() {
        return new Order(List.of(new MenuItem("Fried Rice", "Rice", 5, false)), getAddress(), "dan", false);
    }

    private Location getAddress() {
        return new Location("address1", "address2", "city", "postcode", "country");
    }
}