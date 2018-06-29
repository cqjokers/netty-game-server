package com.vg.game.service;

import java.io.FileInputStream;
import java.util.Properties;

import io.netty.handler.logging.LogLevel;

/**
* <p>Title: ServiceInitalizer.java</p>
* <p>Description: 服务配置初始化</p>
* <p>Copyright: Copyright (c) 2018</p>
* @author moyang
* @date 2018年6月27日
 */
public class ServiceInitalizer {

	private Properties properties;
	
	private String ip;
	
	private int port;
	
	private String name;
	
	private LogLevel loglv = LogLevel.ERROR;
	
	public ServiceInitalizer(String prop) throws Exception {
		FileInputStream fis = new FileInputStream(prop);
		properties = new Properties();
		properties.load(fis);
		
		ip = properties.getProperty("server.ip");
		name = properties.getProperty("server.name");
		port = Integer.valueOf(properties.getProperty("server.port"));
		String netlog = properties.getProperty("server.netlog");
		if (netlog.equals("debug")) {
			loglv = LogLevel.DEBUG;
		} else if (netlog.equals("info")) {
			loglv = LogLevel.INFO;
		} else if (netlog.equals("warn")) {
			loglv = LogLevel.WARN;
		} else if (netlog.equals("error")) {
			loglv = LogLevel.ERROR;
		}
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public String getName() {
		return name;
	}

	public LogLevel getLogLevel() {
		return loglv;
	}
}

