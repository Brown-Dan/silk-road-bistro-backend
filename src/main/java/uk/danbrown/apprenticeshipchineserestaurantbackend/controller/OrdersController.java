package uk.danbrown.apprenticeshipchineserestaurantbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Order;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.OrdersService;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping()
    public ResponseEntity<Order> createOrder(@RequestBody Order order) throws FailureInsertingEntityException {
        return ResponseEntity.status(201).body(ordersService.insertOrder(order));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(ordersService.getOrdersByUserId(userId).reversed());
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok(ordersService.getOrders().reversed());
    }
}
