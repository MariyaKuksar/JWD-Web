package by.epam.store.entity.comparator;

import java.util.Comparator;

import by.epam.store.entity.Product;

public enum ProductComparator {
	
	INCREASE_PRICE((p1, p2) -> p1.getPrice().compareTo(p2.getPrice())),
	DECREASE_PRICE((p1, p2) -> p2.getPrice().compareTo(p1.getPrice()));

	private Comparator<Product> comporator;

	ProductComparator(Comparator<Product> comporator) {
		this.comporator = comporator;
	}

	public Comparator<Product> getComporator() {
		return comporator;
	}
}
