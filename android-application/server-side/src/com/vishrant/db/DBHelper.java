package com.vishrant.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBHelper {

	public static Connection getConnection() {

		Connection connection = null;
		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");

			connection = DriverManager.getConnection(
					"jdbc:oracle:oci8:@127.0.0.1:1521:XE", "scott", "tiger");

			return connection;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
}
