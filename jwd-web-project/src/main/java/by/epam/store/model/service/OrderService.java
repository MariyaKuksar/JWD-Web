package by.epam.store.model.service;

public interface OrderService {

	boolean addProductToOrder(String productId) throws ServiceException;

}
