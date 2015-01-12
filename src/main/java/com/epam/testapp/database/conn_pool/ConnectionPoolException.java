package com.epam.testapp.database.conn_pool;
/**
 * ConnectionPoolException --- class exception which is thrown when some
 * problems occurs in Connection pool
 * 
 * @author Marharyta_Amelyanchuk
 */
public class ConnectionPoolException extends Exception {

	private static final long serialVersionUID = 4992223960436174956L;

	public ConnectionPoolException() {
		super();
	}

	public ConnectionPoolException(String message) {
		super(message);
	}

		public ConnectionPoolException(String message, Exception e) {
		super(message, e);
	}

	public ConnectionPoolException(Throwable cause) {
		super(cause);
	}

}

