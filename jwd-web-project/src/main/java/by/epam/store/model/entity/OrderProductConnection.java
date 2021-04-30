package by.epam.store.model.entity;

import java.io.Serializable;

public class OrderProductConnection implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long orderId;
	private Long productId;
	private int amountProducts;
	
	public OrderProductConnection() {
	}

	public OrderProductConnection(Long orderId, Long productId, int amountProducts) {
		this.orderId = orderId;
		this.productId = productId;
		this.amountProducts = amountProducts;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
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
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderProductConnection [orderId=");
		builder.append(orderId);
		builder.append(", productId=");
		builder.append(productId);
		builder.append(", amountProducts=");
		builder.append(amountProducts);
		builder.append("]");
		return builder.toString();
	}
}
