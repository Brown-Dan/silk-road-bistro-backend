package uk.danbrown.apprenticeshipchineserestaurantbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Order;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.OrdersService;

import java.util.List;

@RestController
@RequestMapping("orders")
public class OrdersController {

    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    public ResponseEntity<Order> createOrder(Order order) throws FailureInsertingEntityException {
        return ResponseEntity.status(201).body(ordersService.insertOrder(order));
    }

    public ResponseEntity<Order> getOrdersByUserId(@RequestParam String userId) {
        return ResponseEntity.ok(ordersService.getOrdersByUserId(userId));
    }

    public ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok(ordersService.getOrders());
    }
}
