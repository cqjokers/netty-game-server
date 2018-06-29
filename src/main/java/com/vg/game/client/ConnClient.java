package com.vg.game.client;

import java.util.concurrent.ExecutorService;

import com.vg.game.service.ServiceMgr;

import io.netty.channel.ChannelHandlerContext;

/**
* <p>Title: ConnClient.java</p>
* <p>Description: 客户端连接</p>
* <p>Copyright: Copyright (c) 2018</p>
* @author moyang
* @date 2018年6月28日
 */
public class ConnClient {

	private ChannelHandlerContext ctx;
	
	private ExecutorService exe;
	
	public static final int IDLE_TIME = ServiceMgr.getInstance().getInitalizer().getIdleTime();
	
	public ConnClient(ChannelHandlerContext ctx, ExecutorService exe){
		this.ctx = ctx;
		this.exe = exe;
	}
	
	public void execute(Runnable task) {
		exe.execute(task);
	}
}
