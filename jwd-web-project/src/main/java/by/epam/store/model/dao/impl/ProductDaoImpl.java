package by.epam.store.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epam.store.model.connection.ConnectionPool;
import by.epam.store.model.connection.ConnectionPoolException;
import by.epam.store.model.dao.DaoException;
import by.epam.store.model.dao.ProductDao;
import by.epam.store.model.entity.Product;
import by.epam.store.model.entity.builder.ProductBuilder;

public class ProductDaoImpl implements ProductDao {
	// private static final Logger logger = LogManager.getLogger();
	private static final String SQL_SELECT_PRODUCTS_BY_CATEGORY_ID = "SELECT PRODUCTS.ID, CATEGORY_ID, CATEGORY, NAME, PRODUCTS.IMAGE_NAME, PRICE, AMOUNT FROM PRODUCTS JOIN PRODUCT_CATEGORIES ON PRODUCTS.CATEGORY_ID=PRODUCT_CATEGORIES.ID WHERE CATEGORY_ID=?";
	private static final String SQL_SELECT_PRODUCTS_BY_NAME = "SELECT PRODUCTS.ID, CATEGORY_ID, CATEGORY, NAME, PRODUCTS.IMAGE_NAME, PRICE, AMOUNT FROM PRODUCTS JOIN PRODUCT_CATEGORIES ON PRODUCTS.CATEGORY_ID=PRODUCT_CATEGORIES.ID WHERE NAME LIKE ?";
	private static final String ZERO_OR_MORE_CHARACTERS = "%";
	private static final String SQL_INSERT_PRODUCT = "INSERT INTO PRODUCTS (CATEGORY_ID, NAME, IMAGE_NAME, PRICE) VALUES (?, ?, ?, ?)";

	@Override
	public List<Product> findAll() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(Product product) throws DaoException {
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT_PRODUCT)) {
			statement.setLong(1, product.getCategory().getCategoryId());
			statement.setString(2, product.getProductName());
			statement.setString(3, product.getImageName());
			statement.setBigDecimal(4, product.getPrice());
			statement.executeUpdate();
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
	}

	@Override
	public boolean update(Product entity) throws DaoException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Product> findProductByCategoryId(Long id) throws DaoException {
		List<Product> products = new ArrayList<>();
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PRODUCTS_BY_CATEGORY_ID)) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Product product = ProductBuilder.getInstance().build(resultSet);
				products.add(product);
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return products;
	}

	@Override
	public List<Product> findProductsByName(String productName) throws DaoException {
		List<Product> products = new ArrayList<>();
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PRODUCTS_BY_NAME)) {
			statement.setString(1, ZERO_OR_MORE_CHARACTERS + productName + ZERO_OR_MORE_CHARACTERS);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
			Product	product = ProductBuilder.getInstance().build(resultSet);
				products.add(product);
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
		return products;
	}
}
