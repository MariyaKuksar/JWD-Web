package by.epam.store.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Basket implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long orderBasketId;
	private Long userId;
	private Map<Product, Integer> products;
	private BigDecimal cost;
	private List<DeliveryMethod> deliveryMethodList;
	private List<PaymentMethod> paymentMethodList;
	
	public Basket() {
	}

	public Basket(Long orderBasketId, Long userId) {
		this.orderBasketId = orderBasketId;
		this.userId = userId;
	}

	public Long getOrderBasketId() {
		return orderBasketId;
	}

	public void setOrderBasketId(Long orderBasketId) {
		this.orderBasketId = orderBasketId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public List<DeliveryMethod> getDeliveryMethodList() {
		return Collections.unmodifiableList(deliveryMethodList);
	}

	public void setDeliveryMethodList(List<DeliveryMethod> deliveryMethodList) {
		this.deliveryMethodList = deliveryMethodList;
	}

	public List<PaymentMethod> getPaymentMethodList() {
		return Collections.unmodifiableList(paymentMethodList);
	}

	public void setPaymentMethodList(List<PaymentMethod> paymentMethodList) {
		this.paymentMethodList = paymentMethodList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Basket [orderBasketId=");
		builder.append(orderBasketId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", products=");
		builder.append(products);
		builder.append(", cost=");
		builder.append(cost);
		builder.append(", deliveryMethodList=");
		builder.append(deliveryMethodList);
		builder.append(", paymentMethodList=");
		builder.append(paymentMethodList);
		builder.append("]");
		return builder.toString();
	}
}
