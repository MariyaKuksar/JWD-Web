package by.epam.store.model.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import by.epam.store.entity.DeliveryMethod;
import by.epam.store.entity.Order;
import by.epam.store.entity.OrderProductConnection;
import by.epam.store.entity.OrderStatus;
import by.epam.store.entity.PaymentMethod;
import by.epam.store.entity.Product;
import by.epam.store.entity.UserRole;
import by.epam.store.model.dao.DaoException;
import by.epam.store.model.dao.OrderDao;
import by.epam.store.model.dao.OrderProductConnectionDao;
import by.epam.store.model.dao.ProductDao;
import by.epam.store.model.service.InvalidDataException;
import by.epam.store.model.service.ServiceException;

public class OrderServiceImplTest {
	@Mock
	private OrderDao orderDao;
	@Mock
	private OrderProductConnectionDao orderProductConnectionDao;
	@Mock
	private ProductDao productDao;
	private AutoCloseable autoCloseable;
	private Map<String, String> orderInfo;
	private Map<Product, Integer> products;
	Order order;

	@InjectMocks
	OrderServiceImpl orderService;

	@BeforeClass
	public void setUp() {
		autoCloseable = MockitoAnnotations.openMocks(this);
		orderInfo = new HashMap<>();
		orderInfo.put("orderBasketId", "1");
		orderInfo.put("cost", "100");
		orderInfo.put("paymentMethod", "online");
		orderInfo.put("deliveryMethod", "pickup");
		products = new HashMap<>();
		Product product = new Product();
		product.setProductId(1L);
		product.setCategoryId(1L);
		product.setProductName("Desk TRIO");
		product.setImageName("desk.jpg");
		product.setPrice(new BigDecimal(320));
		product.setQuantity(10);
		products.put(product, 1);
		order = new Order(1L, 1L);
		order.setDateTime(LocalDateTime.of(2018, 05, 15, 16, 50));
		order.setDeliveryMethod(DeliveryMethod.PICKUP);
		order.setPaymentMethod(PaymentMethod.CASH);
		order.setOrderStatus(OrderStatus.PLACED);
		order.setCost(new BigDecimal(320));
		order.setProducts(products);
	}

	@AfterClass
	public void tierDown() throws Exception {
		autoCloseable.close();
	}

	@Test
	public void addProductToBasketPositiveTest() throws DaoException, ServiceException {
		Optional<Order> expected = Optional.of(new Order(1L, 1L));
		when(orderProductConnectionDao.increaseQuantityOfProduct(isA(OrderProductConnection.class))).thenReturn(false);
		doNothing().when(orderProductConnectionDao).create(isA(OrderProductConnection.class));
		Optional<Order> actual = orderService.addProductToBasket(1L, 1L, "1");
		Assert.assertEquals(actual, expected);
	}

	@Test
	public void addProductToBasketNegativeTest() throws ServiceException {
		Optional<Order> expected = Optional.empty();
		Optional<Order> actual = orderService.addProductToBasket(null, 1L, "1");
		Assert.assertEquals(actual, expected);
	}

	@Test(expectedExceptions = ServiceException.class)
	public void addProductToBasketServiceExceptionTest() throws DaoException, ServiceException {
		when(orderProductConnectionDao.increaseQuantityOfProduct(isA(OrderProductConnection.class)))
				.thenThrow(DaoException.class);
		orderService.addProductToBasket(1L, 1L, "1");
	}

	@Test
	public void changeQuantityOfProductInOrderPositiveTest() throws ServiceException, DaoException {
		when(orderProductConnectionDao.update(isA(OrderProductConnection.class))).thenReturn(true);
		Assert.assertTrue(orderService.changeQuantityOfProductInOrder(1L, "1", "2"));
	}

	@Test
	public void changeQuantityOfProductInOrderNegativeTest() throws ServiceException {
		Assert.assertFalse(orderService.changeQuantityOfProductInOrder(null, "1", "2"));
	}

	@Test(expectedExceptions = ServiceException.class)
	public void changeQuantityOfProductInOrderServiceExceptionTest() throws ServiceException, DaoException {
		when(orderProductConnectionDao.update(isA(OrderProductConnection.class))).thenThrow(DaoException.class);
		orderService.changeQuantityOfProductInOrder(1L, "1", "2");
	}

	@Test
	public void removeProductFromOrderPositiveTest() throws ServiceException, DaoException {
		doNothing().when(orderProductConnectionDao).delete(isA(OrderProductConnection.class));
		Assert.assertTrue(orderService.removeProductFromOrder(1L, "1"));
	}

	@Test
	public void removeProductFromOrderNegativeTest() throws ServiceException {
		Assert.assertFalse(orderService.removeProductFromOrder(null, "1"));
	}

	@Test(expectedExceptions = ServiceException.class)
	public void removeProductFromOrderServiceExceptionTest() throws ServiceException, DaoException {
		doThrow(DaoException.class).when(orderProductConnectionDao).delete(isA(OrderProductConnection.class));
		orderService.removeProductFromOrder(1L, "1");
	}

	@Test
	public void checkoutPositiveTest() throws ServiceException, InvalidDataException, DaoException {
		when(orderDao.update(isA(Order.class))).thenReturn(true);
		when(orderProductConnectionDao.findByOrderId(anyLong())).thenReturn(products);
		doNothing().when(productDao).reduceQuantity(anyMap());
		Assert.assertTrue(orderService.checkout(orderInfo));
	}

	@Test
	public void checkoutNegativeTest() throws ServiceException, InvalidDataException {
		Assert.assertFalse(orderService.checkout(Collections.emptyMap()));
	}

	@Test(expectedExceptions = ServiceException.class)
	public void checkoutServiceExceptionTest() throws ServiceException, InvalidDataException, DaoException {
		when(orderDao.update(isA(Order.class))).thenThrow(DaoException.class);
		orderService.checkout(orderInfo);
	}

	@Test(expectedExceptions = InvalidDataException.class)
	public void checkoutInvalidDataExceptionTest() throws ServiceException, InvalidDataException, DaoException {
		Map<String, String> orderInfo = new HashMap<>();
		orderInfo.put("orderBasketId", "1");
		orderService.checkout(orderInfo);
	}

	@Test
	public void cancelOrderPositiveTest() throws ServiceException, DaoException {
		when(orderDao.updateStatus(anyString(), isA(OrderStatus.class), isA(OrderStatus.class))).thenReturn(true);
		when(orderProductConnectionDao.findByOrderId(anyLong())).thenReturn(products);
		doNothing().when(productDao).increaseQuantity(anyMap());
		Assert.assertTrue(orderService.cancelOrder("1", "placed", UserRole.ADMIN));
	}

	@Test
	public void cancelOrderNegativeTest() throws ServiceException {
		Assert.assertFalse(orderService.cancelOrder("1", "ready", UserRole.CLIENT));
	}

	@Test(expectedExceptions = ServiceException.class)
	public void cancelOrderServiceExceptionTest() throws ServiceException, DaoException {
		when(orderDao.updateStatus(anyString(), isA(OrderStatus.class), isA(OrderStatus.class)))
				.thenThrow(DaoException.class);
		orderService.cancelOrder("1", "placed", UserRole.ADMIN);
	}

	@Test
	public void processOrderPositiveTest() throws ServiceException, DaoException {
		when(orderDao.updateStatus(anyString(), isA(OrderStatus.class), isA(OrderStatus.class))).thenReturn(true);
		Assert.assertTrue(orderService.processOrder("1", "accepted"));
	}

	@Test
	public void processOrderNegativeTest() throws ServiceException {
		Assert.assertFalse(orderService.processOrder("1", "basket"));
	}

	@Test(expectedExceptions = ServiceException.class)
	public void processOrderServiceExceptionTest() throws ServiceException, DaoException {
		when(orderDao.updateStatus(anyString(), isA(OrderStatus.class), isA(OrderStatus.class)))
				.thenThrow(DaoException.class);
		Assert.assertTrue(orderService.processOrder("1", "accepted"));
	}

	@Test
	public void takeOrderBasketPositiveTest() throws ServiceException, DaoException {
		Order basket = new Order(1L, 1L);
		basket.setProducts(products);
		basket.setCost(new BigDecimal(320));
		Optional<Order> expected = Optional.of(basket);
		when(orderProductConnectionDao.findByOrderId(anyLong())).thenReturn(products);
		Optional<Order> actual = orderService.takeOrderBasket(1L, 1L);
		Assert.assertEquals(actual, expected);
	}

	@Test
	public void takeOrderBasketNegativeTest() throws ServiceException {
		Optional<Order> expected = Optional.empty();
		Optional<Order> actual = orderService.takeOrderBasket(null, 1L);
		Assert.assertEquals(actual, expected);
	}

	@Test(expectedExceptions = ServiceException.class)
	public void takeOrderBasketServiceExceptionTest() throws ServiceException, DaoException {
		when(orderDao.findOrderBasketId(anyLong())).thenThrow(DaoException.class);
		orderService.takeOrderBasket(1L, null);
	}

	@Test
	public void takeOrderByIdPositiveTest() throws ServiceException, DaoException {
		Optional<Order> expected = Optional.of(order);
		when(orderDao.findOrderById(anyString())).thenReturn(expected);
		when(orderProductConnectionDao.findByOrderId(anyLong())).thenReturn(products);
		Optional<Order> actual = orderService.takeOrderById("1");
		Assert.assertEquals(actual, expected);
	}

	@Test
	public void takeOrderByIdNegativeTest() throws ServiceException {
		Optional<Order> expected = Optional.empty();
		Optional<Order> actual = orderService.takeOrderById(null);
		Assert.assertEquals(actual, expected);
	}

	@Test(expectedExceptions = ServiceException.class)
	public void takeOrderByIdServiceExceptionTest() throws ServiceException, DaoException {
		when(orderDao.findOrderById(anyString())).thenThrow(DaoException.class);
		orderService.takeOrderById("1");
	}

	@Test
	public void takeOrdersByLoginPositiveTest() throws ServiceException, DaoException {
		List<Order> expected = Arrays.asList(order);
		when(orderDao.findOrdersByLogin(anyString())).thenReturn(expected);
		when(orderProductConnectionDao.findByOrderId(anyLong())).thenReturn(products);
		List<Order> actual = orderService.takeOrdersByLogin("kuksar@gmail.com");
		Assert.assertEquals(actual, expected);
	}

	@Test
	public void takeOrdersByLoginNegativeTest() throws ServiceException {
		List<Order> expected = Collections.emptyList();
		List<Order> actual = orderService.takeOrdersByLogin("123");
		Assert.assertEquals(actual, expected);
	}

	@Test(expectedExceptions = ServiceException.class)
	public void takeOrdersByLoginServiceExceptionTest() throws ServiceException, DaoException {
		when(orderDao.findOrdersByLogin(anyString())).thenThrow(DaoException.class);
		orderService.takeOrdersByLogin("kuksar@gmail.com");
	}

	@Test
	public void takeOrdersByStatusPositiveTest() throws DaoException, ServiceException {
		List<Order> expected = Arrays.asList(order);
		when(orderDao.findOrdersByStatus(anyString())).thenReturn(expected);
		when(orderProductConnectionDao.findByOrderId(anyLong())).thenReturn(products);
		List<Order> actual = orderService.takeOrdersByStatus("placed");
		Assert.assertEquals(actual, expected);
	}

	@Test
	public void takeOrdersByStatusNegativeTest() throws ServiceException {
		List<Order> expected = Collections.emptyList();
		List<Order> actual = orderService.takeOrdersByStatus("123");
		Assert.assertEquals(actual, expected);
	}

	@Test(expectedExceptions = ServiceException.class)
	public void takeOrdersByStatusServiceExceptionTest() throws DaoException, ServiceException {
		when(orderDao.findOrdersByStatus(anyString())).thenThrow(DaoException.class);
		orderService.takeOrdersByStatus("placed");
	}
}
