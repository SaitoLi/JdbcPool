package com.lzz.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.lzz.datasource.JdbcPool;

public class TestDemo1 {
	/**
	 * 创建一个连接池对象
	 */
	private static JdbcPool pool = new JdbcPool();
	
	/**
	 * 从数据库连接池中获取数据库连接对象
	 * @return Connection数据库连接对象
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException{
		return pool.getConnection();
	}
	
//	public static void release(Connection conn , Statement st, ResultSet rs) throws SQLException {
//		if(rs != null) {
//			rs.close();
//		}
//		if(st != null) {
//			st.close();
//		}
//		if(conn != null) {
//			conn.close();
//		}
//	}
	
	public static void main(String[] args) throws SQLException {
		Connection conn1 = getConnection();
		Connection conn2 = getConnection();
		Connection conn3 = getConnection();
		Connection conn4 = getConnection();
		Connection conn5 = getConnection();
		Connection conn6 = getConnection();
		Connection conn7 = getConnection();
		Connection conn8 = getConnection();
		Connection conn9 = getConnection();
		Connection conn10 = getConnection();
		Connection conn11 = getConnection();
		conn1.close();
		conn2.close();
		conn3.close();
		conn4.close();
		conn5.close();
		conn6.close();
		conn7.close();
		conn8.close();
		conn9.close();
		conn10.close();
		
	}
}
