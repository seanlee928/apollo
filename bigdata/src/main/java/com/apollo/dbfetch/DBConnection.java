package com.apollo.dbfetch;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

	private final static String DB_URL = "jdbc:oracle:thin:@10.20.36.16:1521:crmcn";
	private final static String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private final static String DB_USERNAME = "finance";
	private final static String DB_PASSWORD = "oracle";

	public static Connection getConnection() {
		/** 声明Connection连接对象 */
		Connection conn = null;
		try {
			/** 使用Class.forName()方法自动创建这个驱动程序的实例且自动调用DriverManager来注册它 */
			Class.forName(DB_DRIVER);
			/** 通过DriverManager的getConnection()方法获取数据库连接 */
			conn = DriverManager
					.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭数据库连接
	 * 
	 * @param connect
	 */
	public void closeConnection(Connection conn) {
		try {
			if (conn != null) {
				/** 判断当前连接连接对象如果没有被关闭就调用关闭方法 */
				if (!conn.isClosed()) {
					conn.close();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
