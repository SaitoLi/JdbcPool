package com.lzz.datasource;

import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * 使用动态代理机制实现数据库连接池范例
 * 
 * @author CunsiALIEN
 *
 */
public class JdbcPool implements DataSource {

	/**
	 * listConnections 使用LinkedList集合来存放数据库连接， 由于要频繁读写List集合，所以使用LinkedList较为合适
	 */
	public static LinkedList<Connection> listConnections = new LinkedList<Connection>();

	/**
	 * 静态代码块中创建连接池中的数据库连接 jdbcPoolInitSize：初始化的连接池中连接个数
	 */
	static {

		final String className = "com.mysql.jdbc.Driver";
		final String url = "jdbc:mysql://localhost:3306/myustb";
		final String name = "root";
		final String password = "root";
		final int jdbcPoolInitSize = 10;
		try {
			Class.forName(className);
			for (int i = 0; i < jdbcPoolInitSize; i++) {
				Connection conn = DriverManager.getConnection(url, name, password);
				System.out.println("获得链接:" + conn);
				listConnections.add(conn);
			}
			System.out.println("池中连接个数为：" + listConnections.size());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLoginTimeout(int arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 使用动态代理机制获取数据库连接
	 */
	@Override
	public Connection getConnection() throws SQLException {
		if (listConnections.size() > 0) {
			// 从集合中获取一个链接
			final Connection conn = listConnections.removeFirst();
			System.out.println("从池中获取连接：" + conn);
			System.out.println("池中连接个数为:" + listConnections.size());
			// 返回Connection对象的代理对象
			return (Connection) Proxy.newProxyInstance(JdbcPool.class.getClassLoader(), conn.getClass().getInterfaces(),
					new InvocationHandler() {
						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							if (!method.getName().equals("close")) {
								return method.invoke(conn, args);
							} else {
								// 如果调用close()方法，则将连接返还给数据库连接池
								listConnections.add(conn);
								System.out.println(conn + "被还给数据库连接池了。");
								System.out.println("池中连接个数为:" + listConnections.size());
								return null;
							}
						}
					});
		} else {
			System.out.println("连接繁忙，请等待");
			return null;
		}
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
