package by.epam.store.model.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

final class ConnectionCreator {
	private static final Logger logger = LogManager.getLogger();
	private static final ResourceBundle bundle;
	private static final String BUNDLE_NAME = "db";
	private static final String DB_DRIVER = "db.driver";
	private static final String DB_URL = "db.url";
	private static final String DB_USER = "db.user";
	private static final String DB_PASSWORD = "db.password";
	private static final String DATABASE_URL;
	private static final String DATABASE_USER_NAME;
	private static final String DATABASE_PASSWORD;

	static {
		bundle = ResourceBundle.getBundle(BUNDLE_NAME);
		String driverName = bundle.getString(DB_DRIVER);
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			logger.fatal("driver" + driverName + "don't found", e);
			throw new RuntimeException("driver" + driverName + "don't found", e);
		}
		DATABASE_URL = bundle.getString(DB_URL);
		DATABASE_USER_NAME = bundle.getString(DB_USER);
		DATABASE_PASSWORD = bundle.getString(DB_PASSWORD);
	}

	private ConnectionCreator() {
	}

	static Connection createConnection() throws SQLException {
		return DriverManager.getConnection(DATABASE_URL, DATABASE_USER_NAME, DATABASE_PASSWORD);
	}
}
