package com.lzz.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

/**
 * @ClassName JdbcUtils_DBCP
 * @Description ���ݿ����ӹ�����
 * @author CunsiALIEN
 *
 */
public class JdbcUtils_DBCP {
	/**
	 * ��java�У���д���ݿ����ӳ���ʵ��java.sql.DataSource�ӿڣ�ÿһ�����ݿ����ӳض���DataSource�ӿڵ�ʵ��
	 * DBCP���ӳؾ���java.sql.DataSource�ӿڵ�һ������ʵ��
	 */
	private static DataSource ds = null;
	//��̬������м������ݿ����ӳ�
	static {
		//���������ļ�
		InputStream in = JdbcUtils_DBCP.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
		Properties prop = new Properties();
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//��������Դ
		try {
			ds = BasicDataSourceFactory.createDataSource(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @throws SQLException 
	 * 
	 */
	public static Connection getConnection() throws SQLException {
		System.out.println("������ݿ�����");
		return ds.getConnection();
	}
	
	
	/**
	 * @Method release
	 * @description �ͷ���Դ
	 * �ͷŵ���Դ����Connection���ݿ����Ӷ���ִ��SQL�����Statement���󣬲�ѯ���ResultSet����
	 * 
	 * @param conn
	 * @param st
	 * @param rs
	 */
	public static void release(Connection conn,Statement st,ResultSet rs){
        if(rs!=null){
            try{
                //�رմ洢��ѯ�����ResultSet����
                rs.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
            rs = null;
        }
        if(st!=null){
            try{
                //�رո���ִ��SQL�����Statement����
                st.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        if(conn!=null){
            try{
                //��Connection���Ӷ��󻹸����ݿ����ӳ�
                conn.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
