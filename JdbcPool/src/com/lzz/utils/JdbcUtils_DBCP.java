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
 * @Description 数据库连接工具类
 * @author CunsiALIEN
 *
 */
public class JdbcUtils_DBCP {
	/**
	 * 在java中，编写数据库连接池需实现java.sql.DataSource接口，每一种数据库连接池都是DataSource接口的实现
	 * DBCP连接池就是java.sql.DataSource接口的一个具体实现
	 */
	private static DataSource ds = null;
	//静态块代码中加载数据库连接池
	static {
		//加载配置文件
		InputStream in = JdbcUtils_DBCP.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
		Properties prop = new Properties();
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//创建数据源
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
		System.out.println("获得数据库连接");
		return ds.getConnection();
	}
	
	
	/**
	 * @Method release
	 * @description 释放资源
	 * 释放的资源包括Connection数据库连接对象，执行SQL命令的Statement对象，查询结果ResultSet对象
	 * 
	 * @param conn
	 * @param st
	 * @param rs
	 */
	public static void release(Connection conn,Statement st,ResultSet rs){
        if(rs!=null){
            try{
                //关闭存储查询结果的ResultSet对象
                rs.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
            rs = null;
        }
        if(st!=null){
            try{
                //关闭负责执行SQL命令的Statement对象
                st.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        if(conn!=null){
            try{
                //将Connection连接对象还给数据库连接池
                conn.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
