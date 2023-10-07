package com.delivery.app.services;

import com.delivery.app.exceptions.AddressNotFoundException;
import com.delivery.app.exceptions.OrderDetailNotFoundException;
import com.delivery.app.exceptions.OrderNotFoundException;
import com.delivery.app.models.*;
import com.delivery.app.payload.request.NewOrderRequest;
import com.delivery.app.repositories.AddressRepo;
import com.delivery.app.repositories.OrderDetailsRepo;
import com.delivery.app.repositories.OrderRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final UserService userService;
    private final AddressRepo addressRepo;
    private final ProductService productService;
    private final OrderDetailsRepo orderDetailsRepo;
    public OrderService(OrderRepo orderRepo, UserService userService, AddressRepo addressRepo, ProductService productService, OrderDetailsRepo orderDetailsRepo) {
        this.orderRepo = orderRepo;
        this.userService = userService;
        this.addressRepo = addressRepo;
        this.productService = productService;
        this.orderDetailsRepo = orderDetailsRepo;
    }
    public List<Order> getListOrdersForClient(Long clientId){
        User client = userService.findById(clientId);
        return orderRepo.findByClient(client);
    }

    public void addNewOrder(NewOrderRequest request) {
        User user = userService.findById(request.getClientId());
        Address address = addressRepo.findById(request.getAddressId()).orElseThrow(
                () -> new AddressNotFoundException("Address Not Found with id = "+request.getAddressId())
        );
        Order order = new Order();
        order.setClient(user);
        order.setAddress(address);
        order.setAmount(request.getTotal());
        order.setPayType(request.getTypePayment());
        Order savedOrder = orderRepo.save(order);
        for (Long id : request.getProductIds()) {
            Product product = productService.getProductById(id);
            OrderDetail orderDetails = new OrderDetail();
            orderDetails.setOrder(savedOrder);
            orderDetails.setProduct(product);
            orderDetails.setQuantity(1);
//            orderDetails.setPrice(Quantity() * product.getPrice());
            orderDetailsRepo.save(orderDetails);
        }
    }
    public List<Order> getOrdersByStatus(String statusOrder) {
        return orderRepo.findByStatus(statusOrder);
    }
    public OrderDetail getOrderDetailsById(Long idOrderDetails) {
        return orderDetailsRepo.findById(idOrderDetails).orElseThrow(
                () -> new OrderDetailNotFoundException("OrderDetail Not Found with id = "+idOrderDetails)
        );
    }
    public void updateOrderStatusToDispatched(Long deliveryId, Long orderId) {
        User delivery = userService.findById(deliveryId);
        Order order = getOrderById(orderId);
        order.setStatus("DISPATCHED");
        order.setDelivery(delivery);
        orderRepo.save(order);

    }
    public Order getOrderById(Long id){
        return orderRepo.findById(id).orElseThrow(
                () -> new OrderNotFoundException("Order Not Found with id ="+id)
        );
    }
    public List<Order> getOrdersByDeliveryAndStatus(String statusOrder, Long deliveryId) {
        User delivery = userService.findById(deliveryId);
        return orderRepo.findByDeliveryAndStatus(delivery, statusOrder);
    }

    public void updateOrderStatusToOntheWay(Long orderId, String latitude, String longitude) {
        Order order = getOrderById(orderId);
        order.setStatus("ON WAY");
        order.setLatitude(latitude);
        order.setLongitude(longitude);
        orderRepo.save(order);
    }
    public void updateOrderStatusToDelivered(Long orderId) {
        Order order = getOrderById(orderId);
        order.setStatus("DELIVERED");
        orderRepo.save(order);

    }

}
