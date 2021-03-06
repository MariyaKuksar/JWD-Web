package by.epam.store.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import by.epam.store.entity.Product;
import by.epam.store.entity.ProductList;
import by.epam.store.model.connection.ConnectionPool;
import by.epam.store.model.connection.ConnectionPoolException;
import by.epam.store.model.dao.DaoException;
import by.epam.store.model.dao.ProductDao;

/**
 * Works with database table products
 * 
 * @author Mariya Kuksar
 * @see ProductDao
 */
public class ProductDaoImpl implements ProductDao {
	private static final String SQL_INSERT_PRODUCT = "INSERT INTO PRODUCTS (CATEGORY_ID, NAME, IMAGE_NAME, PRICE) VALUES (?, ?, ?, ?)";
	private static final String SQL_UPDATE_PRODUCT = "UPDATE PRODUCTS SET NAME=?, PRICE=? WHERE ID=?";
	private static final String SQL_UPDATE_REDUCE_QUANTITY_PRODUCT = "UPDATE PRODUCTS SET QUANTITY=QUANTITY-? WHERE ID=?";
	private static final String SQL_UPDATE_INCREASE_QUANTITY_PRODUCT = "UPDATE PRODUCTS SET QUANTITY=QUANTITY+? WHERE ID=?";
	private static final String SQL_SELECT_PRODUCTS_BY_CATEGORY_ID = "SELECT ID, CATEGORY_ID, NAME, IMAGE_NAME, PRICE, QUANTITY FROM PRODUCTS WHERE CATEGORY_ID=?";
	private static final String SQL_SELECT_PRODUCTS_BY_NAME = "SELECT ID, CATEGORY_ID, NAME, IMAGE_NAME, PRICE, QUANTITY FROM PRODUCTS WHERE NAME LIKE ?";
	private static final String SQL_SELECT_PRODUCT_BY_ID = "SELECT ID, CATEGORY_ID, NAME, IMAGE_NAME, PRICE, QUANTITY FROM PRODUCTS WHERE PRODUCTS.ID=?";
	private static final String SQL_SELECT_PRODUCTS_IN_STOCK = "SELECT SQL_CALC_FOUND_ROWS ID, CATEGORY_ID, NAME, IMAGE_NAME, PRICE, QUANTITY FROM PRODUCTS WHERE QUANTITY > 0 LIMIT ";
	private static final String SQL_SELECT_PRODUCTS_ON_ORDER = "SELECT SQL_CALC_FOUND_ROWS ID, CATEGORY_ID, NAME, IMAGE_NAME, PRICE, QUANTITY FROM PRODUCTS WHERE QUANTITY <= 0 LIMIT ";
	private static final String SQL_SELECT_FOUND_ROWS = "SELECT FOUND_ROWS()";
	private static final String ZERO_OR_MORE_CHARACTERS = "%";
	private static final String SEPARATOR = ", ";

	@Override
	public void create(Product product) throws DaoException {
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT_PRODUCT)) {
			statement.setLong(1, product.getCategoryId());
			statement.setString(2, product.getProductName());
			statement.setString(3, product.getImageName());
			statement.setBigDecimal(4, product.getPrice());
			statement.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
	}

	@Override
	public boolean update(Product product) throws DaoException {
		int numberUpdatedRows;
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PRODUCT)) {
			statement.setString(1, product.getProductName());
			statement.setBigDecimal(2, product.getPrice());
			statement.setLong(3, product.getProductId());
			numberUpdatedRows = statement.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return (numberUpdatedRows != 0);
	}

	@Override
	public List<Product> findProductsByCategoryId(String categoryId) throws DaoException {
		List<Product> products = new ArrayList<>();
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PRODUCTS_BY_CATEGORY_ID)) {
			statement.setString(1, categoryId);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Product product = DaoEntityBuilder.buildProduct(resultSet);
				products.add(product);
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return products;
	}

	@Override
	public void increaseQuantity(Map<Product, Integer> products) throws DaoException {
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_INCREASE_QUANTITY_PRODUCT)) {
			connection.setAutoCommit(false);
			for (Map.Entry<Product, Integer> productAndQuantity : products.entrySet()) {
				statement.setInt(1, productAndQuantity.getValue());
				statement.setLong(2, productAndQuantity.getKey().getProductId());
				statement.addBatch();
			}
			statement.executeBatch();
			connection.commit();
			connection.setAutoCommit(true);
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
	}

	@Override
	public void reduceQuantity(Map<Product, Integer> products) throws DaoException {
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_REDUCE_QUANTITY_PRODUCT)) {
			connection.setAutoCommit(false);
			for (Map.Entry<Product, Integer> productAndQuantity : products.entrySet()) {
				statement.setInt(1, productAndQuantity.getValue());
				statement.setLong(2, productAndQuantity.getKey().getProductId());
				statement.addBatch();
			}
			statement.executeBatch();
			connection.commit();
			connection.setAutoCommit(true);
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
	}

	@Override
	public Optional<Product> findProductById(String productId) throws DaoException {
		Optional<Product> productOptional;
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PRODUCT_BY_ID)) {
			statement.setString(1, productId);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				Product product = DaoEntityBuilder.buildProduct(resultSet);
				productOptional = Optional.of(product);
			} else {
				productOptional = Optional.empty();
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return productOptional;
	}

	@Override
	public List<Product> findProductsByName(String productName) throws DaoException {
		List<Product> products = new ArrayList<>();
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PRODUCTS_BY_NAME)) {
			statement.setString(1, ZERO_OR_MORE_CHARACTERS + productName + ZERO_OR_MORE_CHARACTERS);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Product product = DaoEntityBuilder.buildProduct(resultSet);
				products.add(product);
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return products;
	}

	@Override
	public ProductList findProductsInStock(int startPozition, int recordsPerPages) throws DaoException {
		ProductList productList = new ProductList();
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement
					.executeQuery(SQL_SELECT_PRODUCTS_IN_STOCK + startPozition + SEPARATOR + recordsPerPages);
			List<Product> products = new ArrayList<>();
			while (resultSet.next()) {
				Product product = DaoEntityBuilder.buildProduct(resultSet);
				products.add(product);
			}
			productList.setProducts(products);
			resultSet = statement.executeQuery(SQL_SELECT_FOUND_ROWS);
			if (resultSet.next()) {
				int numberOfRows = resultSet.getInt(1);
				int numberOfPages = (int) Math.ceil((double) numberOfRows / recordsPerPages);
				productList.setNumberOfPages(numberOfPages);
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return productList;
	}

	@Override
	public ProductList findProductsOnOrder(int startPozition, int recordsPerPages) throws DaoException {
		ProductList productList = new ProductList();
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement
					.executeQuery(SQL_SELECT_PRODUCTS_ON_ORDER + startPozition + SEPARATOR + recordsPerPages);
			List<Product> products = new ArrayList<>();
			while (resultSet.next()) {
				Product product = DaoEntityBuilder.buildProduct(resultSet);
				products.add(product);
			}
			productList.setProducts(products);
			resultSet = statement.executeQuery(SQL_SELECT_FOUND_ROWS);
			if (resultSet.next()) {
				int numberOfRows = resultSet.getInt(1);
				int numberOfPages = (int) Math.ceil((double) numberOfRows / recordsPerPages);
				productList.setNumberOfPages(numberOfPages);
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return productList;
	}

	@Override
	public List<Product> findAll() throws DaoException {
		throw new UnsupportedOperationException("operation not supported for class " + this.getClass().getName());
	}
}
