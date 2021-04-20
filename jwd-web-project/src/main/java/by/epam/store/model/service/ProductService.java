package by.epam.store.model.service;

import java.util.List;

import by.epam.store.model.entity.Product;
import by.epam.store.model.entity.ProductCategory;

public interface ProductService {

	List<ProductCategory> findAllProductCategories() throws ServiceException;

	List<Product> findProductsFromCategory(String categoryId) throws ServiceException;
}
