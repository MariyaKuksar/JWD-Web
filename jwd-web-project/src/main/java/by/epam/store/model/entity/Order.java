package by.epam.store.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long orderId;
	private Long userId;
	private OrderStatus orderStatus;
	private Map<Product, Integer> products;
	private BigDecimal cost;
	private LocalDateTime dataTime;
	private PaymentMethod paymentMethod;
	private DeliveryMethod deliveryMethod;

	public Order() {
	}

	public Order(Long userId) {
		this.userId = userId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Map<Product, Integer> getProducts() {
		return products;
	}

	public void setProducts(Map<Product, Integer> products) {
		this.products = products;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public LocalDateTime getDataTime() {
		return dataTime;
	}

	public void setDataTime(LocalDateTime dataTime) {
		this.dataTime = dataTime;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public DeliveryMethod getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		result = prime * result + ((dataTime == null) ? 0 : dataTime.hashCode());
		result = prime * result + ((deliveryMethod == null) ? 0 : deliveryMethod.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((orderStatus == null) ? 0 : orderStatus.hashCode());
		result = prime * result + ((paymentMethod == null) ? 0 : paymentMethod.hashCode());
		result = prime * result + ((products == null) ? 0 : products.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (!cost.equals(other.cost))
			return false;
		if (dataTime == null) {
			if (other.dataTime != null)
				return false;
		} else if (!dataTime.equals(other.dataTime))
			return false;
		if (deliveryMethod != other.deliveryMethod)
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (orderStatus != other.orderStatus)
			return false;
		if (paymentMethod != other.paymentMethod)
			return false;
		if (products == null) {
			if (other.products != null)
				return false;
		} else if (!products.equals(other.products))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Order [orderId=");
		builder.append(orderId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", orderStatus=");
		builder.append(orderStatus);
		builder.append(", products=");
		builder.append(products);
		builder.append(", cost=");
		builder.append(cost);
		builder.append(", dataTime=");
		builder.append(dataTime);
		builder.append(", paymentMethod=");
		builder.append(paymentMethod);
		builder.append(", deliveryMethod=");
		builder.append(deliveryMethod);
		builder.append("]");
		return builder.toString();
	}
}
