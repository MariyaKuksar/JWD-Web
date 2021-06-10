package by.epam.store.entity;

import java.io.Serializable;

/**
 * Describes the entity OrderProductConnection
 * 
 * @author Mariya Kuksar
 */
public class OrderProductConnection implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long orderId;
	private Long productId;
	private int quantityOfProducts;

	/**
	 * Constructs a new OrderProductConnection
	 */
	public OrderProductConnection() {
	}

	/**
	 * Constructs a new OrderProductConnection with the specified order id, product
	 * id and quantity of products
	 * 
	 * @param orderId            {@link Long} order id
	 * @param productId          {@link Long} product id
	 * @param quantityOfProducts quantity of products
	 */
	public OrderProductConnection(Long orderId, Long productId, int quantityOfProducts) {
		this.orderId = orderId;
		this.productId = productId;
		this.quantityOfProducts = quantityOfProducts;
	}

	/**
	 * Constructs a new OrderProductConnection with the specified order id and
	 * product id
	 * 
	 * @param orderId   {@link Long} order id
	 * @param productId {@link Long} product id
	 */
	public OrderProductConnection(Long orderId, Long productId) {
		this.orderId = orderId;
		this.productId = productId;
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

	public int getQuantityOfProducts() {
		return quantityOfProducts;
	}

	public void setQuantityOfProducts(int quantityOfProducts) {
		this.quantityOfProducts = quantityOfProducts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + quantityOfProducts;
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
		if (quantityOfProducts != other.quantityOfProducts)
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
		builder.append(", quantityOfProducts=");
		builder.append(quantityOfProducts);
		builder.append("]");
		return builder.toString();
	}
}
