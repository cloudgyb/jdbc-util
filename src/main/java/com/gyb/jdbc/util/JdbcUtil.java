package com.gyb.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gyb.jdbc.test.JdbcUtilTest;

public final class JdbcUtil {
	public static Logger log = LogManager.getLogger();
	private static Properties prop;
	private static String url;
	private static String username;
	private static String password;
	private static ThreadLocal<Connection> tl;
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			InputStream is = JdbcUtilTest.class.getResourceAsStream("/jdbc.properties");
			prop = new Properties();
			prop.load(is);
			url = prop.getProperty("jdbc.url");
			username = prop.getProperty("jdbc.username");
			password = prop.getProperty("jdbc.password");
			is.close();
			tl = new ThreadLocal<>();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	private static Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, username, password);
			tl.set(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	public static Connection getConn() {
		return tl.get() == null?getConnection():tl.get();
	}
	
	public static PreparedStatement getStatement(String sql) {
		Connection conn = getConn();
		System.out.println(conn);
		PreparedStatement ps = null;
		if (conn != null) {
			try {
				ps = conn.prepareStatement(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ps;
	}

	public static void jdbcResourceClose(Connection conn, PreparedStatement ps, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void jdbcResourceClose(PreparedStatement ps, ResultSet rs) {
		jdbcResourceClose(tl.get(), ps, rs);
		tl.remove();
	}
}
