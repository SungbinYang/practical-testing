package me.sungbin.spring.api.service.order;

import lombok.RequiredArgsConstructor;
import me.sungbin.spring.api.controller.order.request.OrderCreateRequest;
import me.sungbin.spring.api.service.order.response.OrderResponse;
import me.sungbin.spring.domain.order.Order;
import me.sungbin.spring.domain.order.OrderRepository;
import me.sungbin.spring.domain.product.Product;
import me.sungbin.spring.domain.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        Order order = Order.create(products, registeredDateTime);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }
}
