package com.vg.game.dao;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.vg.game.util.DBUtils;

/**
* <p>Title: BaseDao.java</p>
* <p>Description: dao层基类,可以借助于Apache Commons的DBUtils工具类，该类中并没有使用</p>
* <p>Copyright: Copyright (c) 2018</p>
* <p>Company: www.ziyuantech.com</p>
* @author cqjokers
* @date 2018年7月24日
*/
public class BaseDao<M> {

	public BaseDao() {
	}
	
	public int update(String sql, String... param) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtils.getConnection();
			ps = conn.prepareStatement(sql);
			int size = param.length;
			for(int i = 0;i < size;i++) {
				ps.setString(i+1, param[i]);
			}
			int ret = ps.executeUpdate();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(ps != null)
				ps.close();
			DBUtils.releaseConnection();
		}
		return 0;
	}


	public int delete(String sql, String... param) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtils.getConnection();
			ps = conn.prepareStatement(sql);
			int size = param.length;
			for(int i = 0;i < size;i++) {
				ps.setString(i+1, param[i]);
			}
			int ret = ps.executeUpdate();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(ps != null)
				ps.close();
			DBUtils.releaseConnection();
		}
		return 0;
	}


	public <M> List<M> query(String sql, String... param) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet ret = null;
		try {
			conn = DBUtils.getConnection();
			ps = conn.prepareStatement(sql);
			int size = param.length;
			for(int i = 0;i < size;i++) {
				ps.setString(i+1, param[i]);
			}
			ret = ps.executeQuery();
			if(ret != null) {
				List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
				ResultSetMetaData metaData = ret.getMetaData();
				int columnCount = metaData.getColumnCount();
				while(ret.next()) {
					for(int i = 1;i <= columnCount;i++) {
						String columnName = metaData.getColumnLabel(i);
						String value = ret.getString(columnName);
						Map<String, String> map = new HashMap<String,String>();
						map.put(columnName, value);
						dataList.add(map);
					}
				}
				String result = JSON.toJSONString(dataList);
				Class<M> tClass = (Class<M>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
				List<M> m1 = (List<M>) JSON.parseArray(result, tClass);
				return m1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(ret != null)
				ret.close();
			if(ps != null)
				ps.close();
			DBUtils.releaseConnection();
		}
		return null;
	}


	public <M> M queryOne(String sql, String... param) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet ret = null;
		try {
			conn = DBUtils.getConnection();
			ps = conn.prepareStatement(sql);
			int size = param.length;
			for(int i = 0;i < size;i++) {
				ps.setString(i+1, param[i]);
			}
			ret = ps.executeQuery();
			if(ret != null) {
				ResultSetMetaData metaData = ret.getMetaData();
				int columnCount = metaData.getColumnCount();
				String result = "";
				Map<String, String> map = new HashMap<String,String>();
				while(ret.next()) {
					for(int i = 1;i <= columnCount;i++) {
						String columnName = metaData.getColumnLabel(i);
						String value = ret.getString(columnName);
						map.put(columnName, value);
					}
				}
				result = JSON.toJSONString(map);
				Class<M> tClass = (Class<M>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
				M m1 = JSON.parseObject(result, tClass);
				return m1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(ret != null)
				ret.close();
			if(ps != null)
				ps.close();
			DBUtils.releaseConnection();
		}
		return null;
	}

	public int insert(String sql, String... param) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBUtils.getConnection();
			ps = conn.prepareStatement(sql);
			int size = param.length;
			for(int i = 0;i < size;i++) {
				ps.setString(i+1, param[i]);
			}
			int ret = ps.executeUpdate();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(ps != null)
				ps.close();
			DBUtils.releaseConnection();
		}
		return 0;
	}

}
