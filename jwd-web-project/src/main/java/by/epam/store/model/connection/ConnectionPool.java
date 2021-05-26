package by.epam.store.model.connection;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionPool {
	private static final Logger logger = LogManager.getLogger();
	private static final int DEFAULT_POOL_SIZE = 8;
	private static final ConnectionPool instance = new ConnectionPool();
	private BlockingQueue<ProxyConnection> freeConnections;
	private BlockingQueue<ProxyConnection> givenAwayConnections;

	public static ConnectionPool getInstance() {
		return instance;
	}

	private ConnectionPool() {
		freeConnections = new ArrayBlockingQueue<ProxyConnection>(DEFAULT_POOL_SIZE);
		givenAwayConnections = new ArrayBlockingQueue<ProxyConnection>(DEFAULT_POOL_SIZE);
		for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
			try {
				Connection connection = ConnectionCreator.createConnection();
				ProxyConnection proxyConnection = new ProxyConnection(connection);
				freeConnections.add(proxyConnection);
			} catch (SQLException e) {
				logger.error("database access error, connection not received", e);
			}
		}
		if (freeConnections.isEmpty()) {
			logger.fatal("database access error");
			throw new RuntimeException("database access error");
		}
	}

	public Connection getConnection() throws ConnectionPoolException {
		ProxyConnection connection;
		try {
			connection = freeConnections.take();
			givenAwayConnections.offer(connection);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new ConnectionPoolException("error getting connection", e);
		}
		return connection;
	}

	public boolean releaseConnection(Connection connection) {
		boolean isReleased = false;
		if (givenAwayConnections.remove(connection)) {
			isReleased = freeConnections.offer((ProxyConnection) connection);
		}
		return isReleased;
	}

	public void destroyPool() throws ConnectionPoolException {
		for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
			try {
				freeConnections.take().reallyClose();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new ConnectionPoolException("Thread was interrupted while taking a free connection", e);
			} catch (SQLException e) {
				throw new ConnectionPoolException("error closing connection", e);
			}
		}
		deregisterDrivers();
	}

	private void deregisterDrivers() throws ConnectionPoolException {
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			Driver driver = drivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
			} catch (SQLException e) {
				throw new ConnectionPoolException("driver deregistration error", e);
			}
		}
	}
}
