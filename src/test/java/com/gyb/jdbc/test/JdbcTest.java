package com.gyb.jdbc.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTest {
	//@Test
	public void test1() {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "123456");
			conn.setAutoCommit(false);
			String sql = "select * from class where cid=?";
			statement = conn.prepareStatement(sql);
			statement.setInt(1, 1);
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				int c1 = resultSet.getInt(1);
				String c2 = resultSet.getString(2);
				System.out.println(c1+"\t"+c2);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally {
			try {
				resultSet.close();
				statement.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
