package by.epam.store.entity.builder.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import static by.epam.store.controller.command.ParameterAndAttribute.*;
import by.epam.store.entity.DeliveryMethod;
import by.epam.store.entity.Order;
import by.epam.store.entity.OrderStatus;
import by.epam.store.entity.PaymentMethod;
import by.epam.store.entity.builder.EntityBuilder;

/**
 * The builder is responsible for building order
 * 
 * @author Mariya Kuksar
 */
public class OrderBuilder implements EntityBuilder<Order> {
	private static final OrderBuilder instance = new OrderBuilder();

	private OrderBuilder() {
	}

	/**
	 * Get instance of this class
	 * 
	 * @return {@link OrderBuilder} instance
	 */
	public static OrderBuilder getInstance() {
		return instance;
	}

	@Override
	public Order build(Map<String, String> orderInfo) {
		Order order = new Order();
		order.setOrderId(Long.valueOf(orderInfo.get(ORDER_BASKET_ID)));
		order.setOrderStatus(OrderStatus.PLACED);
		order.setCost(new BigDecimal(orderInfo.get(COST)));
		order.setDateTime(LocalDateTime.now());
		order.setPaymentMethod(
				PaymentMethod.valueOf(orderInfo.get(PAYMENT_METHOD).toUpperCase()));
		order.setDeliveryMethod(
				DeliveryMethod.valueOf(orderInfo.get(DELIVERY_METHOD).toUpperCase()));
		if (order.getDeliveryMethod() == DeliveryMethod.DELIVERY) {
			order.getAddress().setCity(orderInfo.get(CITY));
			order.getAddress().setStreet(orderInfo.get(STREET));
			order.getAddress().setHouse(orderInfo.get(HOUSE));
			order.getAddress().setApartment(orderInfo.get(APARTMENT));
		}
		return order;
	}
}
