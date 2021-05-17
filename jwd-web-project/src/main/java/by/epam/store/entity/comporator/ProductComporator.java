package by.epam.store.entity.comporator;

import java.util.Comparator;

import by.epam.store.entity.Product;

public enum ProductComporator implements Comparator<Product>{
	INCREASE_PRICE {
		@Override
		public int compare(Product product1, Product product2) {
			return product1.getPrice().compareTo(product2.getPrice());
		}
	},
	
	DECREASE_PRICE {
		@Override
		public int compare(Product product1, Product product2) {
			return product2.getPrice().compareTo(product1.getPrice());
		}	
	}
}
