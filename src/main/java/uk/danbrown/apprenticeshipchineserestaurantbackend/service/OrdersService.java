package uk.danbrown.apprenticeshipchineserestaurantbackend.service;

import org.springframework.stereotype.Service;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Order;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.repository.OrdersRepository;

import java.util.List;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;

    public OrdersService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public Order insertOrder(Order order) throws FailureInsertingEntityException {
        return ordersRepository.insertOrder(order);
    }

    public List<Order> getOrdersByUserId(String userId) {
        return ordersRepository.getOrdersByUserId(userId);
    }

    public List<Order> getOrders() {
        return ordersRepository.getOrders();
    }
}
