package by.epam.store.model.entity.builder;

import java.util.Map;

import by.epam.store.model.entity.Order;


public class OrderBuilder implements EntityBuilder<Order> {
	private static final OrderBuilder instance = new OrderBuilder();

	private OrderBuilder() {
	}

	public static OrderBuilder getInstance() {
		return instance;
	}

	@Override
	public Order build(Map<String, String> entityInfo) {
		// TODO Auto-generated method stub
		return null;
	}
}
