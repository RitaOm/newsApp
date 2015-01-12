package com.epam.testapp.database.conn_pool;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.log4j.Logger;

/**
 * JDBCConnectionPool --- implementation of ConnectionPool interface which uses
 * JDBC for database connectivity
 * 
 * @author Marharyta_Amelyanchuk
 */
public final class JDBCConnectionPool implements ConnectionPool {
	private static final Logger LOG = Logger
			.getLogger(JDBCConnectionPool.class);

	private Driver dbDriver;
	/**
	 * constant which store value of max quantity SQLException occurrences on
	 * pool initialization when creating new connections to put them to the
	 * queue of busy
	 */
	private static final int MAX_SQL_EXCEPTIONS_ON_POOL_INITIALIZATION = 3;
	/** queue of free connections */
	private BlockingQueue<Connection> freeConnections;
	/** queue of connections in use */
	private BlockingQueue<Connection> busyConnections;
	/** field which is set as true when shutdown of connection pool takes place */
	private AtomicBoolean isShutDown = new AtomicBoolean(false);
	/** counter of opened connection */
	private AtomicInteger openedConnectins = new AtomicInteger(0);
	/** data for connecting to database */
	private String driver;
	private String url;
	private String user;
	private String password;
	private int maxSize;
	private int minSize;
	private static final ReentrantLock LOCK = new ReentrantLock();

	/**
	 * Constructor Initializes field info. Register driver. Initialize queue of
	 * free connections and queue of connections in use. Open 1/3 of connection
	 * pool max size connections and put it to the queue of free connections
	 * 
	 * @exception ConnectionPoolException
	 *                if connection pool isn't initialized properly
	 */
	private JDBCConnectionPool(){
	}
	
	public void init()
			throws ConnectionPoolException {
		registerDriver();
		if (maxSize == 0 || minSize == 0 || maxSize < minSize) {
			throw new ConnectionPoolException(
					"A problem with connection pool max/min size ");
		}
		freeConnections = new ArrayBlockingQueue<Connection>(maxSize);
		busyConnections = new ArrayBlockingQueue<Connection>(maxSize);
		Connection connection = null;
		int exceptions = 0;
		try {
			for (int i = 0; i < minSize; i++) {
				connection = createConnection();
				freeConnections.add(connection);
			}
		} catch (SQLException e) {
			exceptions++;
			if (exceptions > MAX_SQL_EXCEPTIONS_ON_POOL_INITIALIZATION) {
				throw new ConnectionPoolException(
						"Can't create a connection on pool initialization: "
								+ e.getMessage());
			} else {
				LOG.error("Can't create a connection on pool initialization: "
						+ e.getMessage());
			}
		}
	}

	private void registerDriver() throws ConnectionPoolException {
		try {
			dbDriver = (Driver) Class.forName(driver).newInstance();
			DriverManager.registerDriver(dbDriver);
		} catch (InstantiationException e) {
			throw new ConnectionPoolException("Can't instantiate DB Driver. "
					+ e.getMessage());
		} catch (IllegalAccessException e) {
			throw new ConnectionPoolException(
					"Access problem when register DB Driver. " + e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new ConnectionPoolException("Class " + driver
					+ "is not found. " + e.getMessage());
		} catch (SQLException e) {
			throw new ConnectionPoolException("Unable to register " + driver
					+ " driver." + e.getMessage());
		}
	}

	/**
	 * Throws ConnectionPoolException if shutdown of connection pool takes
	 * place. In other case tries to poll connection from the queue of free
	 * connections with time waiting 1 second. If connection isn't taken and
	 * pool size allows, a new connection is opening. In other case method tries
	 * to poll connection from the queue of free connections with time waiting
	 * 10 second. If after these operations connection hasn't been initialized
	 * yet ConnectionPoolException will be thrown. If exception isn't thrown
	 * checking if connection is closed or null takes place. If connection isn't
	 * applicable for using method will be called recursively, else - method
	 * return connection
	 * 
	 * @return object of class Connection which must be released after using
	 * @exception ConnectionPoolException
	 *                if shutdown of connection pool takes place, waiting time
	 *                is exceeded or SQLException occurs when connection is
	 *                taken
	 */
	public Connection getConnection() throws ConnectionPoolException {
		if (isShutDown.get()) {
			throw new ConnectionPoolException(
					"Connection to database can't be taken: shut down of connection pool");
		}
		Connection connection = null;
		try {
			connection = freeConnections.poll(1, TimeUnit.SECONDS);
			if (connection == null && openedConnectins.get() < maxSize) {
				connection = createConnection();
			} else if (connection == null) {
				connection = freeConnections.poll(10, TimeUnit.SECONDS);
				if (connection == null) {
					throw new ConnectionPoolException(
							"Connection isn't taken: waiting time is exceeded.");
				}
			}
		} catch (InterruptedException e) {
			LOG.error("Taking a connection from the queue of available is interrupted."
					+ e.getMessage());
		} catch (SQLException e) {
			throw new ConnectionPoolException(
					"SQLException when making new connection: "
							+ e.getMessage());
		}
		try {
			if (connection.isClosed()) {
				openedConnectins.decrementAndGet();
				connection = getConnection();
			}
			busyConnections.offer(connection);
		} catch (SQLException e) {
			LOG.error("SQLException: " + e.getMessage());
		}
		return connection;
	}

	/**
	 * 
	 * Checks if connection is null. In this case the counter of opened
	 * connections (openedConnectins field) is decreased by 1 and return from
	 * method occurs. Else connection is removed from the queue of busy
	 * connections. If connection has already closed counter of opened
	 * connection is decreased by 1, else this connection is added to the queue
	 * of free connections
	 * 
	 * @param connection
	 *            Object of class Connection which will be released
	 * @return void
	 */
	public void release(Connection connection) {
		if (connection == null) {
			openedConnectins.decrementAndGet();
			return;
		}
		busyConnections.remove(connection);
		try {
			if (connection.isClosed()) {
				openedConnectins.decrementAndGet();
			} else {
				freeConnections.offer(connection);
			}
		} catch (SQLException e) {
			LOG.error("SQLException, releasing a connection. " + e.getMessage());
		}
	}

	/**
	 * Closes all opened connections in this instance of connection pool. Sets
	 * field isShutDown true so no more connections can be taken. Waiting all
	 * connections in use would be returned to the queue of free connections.
	 * Close all connection of the queue of free connections. Deregister driver
	 * 
	 * @return void
	 */
	public void shutdown() {
		isShutDown.set(true);
		Connection connection = null;
		LOCK.lock();
		try {
			while (!busyConnections.isEmpty()) {
				connection = busyConnections.poll();
				if (connection != null | !connection.isClosed()) {
					connection.close();
				}
			}
			while (!freeConnections.isEmpty()) {
				connection = freeConnections.poll();
				if (connection != null | !connection.isClosed()) {
					connection.close();
				}
			}
			DriverManager.deregisterDriver(dbDriver);
			LOG.debug("SHUTDOWN of " + toString());
		} catch (SQLException e) {
			LOG.error("SQLException, shut down the connection pool: "
					+ e.getMessage());
			shutdown();
		} finally {
			LOCK.unlock();
		}
	}

	private Connection createConnection() throws SQLException {
		Connection connection = DriverManager
				.getConnection(url, user, password);
		openedConnectins.incrementAndGet();
		return connection;
	}

	public String toString() {
		return "Connection pool: " + url + ", " + user + ", available="
				+ freeConnections.size() + ", busy=" + busyConnections.size()
				+ ", max=" + maxSize;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
	public int getMinSize() {
		return minSize;
	}
	public void setMinSize(int minSize) {
		this.minSize = minSize;
	}

	
}
