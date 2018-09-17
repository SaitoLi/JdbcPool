package com.lzz.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lzz.utils.JdbcUtils_DBCP;

public class DataSourceTest {
	public static void main(String[] args) throws SQLException {
	
		long start = System.currentTimeMillis();
		// 获取数据库连接
		Connection conn1 = JdbcUtils_DBCP.getConnection();
		Connection conn2 = JdbcUtils_DBCP.getConnection();
		Connection conn3 = JdbcUtils_DBCP.getConnection();
		Connection conn4 = JdbcUtils_DBCP.getConnection();
		long end = System.currentTimeMillis();
		
		System.out.println("用时:" + (end - start));
		System.out.println(conn1);
		System.out.println(conn2);
		System.out.println(conn3);
		System.out.println(conn4);
		
		conn1.close();
		conn2.close();
		conn3.close();
		conn4.close();
	}
}
