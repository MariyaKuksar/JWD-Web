package by.epam.store.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

/**
 * Describes the entity Order
 * 
 * @author Mariya Kuksar
 */
public class Order implements Serializable {
	
	private Long orderId;
	private Long userId;
	private OrderStatus orderStatus;
	private Map<Product, Integer> products;
	private BigDecimal cost;
	private LocalDateTime dateTime;
	private PaymentMethod paymentMethod;
	private DeliveryMethod deliveryMethod;
	private Address address = new Address();

	/**
	 * Constructs a new Order
	 */
	public Order() {
	}

	/**
	 * Constructs a new Order with the specified user id
	 * 
	 * @param userId {@link Long} user id
	 */
	public Order(Long userId) {
		this.userId = userId;
	}

	/**
	 * Constructs a new Order with the specified order id and user id
	 * 
	 * @param orderId {@link Long} order id
	 * @param userId  {@link Long} user id
	 */
	public Order(Long orderId, Long userId) {
		this.orderId = orderId;
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
		return Collections.unmodifiableMap(products);
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

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		result = prime * result + ((dateTime == null) ? 0 : dateTime.hashCode());
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
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (!cost.equals(other.cost))
			return false;
		if (dateTime == null) {
			if (other.dateTime != null)
				return false;
		} else if (!dateTime.equals(other.dateTime))
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
		builder.append(", dateTime=");
		builder.append(dateTime);
		builder.append(", paymentMethod=");
		builder.append(paymentMethod);
		builder.append(", deliveryMethod=");
		builder.append(deliveryMethod);
		builder.append(", address=");
		builder.append(address);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * Describes the entity Address
	 * 
	 * @author Mariya Kuksar
	 */
	public class Address implements Serializable {
		private static final long serialVersionUID = 1L;
		private String city;
		private String street;
		private String house;
		private String apartment;

		public Address() {
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getStreet() {
			return street;
		}

		public void setStreet(String street) {
			this.street = street;
		}

		public String getHouse() {
			return house;
		}

		public void setHouse(String house) {
			this.house = house;
		}

		public String getApartment() {
			return apartment;
		}

		public void setApartment(String apartment) {
			this.apartment = apartment;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((apartment == null) ? 0 : apartment.hashCode());
			result = prime * result + ((city == null) ? 0 : city.hashCode());
			result = prime * result + ((house == null) ? 0 : house.hashCode());
			result = prime * result + ((street == null) ? 0 : street.hashCode());
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
			Address other = (Address) obj;
			if (apartment == null) {
				if (other.apartment != null)
					return false;
			} else if (!apartment.equals(other.apartment))
				return false;
			if (city == null) {
				if (other.city != null)
					return false;
			} else if (!city.equals(other.city))
				return false;
			if (house == null) {
				if (other.house != null)
					return false;
			} else if (!house.equals(other.house))
				return false;
			if (street == null) {
				if (other.street != null)
					return false;
			} else if (!street.equals(other.street))
				return false;
			return true;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Address [city=");
			builder.append(city);
			builder.append(", street=");
			builder.append(street);
			builder.append(", house=");
			builder.append(house);
			builder.append(", apartment=");
			builder.append(apartment);
			builder.append("]");
			return builder.toString();
		}
	}
}
