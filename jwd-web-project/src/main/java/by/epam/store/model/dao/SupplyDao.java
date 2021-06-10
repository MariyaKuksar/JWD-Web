package by.epam.store.model.dao;

import java.sql.SQLException;

import by.epam.store.entity.Supply;
import by.epam.store.model.connection.ConnectionPoolException;

/**
 * The interface for working with database table supplies
 * 
 * @author Mariya Kuksar
 * @see BaseDao
 */
public interface SupplyDao extends BaseDao<Supply> {

	/**
	 * Creates new supply product connection
	 * 
	 * @param supply {@link Supply} all data to create
	 * @throws DaoException if {@link ConnectionPoolException} or
	 *                      {@link SQLException} occur
	 */
	void createSupplyProductConnection(Supply supply) throws DaoException;
}
