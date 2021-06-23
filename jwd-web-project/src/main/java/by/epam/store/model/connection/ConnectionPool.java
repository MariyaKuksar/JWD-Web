package by.epam.store.model.connection;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Pool of connections used while the system is running
 * 
 * @author Mariya Kuksar
 */
public class ConnectionPool {
	private static final Logger logger = LogManager.getLogger();
	private static final int DEFAULT_POOL_SIZE = 8;
	private static ConnectionPool instance;
	private static AtomicBoolean instanceCreated = new AtomicBoolean();
	private static ReentrantLock lock = new ReentrantLock();
	private BlockingQueue<ProxyConnection> freeConnections;
	private BlockingQueue<ProxyConnection> givenAwayConnections;
	
	private ConnectionPool() {
	}

	/**
	 * Gets instance of this class
	 * 
	 * @return {@link ConnectionPool} instance
	 */
	public static ConnectionPool getInstance() {
		if (!instanceCreated.get()) {
			lock.lock();
			if (!instanceCreated.get()) {
				instance = new ConnectionPool();
				instanceCreated.set(true);
			}
			lock.unlock();
		}
		return instance;
	}

	/**
	 * Initialize connection pool
	 * 
	 * @throws ConnectionPoolException if no connection created
	 */
	public void initPool() throws ConnectionPoolException {
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
			throw new ConnectionPoolException("connection pool initialization error");
		}
	}

	/**
	 * Gets a connection from the connection pool
	 * 
	 * @return {@link Connection} connection to the database
	 * @throws ConnectionPoolException if {@link InterruptedException} occurs
	 */
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

	/**
	 * Returns the connection to the connection pool
	 * 
	 * @param connection {@link Connection} connection to the database
	 * @return boolean
	 */
	public boolean releaseConnection(Connection connection) {
		boolean isReleased = false;
		if (givenAwayConnections.remove(connection)) {
			isReleased = freeConnections.offer((ProxyConnection) connection);
		}
		return isReleased;
	}

	/**
	 * Destroy connection pool
	 * 
	 * @throws ConnectionPoolException if {@link InterruptedException} or
	 *                                 {@link SQLException} occurs
	 */
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

	/**
	 * Unregisters drivers
	 * 
	 * @throws ConnectionPoolException if {@link SQLException} occurs
	 */
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
