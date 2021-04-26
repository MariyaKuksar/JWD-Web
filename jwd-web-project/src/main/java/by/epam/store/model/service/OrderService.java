package by.epam.store.model.service;

public interface OrderService {

	boolean addProduct(String userId, String productId) throws ServiceException;

}
