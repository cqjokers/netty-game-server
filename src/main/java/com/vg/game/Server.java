package com.vg.game;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.vg.game.service.ServiceInitalizer;
import com.vg.game.service.ServiceMgr;

/**
* <p>Title: Server.java</p>
* <p>Description: 服务启动类</p>
* <p>Copyright: Copyright (c) 2018</p>
* @author moyang
* @date 2018年6月27日
*/
public class Server {
	static Logger log = Logger.getLogger(Server.class);
	public static void main(String[] args) {
		String gameProp = "game.properties";
		String dbcpProp = "dbcp.properties";
		String logProp = "log4j.properties";
		if(args.length > 0) 
			gameProp = args[0];

		if (args.length > 1)
			dbcpProp = args[1];

		if (args.length > 2)
			logProp = args[2];
		
		try {
			PropertyConfigurator.configure(logProp);
			ServiceMgr serviceMgr = new ServiceMgr(new ServiceInitalizer(gameProp,dbcpProp));
			serviceMgr.run();
		}catch (Exception e){
			e.printStackTrace();
			Runtime.getRuntime().exit(0);
		}
	}
	
}