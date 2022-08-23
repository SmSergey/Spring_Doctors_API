package com.app.domain.order;

import com.app.domain.order.dto.CreateOrderRequest;
import com.app.domain.order.interfaces.OrderRepository;
import com.app.utils.messages.ResponseMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @PostMapping("/addNewOrder")
    public Order createOrder(@RequestBody CreateOrderRequest req) {
        return orderService.createOrder(req);
    }

    @PatchMapping("/updateUserOrder")
    public String updateOrder() {
        return ResponseMessages.ORDER_UPDATED;
    }

    @DeleteMapping("deleteUsersOrder")
    public String deleteOrder(@RequestParam("id") String id) {
        orderRepository.deleteById(Long.valueOf(id));
        return ResponseMessages.ORDER_DELETED;
    }
}
