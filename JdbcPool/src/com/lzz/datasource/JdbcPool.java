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
 * ʹ�ö�̬�������ʵ�����ݿ����ӳط���
 * 
 * @author CunsiALIEN
 *
 */
public class JdbcPool implements DataSource {

	/**
	 * listConnections ʹ��LinkedList������������ݿ����ӣ� ����ҪƵ����дList���ϣ�����ʹ��LinkedList��Ϊ����
	 */
	public static LinkedList<Connection> listConnections = new LinkedList<Connection>();

	/**
	 * ��̬������д������ӳ��е����ݿ����� jdbcPoolInitSize����ʼ�������ӳ������Ӹ���
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
				System.out.println("�������:" + conn);
				listConnections.add(conn);
			}
			System.out.println("�������Ӹ���Ϊ��" + listConnections.size());
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
	 * ʹ�ö�̬������ƻ�ȡ���ݿ�����
	 */
	@Override
	public Connection getConnection() throws SQLException {
		if (listConnections.size() > 0) {
			// �Ӽ����л�ȡһ������
			final Connection conn = listConnections.removeFirst();
			System.out.println("�ӳ��л�ȡ���ӣ�" + conn);
			System.out.println("�������Ӹ���Ϊ:" + listConnections.size());
			// ����Connection����Ĵ������
			return (Connection) Proxy.newProxyInstance(JdbcPool.class.getClassLoader(), conn.getClass().getInterfaces(),
					new InvocationHandler() {
						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							if (!method.getName().equals("close")) {
								return method.invoke(conn, args);
							} else {
								// �������close()�����������ӷ��������ݿ����ӳ�
								listConnections.add(conn);
								System.out.println(conn + "���������ݿ����ӳ��ˡ�");
								System.out.println("�������Ӹ���Ϊ:" + listConnections.size());
								return null;
							}
						}
					});
		} else {
			System.out.println("���ӷ�æ����ȴ�");
			return null;
		}
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
