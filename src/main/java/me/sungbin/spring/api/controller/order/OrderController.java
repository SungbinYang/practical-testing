package me.sungbin.spring.api.controller.order;

import lombok.RequiredArgsConstructor;
import me.sungbin.spring.api.controller.order.request.OrderCreateRequest;
import me.sungbin.spring.api.service.order.OrderService;
import me.sungbin.spring.api.service.order.response.OrderResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/v1/orders/new")
    public OrderResponse createOrder(@RequestBody OrderCreateRequest request) {
        LocalDateTime registeredDateTime = LocalDateTime.now();
        return orderService.createOrder(request, registeredDateTime);
    }
}
