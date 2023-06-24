package com.food.ordering.system.order.service.domain.mapper;

import com.food.ordering.system.domain.valueobject.CustomerID;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.ProductID;
import com.food.ordering.system.domain.valueobject.RestaurantID;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.create.OrderAddress;
import com.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.OrderItem;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {

    public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand) {
        return Restaurant.Builder.builder()
                .restaurantID(new RestaurantID(createOrderCommand.getRestaurantID()))
                .products(createOrderCommand.getItems().stream().map(orderItem ->
                        new Product(new ProductID(orderItem.getProductID())))
                                .collect(Collectors.toList()))
                .build();
    }
    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.Builder.builder()
                .customerID(new CustomerID(createOrderCommand.getCustomerID()))
                .restaurantID(new RestaurantID(createOrderCommand.getRestaurantID()))
                .streetAddress(orderAddressToStreetAddress(createOrderCommand.getOrderAddress()))
                .price(new Money(createOrderCommand.getPrice()))
                .items(orderItemsTpOrderItemsEntities(createOrderCommand.getItems()))
                .build();
    }
    public CreateOrderResponse orderToCreateOrderResponse(Order order, String message) {
        return CreateOrderResponse.builder()
                .orderTrackingID(order.getTrackingID().getValue())
                .orderStatus(order.getOrderStatus())
                .message(message)
                .build();
    }

    public TrackOrderResponse orderToTrackOrderResponse(Order order) {
        return TrackOrderResponse.builder()
                .orderTrackingID(order.getTrackingID().getValue())
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMessages())
                .build();
    }

    private List<OrderItem> orderItemsTpOrderItemsEntities(List<com.food.ordering.system.order.service.domain.dto.create.OrderItem> items) {
        return items.stream()
                .map(orderItem ->
                                OrderItem.Builder.builder()
                                        .product(new Product(new ProductID(orderItem.getProductID())))
                                        .price(new Money(orderItem.getPrice()))
                                        .quantity(orderItem.getQuantity())
                                        .subTotal(new Money(orderItem.getSubTotal()))
                                        .build()).collect(Collectors.toList());
    }

    private StreetAddress orderAddressToStreetAddress(OrderAddress orderAddress) {
        return new StreetAddress(
                UUID.randomUUID(),
                orderAddress.getStreet(),
                orderAddress.getPostalCode(),
                orderAddress.getCity()
        );
    }
}
