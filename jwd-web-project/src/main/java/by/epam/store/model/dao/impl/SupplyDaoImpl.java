package by.epam.store.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import by.epam.store.entity.Product;
import by.epam.store.entity.Supply;
import by.epam.store.model.connection.ConnectionPool;
import by.epam.store.model.connection.ConnectionPoolException;
import by.epam.store.model.dao.DaoException;
import by.epam.store.model.dao.SupplyDao;

public class SupplyDaoImpl implements SupplyDao {
	private static final String SQL_INSERT_SUPPLY = "INSERT INTO SUPPLIES (DATA_TIME) VALUES (?)";
	private static final String SQL_INSERT_SUPPLY_PRODUCT_CONNECTION = "INSERT INTO SUPPLY_PRODUCT_CONNECTION (SUPPLY_ID, PRODUCT_ID, AMOUNT_OF_PRODUCT) VALUES (?,?,?)";

	@Override
	public List<Supply> findAll() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(Supply supply) throws DaoException {
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT_SUPPLY,
						Statement.RETURN_GENERATED_KEYS)) {
			statement.setTimestamp(1, Timestamp.valueOf(supply.getDateTime()));
			statement.executeUpdate();
			ResultSet resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				supply.setSupplyId(resultSet.getLong(1));
			}
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
	}

	@Override
	public boolean update(Supply supply) throws DaoException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createSupplyProductConnection(Supply supply) throws DaoException {
		try (Connection connection = ConnectionPool.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(SQL_INSERT_SUPPLY_PRODUCT_CONNECTION)) {
			for (Map.Entry<Product, Integer> productAndAmount : supply.getSuppliedProducts().entrySet()) {
				statement.setLong(1, supply.getSupplyId());
				statement.setLong(2, productAndAmount.getKey().getProductId());
				statement.setInt(3, productAndAmount.getValue());
				statement.addBatch();
			}
			statement.executeBatch();
		} catch (ConnectionPoolException | SQLException e) {
			throw new DaoException("database error", e);
		}
	}
}