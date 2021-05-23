package by.epam.store.model.dao;

import by.epam.store.entity.Supply;

public interface SupplyDao extends BaseDao<Supply> {

	void createSupplyProductConnection(Supply supply) throws DaoException;

}
