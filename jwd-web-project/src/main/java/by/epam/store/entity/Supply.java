package by.epam.store.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

/**
 * Describes the entity Supply
 * 
 * @author Mariya Kuksar
 */
public class Supply implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long supplyId;
	private LocalDateTime dateTime;
	private Map<Product, Integer> suppliedProducts;

	/**
	 * Constructs a new Supply
	 */
	public Supply() {
	}

	/**
	 * Constructs a new Supply with the specified date and time, supplied products
	 * 
	 * @param dateTime         {@link LocalDateTime} date and time of supply
	 * @param suppliedProducts {@link Map} of {@link Product} and {@link Integer}
	 *                         supplied products and its quantity
	 */
	public Supply(LocalDateTime dateTime, Map<Product, Integer> suppliedProducts) {
		this.dateTime = dateTime;
		this.suppliedProducts = suppliedProducts;
	}

	public Long getSupplyId() {
		return supplyId;
	}

	public void setSupplyId(Long supplyId) {
		this.supplyId = supplyId;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public Map<Product, Integer> getSuppliedProducts() {
		return Collections.unmodifiableMap(suppliedProducts);
	}

	public void setSuppliedProducts(Map<Product, Integer> suppliedProducts) {
		this.suppliedProducts = suppliedProducts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateTime == null) ? 0 : dateTime.hashCode());
		result = prime * result + ((suppliedProducts == null) ? 0 : suppliedProducts.hashCode());
		result = prime * result + ((supplyId == null) ? 0 : supplyId.hashCode());
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
		Supply other = (Supply) obj;
		if (dateTime == null) {
			if (other.dateTime != null)
				return false;
		} else if (!dateTime.equals(other.dateTime))
			return false;
		if (suppliedProducts == null) {
			if (other.suppliedProducts != null)
				return false;
		} else if (!suppliedProducts.equals(other.suppliedProducts))
			return false;
		if (supplyId == null) {
			if (other.supplyId != null)
				return false;
		} else if (!supplyId.equals(other.supplyId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Supply [supplyId=");
		builder.append(supplyId);
		builder.append(", dateTime=");
		builder.append(dateTime);
		builder.append(", suppliedProducts=");
		builder.append(suppliedProducts);
		builder.append("]");
		return builder.toString();
	}
}
