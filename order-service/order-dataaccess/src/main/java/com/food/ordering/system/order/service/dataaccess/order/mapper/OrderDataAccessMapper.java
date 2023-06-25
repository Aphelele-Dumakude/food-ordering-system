package com.food.ordering.system.order.service.dataaccess.order.mapper;

import com.food.ordering.system.domain.valueobject.*;
import com.food.ordering.system.order.service.dataaccess.order.entity.OrderAddressEntity;
import com.food.ordering.system.order.service.dataaccess.order.entity.OrderEntity;
import com.food.ordering.system.order.service.dataaccess.order.entity.OrderItemEntity;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.OrderItem;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.valueobject.OrderItemID;
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import com.food.ordering.system.order.service.domain.valueobject.TrackingID;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderDataAccessMapper {

    public OrderEntity orderToOrderEntity(Order order) {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(order.getId().getValue())
                .customerId(order.getCustomerID().getValue())
                .restaurantId(order.getRestaurantID().getValue())
                .trackingId(order.getTrackingID().getValue())
                .orderAddressEntity(deliveryAddressToAddressEntity(order.getStreetAddress()))
                .price(order.getPrice().getAmount())
                .orderItemEntityList(orderItemsToOrderItemEntities(order.getItems()))
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMessages() != null ?
                        String.join(",", order.getFailureMessages()) : "")
                .build();

        orderEntity.getOrderAddressEntity().setOrder(orderEntity);
        orderEntity.getOrderItemEntityList().forEach(orderItemEntity ->
                orderItemEntity.setOrder(orderEntity));
        return orderEntity;
    }

    public Order orderEntityToOrder(OrderEntity orderEntity) {
        return Order.Builder.builder()
                .orderID(new OrderID(orderEntity.getId()))
                .customerID(new CustomerID(orderEntity.getCustomerId()))
                .restaurantID(new RestaurantID(orderEntity.getRestaurantId()))
                .streetAddress(addressEntityToDeliveryAddress(orderEntity.getOrderAddressEntity()))
                .price(new Money(orderEntity.getPrice()))
                .items(orderItemEntitiesToOrderItems(orderEntity.getOrderItemEntityList()))
                .trackingID(new TrackingID(orderEntity.getTrackingId()))
                .failureMessages(orderEntity.getFailureMessages().isEmpty() ? new ArrayList<>() :
                        new ArrayList<>(Arrays.asList(orderEntity.getFailureMessages()
                                .split(","))))
                .build();
    }

    private List<OrderItem> orderItemEntitiesToOrderItems(List<OrderItemEntity> orderItemEntityList) {
        return orderItemEntityList.stream()
                .map(orderItemEntity -> OrderItem.Builder.builder()
                        .orderItemID(new OrderItemID(orderItemEntity.getId()))
                        .product(new Product(new ProductID(orderItemEntity.getProductId())))
                        .price(new Money(orderItemEntity.getPrice()))
                        .quantity(orderItemEntity.getQuantity())
                        .subTotal(new Money(orderItemEntity.getSubTotal()))
                        .build())
                .collect(Collectors.toList());
    }

    private StreetAddress addressEntityToDeliveryAddress(OrderAddressEntity orderAddressEntity) {
        return new StreetAddress(orderAddressEntity.getId(),
                orderAddressEntity.getStreet(),
                orderAddressEntity.getPostalCode(),
                orderAddressEntity.getCity());
    }

    private List<OrderItemEntity> orderItemsToOrderItemEntities(List<OrderItem> items) {
        return items.stream()
                .map(orderItem -> OrderItemEntity.builder()
                        .id(orderItem.getId().getValue())
                        .productId(orderItem.getProduct().getId().getValue())
                        .price(orderItem.getPrice().getAmount())
                        .quantity(orderItem.getQuantity())
                        .subTotal(orderItem.getSubTotal().getAmount())
                        .build())
                .collect(Collectors.toList());
    }

    private OrderAddressEntity deliveryAddressToAddressEntity(StreetAddress streetAddress) {
        return OrderAddressEntity.builder()
                .id(streetAddress.getId())
                .street(streetAddress.getStreet())
                .postalCode(streetAddress.getPostalCode())
                .city(streetAddress.getCity())
                .build();
    }
}
