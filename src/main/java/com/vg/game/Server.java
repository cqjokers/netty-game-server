package com.vg.game;

/**
* <p>Title: Server.java</p>
* <p>Description: 服务启动类</p>
* <p>Copyright: Copyright (c) 2018</p>
* @author moyang
* @date 2018年6月27日
*/
public class Server {
	
	public static void main(String[] args) {
		String gameProp = "game.properties";
		if(args.length > 0) {
			gameProp = args[0];
		}
		
	}
	
}
