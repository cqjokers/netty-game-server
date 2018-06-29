package com.vg.game.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.ByteOrder;

import com.vg.game.core.concurrent.HFExecutorGroup;
import com.vg.game.core.hfboot.ServerHFBoot;
import com.vg.game.core.net.MessageHandlerMap;
import com.vg.game.core.net.initializer.HFBootBinMessageInitializer;
import com.vg.game.initializer.ClientHandlerInitalizer;
import com.vg.game.initializer.ModuleInitalizer;
import com.vg.game.module.ModuleMgr;

import io.netty.channel.Channel;
import io.netty.handler.logging.LoggingHandler;

/**
* <p>Title: ServiceMgr.java</p>
* <p>Description: 服务管理器</p>
* <p>Copyright: Copyright (c) 2018</p>
* @author moyang
* @date 2018年6月27日
 */
public class ServiceMgr {

	protected ServiceInitalizer initalizer;
	
	protected String serviceName;
	
	public ServerHFBoot serverHFBoot;
	
	protected HFExecutorGroup exeGroup;
	
	protected MessageHandlerMap messageHandlerMap;
	
	protected long currentTime;
	
	private static volatile ServiceMgr instance;

	public static ServiceMgr getInstance() {
		return instance;
	}
	
	public ServiceMgr(ServiceInitalizer initalizer) {
		if(instance == null) {
			instance = this;
			this.initalizer = initalizer;
			this.serviceName = initalizer.getName();
			serverHFBoot = new ServerHFBoot();
			exeGroup = new HFExecutorGroup(Runtime.getRuntime().availableProcessors() * 2);
			messageHandlerMap = new MessageHandlerMap();
		}
	}
	
	/**
	 * 取得服务启动器
	 * 
	 * @return serverHFBoot
	 */
	public ServerHFBoot getServerHFBoot() {
		return serverHFBoot;
	}
	/**
	 * 取得消息映射
	 * 
	 * @return messageHandlerMap
	 */
	public MessageHandlerMap getMessageHandlerMap() {
		return messageHandlerMap;
	}

	/**
	 * 取得计算线程线程池
	 * 
	 * @return exeGroup
	 */
	public HFExecutorGroup getExecutorGroup() {
		return exeGroup;
	}
	
	/**
	 * 初始化并启动服务
	 * 
	 * @param port
	 */
	public void run() {
		Runtime.getRuntime().addShutdownHook(new ShutDownHook());
		//注册模块
		ModuleInitalizer.init(ModuleMgr.getInstance());
		ModuleMgr.getInstance().openAllModel();
		runNetService();
	}
	
	private void runNetService() {
		serverHFBoot.handler(new LoggingHandler(initalizer.getLogLevel()))
				.childHandler(
						new HFBootBinMessageInitializer(messageHandlerMap,new ClientHandlerInitalizer(exeGroup),ByteOrder.BIG_ENDIAN,2048,2));

		try {
			Channel ch = serverHFBoot.bind(initalizer.getPort());

			System.out.println(serviceName + " Service start: "
					+ initalizer.getPort());
			System.out.println("Enter 'exit' to exit");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			for (;;) {
				String line = in.readLine();
				if (line != null && "exit".equalsIgnoreCase(line)) {
					break;
				}
			}
			System.out.println("ShuttingDown...");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Runtime.getRuntime().exit(0);
		}
	}
	
	protected void shutdown() {
		try {
			serverHFBoot.shutdownGracefully();
			exeGroup.shutdownGracefully();
			System.out.println("bye...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭线程
	 */
	final class ShutDownHook extends Thread {
		public ShutDownHook() {
			super("shutdown");
		}

		@Override
		public void run() {
			shutdown();
		}
	}

	public ServiceInitalizer getInitalizer() {
		return initalizer;
	}
}
