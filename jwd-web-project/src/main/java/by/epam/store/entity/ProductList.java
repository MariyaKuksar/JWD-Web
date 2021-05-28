package by.epam.store.entity;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class ProductList implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<Product> products;
	private int currentPageNumber;
	private int numberOfPages;
	
	public ProductList() {
	}

	public List<Product> getProducts() {
		return Collections.unmodifiableList(products);
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public int getCurrentPageNumber() {
		return currentPageNumber;
	}

	public void setCurrentPageNumber(int currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}

	public int getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + currentPageNumber;
		result = prime * result + numberOfPages;
		result = prime * result + ((products == null) ? 0 : products.hashCode());
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
		ProductList other = (ProductList) obj;
		if (currentPageNumber != other.currentPageNumber)
			return false;
		if (numberOfPages != other.numberOfPages)
			return false;
		if (products == null) {
			if (other.products != null)
				return false;
		} else if (!products.equals(other.products))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductList [products=");
		builder.append(products);
		builder.append(", currentPageNumber=");
		builder.append(currentPageNumber);
		builder.append(", numberOfPages=");
		builder.append(numberOfPages);
		builder.append("]");
		return builder.toString();
	}
}
