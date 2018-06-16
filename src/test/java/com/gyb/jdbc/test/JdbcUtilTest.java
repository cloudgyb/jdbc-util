package com.gyb.jdbc.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.gyb.jdbc.util.JdbcUtil;

public class JdbcUtilTest {
	@Test
	public void testUtil() {
		String sql = "select * from class where cid=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = JdbcUtil.getStatement(sql);
			ps.setInt(1, 1);
			rs = ps.executeQuery();
			while(rs.next()) {
				int c1 = rs.getInt(1);
				String c2 = rs.getString(2);
				System.out.println(c1+"\t"+c2);
			}
			Connection conn = JdbcUtil.getConn();
			System.out.println(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.jdbcResourceClose(ps, rs);
		}
		
	}
}
