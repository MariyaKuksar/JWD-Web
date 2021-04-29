package by.epam.store.model.entity.builder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import by.epam.store.model.entity.Order;
import by.epam.store.model.entity.OrderStatus;
import by.epam.store.util.ColumnName;

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
//пока не использую, доработать
	@Override
	public Order build(ResultSet resultSet) throws SQLException {	
			Order order = new Order();
			order.setOrderId(resultSet.getLong(ColumnName.ORDERS_ID));
			order.setUserId(resultSet.getLong(ColumnName.ORDERS_USER_ID));
			order.setDataTime(resultSet.getTimestamp(ColumnName.ORDERS_DATA_TIME).toLocalDateTime());
			order.setOrderStatus(OrderStatus.valueOf(resultSet.getString(ColumnName.ORDERS_STATUS)));	
		return order;
	}
}
