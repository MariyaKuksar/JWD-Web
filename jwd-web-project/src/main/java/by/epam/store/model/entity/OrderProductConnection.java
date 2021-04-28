package by.epam.store.model.entity;

import java.io.Serializable;

public class OrderProductConnection implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long orderId;
	private Product product;
	private int amountProducts;
	
	public OrderProductConnection() {
	}
	
	public OrderProductConnection(Long orderId, Product product, int amountProducts) {
		this.orderId = orderId;
		this.product = product;
		this.amountProducts = amountProducts;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getAmountProducts() {
		return amountProducts;
	}

	public void setAmountProducts(int amountProducts) {
		this.amountProducts = amountProducts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amountProducts;
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		OrderProductConnection other = (OrderProductConnection) obj;
		if (amountProducts != other.amountProducts)
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderProductConnection [orderId=");
		builder.append(orderId);
		builder.append(", product=");
		builder.append(product);
		builder.append(", amountProducts=");
		builder.append(amountProducts);
		builder.append("]");
		return builder.toString();
	}
}
