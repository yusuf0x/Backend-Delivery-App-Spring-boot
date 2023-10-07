package com.delivery.app.controllers;

import com.delivery.app.models.Order;
import com.delivery.app.models.OrderDetail;
import com.delivery.app.payload.request.NewOrderRequest;
import com.delivery.app.payload.response.ApiResponse;
import com.delivery.app.payload.response.ApiResponseWithData;
import com.delivery.app.services.OrderService;
import com.delivery.app.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/by-client/{clientId}")
    public ResponseEntity<?> getListOrdersForClient(@PathVariable Long clientId) {
        try {
            List<Order> orders = orderService.getListOrdersForClient(clientId);
            return ResponseEntity.ok(new ApiResponseWithData<>(true, "List orders for client", orders));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    @PostMapping("/add-new")
    public ResponseEntity<?> addNewOrders(@RequestBody NewOrderRequest newOrderRequest) {
        try {
            orderService.addNewOrder(newOrderRequest);
            return ResponseEntity.ok(new ApiResponse(true, "New Order added successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    @GetMapping("/by-status/{statusOrder}")
    public ResponseEntity<?> getOrdersByStatus(@PathVariable String statusOrder) {
        try {
            List<Order> orders = orderService.getOrdersByStatus(statusOrder);
            return ResponseEntity.ok(new ApiResponseWithData<>(true, "Orders by " + statusOrder, orders));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    @GetMapping("/details-order/{orderDetailsId}")
    public ResponseEntity<?> getDetailsOrderById(@PathVariable Long orderDetailsId) {
        try {
            OrderDetail orderDetails = orderService.getOrderDetailsById(orderDetailsId);
            return ResponseEntity.ok(new ApiResponseWithData<>(true, "Order details by " + orderDetailsId, orderDetails));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    @PutMapping("/{orderId}/update-status-to-dispatched/")
    public ResponseEntity<?> updateStatusToDispatched(@PathVariable Long orderId,@RequestParam Long deliveryId) {
        try {
            orderService.updateOrderStatusToDispatched(deliveryId, orderId);
            return ResponseEntity.ok(new ApiResponse(true, "Order DISPATCHED"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    @GetMapping("/by-delivery/{statusOrder}")
    public ResponseEntity<?> getOrdersByDelivery(@PathVariable String statusOrder,@RequestParam Long deliveryId) {
        try {
            List<Order> orders = orderService.getOrdersByDeliveryAndStatus(statusOrder,deliveryId);
            return ResponseEntity.ok(new ApiResponseWithData<>(true, "All Orders By Delivery", orders));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    @PutMapping("/{orderId}/update-status-order-on-way/")
    public ResponseEntity<?> updateStatusToOntheWay(@PathVariable Long orderId, @RequestParam String latitude ,@RequestParam String longitude) {
        try {
            orderService.updateOrderStatusToOntheWay(orderId, latitude,longitude);
            return ResponseEntity.ok(new ApiResponse(true, "ON WAY"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    @PutMapping("/{orderId}/update-status-order-delivered")
    public ResponseEntity<?> updateStatusToDelivered(@PathVariable Long orderId) {
        try {
            orderService.updateOrderStatusToDelivered(orderId);
            return ResponseEntity.ok(new ApiResponse(true, "ORDER DELIVERED"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

}
