package by.epam.store.model.service;

public interface OrderService {

	boolean addProductToCart(String productId) throws ServiceException;

}
