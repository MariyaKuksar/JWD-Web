package by.epam.store.model.entity.builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import by.epam.store.model.entity.DeliveryMethod;
import by.epam.store.model.entity.Order;
import by.epam.store.model.entity.OrderStatus;
import by.epam.store.model.entity.PaymentMethod;
import by.epam.store.util.ParameterAndAttribute;

public class OrderBuilder implements EntityBuilder<Order> {
	private static final OrderBuilder instance = new OrderBuilder();

	private OrderBuilder() {
	}

	public static OrderBuilder getInstance() {
		return instance;
	}

	@Override
	public Order build(Map<String, String> orderInfo) {
		Order order = new Order();
		order.setOrderId(Long.parseLong(orderInfo.get(ParameterAndAttribute.ORDER_BASKET_ID)));
		order.setOrderStatus(OrderStatus.PLACED);
		order.setCost(new BigDecimal(orderInfo.get(ParameterAndAttribute.COST)));
		order.setDataTime(LocalDateTime.now()); //TODO норм это использовать для текущей даты?
		order.setPaymentMethod(PaymentMethod.valueOf(orderInfo.get(ParameterAndAttribute.PAYMENT_METHOD)));
		order.setDeliveryMethod(DeliveryMethod.valueOf(orderInfo.get(ParameterAndAttribute.DELIVERY_METHOD)));
		if (order.getDeliveryMethod()==DeliveryMethod.DELIVERY) {
			order.getAddress().setCity(orderInfo.get(ParameterAndAttribute.CITY));
			order.getAddress().setStreet(orderInfo.get(ParameterAndAttribute.STREET));
			order.getAddress().setHouse(orderInfo.get(ParameterAndAttribute.HOUSE));
			order.getAddress().setApartment(orderInfo.get(ParameterAndAttribute.APARTMENT));
		}
		return order;
	}
}
