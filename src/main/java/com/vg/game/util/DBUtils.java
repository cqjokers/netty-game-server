package com.vg.game.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.vg.game.service.ServiceMgr;

/**
* <p>Title: DBUtils.java</p>
* <p>Description: 数据库工具类</p>
* <p>Copyright: Copyright (c) 2018</p>
* <p>Company: www.ziyuantech.com</p>
* @author cqjokers
* @date 2018年7月24日
 */
public class DBUtils {

	private static DataSource source = null;
	
	private static ThreadLocal<Connection> local = null;
	
	static {
		local = new ThreadLocal<Connection>();
		source = ServiceMgr.getInstance().getDataSource();
	}
	
	/**
	 * 获得当前线程的连接
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException{
		try {
			Connection conn = local.get();
			if(conn == null) {
				conn = source.getConnection();
				local.set(conn);
			}
			return conn;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 开启事务
	 * @throws SQLException
	 */
	public static void startTransaction() throws SQLException{
		try {
			Connection conn = local.get();
			if(conn == null) {
				conn = source.getConnection();
				local.set(conn);
			}
			conn.setAutoCommit(false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 提交事务
	 * @throws SQLException
	 */
	public static void CommitTransaction() throws SQLException {
		try {
			Connection conn = local.get();
			if(conn != null) 
				conn.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 关闭连接
	 * @throws SQLException
	 */
	public static void releaseConnection() throws SQLException{
		try {
			Connection conn = local.get();
			if(conn != null)
				conn.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			local.remove();
		}
	}
	
}
