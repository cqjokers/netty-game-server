package com.vg.game.core.hfboot;

import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.MultithreadEventExecutorGroup;
import io.netty.util.concurrent.SingleThreadEventExecutor;

/**
* <p>Title: ServerHFBoot.java</p>
* <p>Description: 服务启动器</p>
* <p>Copyright: Copyright (c) 2018</p>
* @author moyang
* @date 2018年6月27日
 */
public class ServerHFBoot {

	private final ServerBootstrap bootstrap;
	
	private final EventLoopGroup bossGroup;
	
	private final EventLoopGroup workerGroup;
	
	public ServerHFBoot() {
		this(null, null);
	}
	
	public ServerHFBoot(EventLoopGroup bossGroup,EventLoopGroup workerGroup) {
		//先不用epoll
		boolean epollAvailable=false;//Epoll.isAvailable();
		if(bossGroup == null)
			bossGroup = new NioEventLoopGroup();
		if(workerGroup == null)
			workerGroup = new NioEventLoopGroup();
		bootstrap = new ServerBootstrap();
		this.workerGroup = workerGroup;
		this.bossGroup = bossGroup;
		
		bootstrap.group(bossGroup, workerGroup);
		bootstrap.option(ChannelOption.SO_REUSEADDR, true);
		bootstrap.option(ChannelOption.TCP_NODELAY, true);
		if(!epollAvailable) {
			bootstrap.option(ChannelOption.SO_SNDBUF, 1024*1024);
			bootstrap.option(ChannelOption.SO_RCVBUF, 1024*1024);
			bootstrap.channel(NioServerSocketChannel.class);
		} else {
			bootstrap.channel(EpollServerSocketChannel.class);
		}
	}
	
	/**
	 * 取得一个执行线程
	 * @return
	 */
	public EventLoop getWorkerLoop() {
		return workerGroup.next();
	}
	
	/**
	 * 取得boss组中挂起的任务
	 * @return
	 */
	public int bossPendingTasks() {
		int count = 0;
		MultithreadEventExecutorGroup loopGroup = (MultithreadEventExecutorGroup) bossGroup;
		Iterator<EventExecutor> it = loopGroup.iterator();
		while (it.hasNext()) {
			SingleThreadEventExecutor exe = (SingleThreadEventExecutor) it.next();
			count += exe.pendingTasks();
		}
		return count;
	}
	
	/**
	 * 取得worker组中的挂起的任务
	 * @return
	 */
	public int workerPendingTasks() {
		int count = 0;
		MultithreadEventExecutorGroup loopGroup = (MultithreadEventExecutorGroup) workerGroup;
		Iterator<EventExecutor> it = loopGroup.iterator();
		while (it.hasNext()){
			SingleThreadEventExecutor exe = (SingleThreadEventExecutor) it.next();
			count += exe.pendingTasks();
		}
		return count;
	}
	
	/**
	 * 用请求的的handler
	 * @param handler
	 * @return
	 */
	public ServerHFBoot handler(ChannelHandler handler) {
		bootstrap.handler(handler);
		return this;
	}
	
	/**
	 * 用于Channel的请求的handler
	 * @param childHandler
	 * @return this
	 */
	public ServerHFBoot childHandler(ChannelHandler childHandler) {
		bootstrap.childHandler(childHandler);
		return this;
	}
	
	/**
	 * 设置特殊的选项值,null会清除该值
	 * 
	 * @param option
	 *            ChannelOption
	 * @param value
	 *            对应的值
	 * @return this
	 */
	public <T> ServerHFBoot option(ChannelOption<T> option,T value) {
		bootstrap.option(option, value);
		return this;
	}
	
	/**
	 * 创建新的 {@link Channel} 并bind 它,会等待成功返回,或者失败抛出异常
	 * @param port
	 * @return
	 * @throws InterruptedException
	 */
	public Channel bind(int port) throws InterruptedException {
		Channel ch = bootstrap.bind(port).sync().channel();
		return ch;
	}
	
	/**
	 * 创建新的 {@link Channel} 并bind 它,会等待成功返回,或者失败抛出异常
	 * @param inetHost
	 * @param port
	 * @return
	 * @throws InterruptedException
	 */
	public Channel bind(InetAddress inetHost,int port) throws InterruptedException {
		Channel ch = bootstrap.bind(inetHost,port).sync().channel();
		return ch;
	}
	
	/**
	 * 创建新的 {@link Channel} 并bind 它,会等待成功返回,或者失败抛出异常
	 * @param localAddress
	 * @return
	 * @throws InterruptedException
	 */
	public Channel bind(SocketAddress localAddress) throws InterruptedException {
		Channel ch = bootstrap.bind(localAddress).sync().channel();
		return ch;
	}
	/**
	 * 创建新的 {@link Channel} 并bind 它,异步创建,不会等待返回
	 * @return {@link Channel}
	 * @throws InterruptedException 
	 */
	public ChannelFuture bindAsync(int inetPort){
		return bootstrap.bind(inetPort);
	}

	/**
	 * 创建新的 {@link Channel} 并bind 它,异步创建,不会等待返回
	 * @return {@link Channel}
	 * @throws InterruptedException 
	 */
	public ChannelFuture bindAsync(String inetHost, int inetPort){
		return bootstrap.bind(inetHost, inetPort);
	}

	/**
	 * 创建新的 {@link Channel} 并bind 它,异步创建,不会等待返回
	 * @return {@link Channel}
	 * @throws InterruptedException 
	 */
	public ChannelFuture bindAsync(InetAddress inetHost, int inetPort){
		return bootstrap.bind(inetHost, inetPort);
	}

	/**
	 * 创建新的 {@link Channel} 并bind 它,异步创建,不会等待返回
	 * @return {@link Channel}
	 * @throws InterruptedException 
	 */
	public ChannelFuture bindAsync(SocketAddress localAddress){
		return bootstrap.bind(localAddress);
	}
	
	/**
	 * 如果调用了{@link #shutdownGracefully()} 开始优雅的停止,或者已经停止都会返回true
	 * @return this
	 */
	public boolean isShuttingDown(){
		return bossGroup.isShuttingDown()&&workerGroup.isShuttingDown();
	}
	
	/**
	 * 优雅的停止 安静期1秒 超时2秒
	 */
	public void shutdownGracefully() {
		try {
			Future<?> fb = bossGroup.shutdownGracefully(1, 2, TimeUnit.SECONDS);
			Future<?> fw = workerGroup.shutdownGracefully(1, 2, TimeUnit.SECONDS);
			fb.await();
			fw.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 优雅的停止
	 * @param quietPeriod 安静期时间
	 * @param timeout 在安静期有请求时最大等待时间,超过该时间便会直接停止
	 * @param unit 时间单位
	 */
	public void shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit){
		try{
			Future<?> fb=bossGroup.shutdownGracefully(quietPeriod,timeout,unit);
			Future<?> fw=workerGroup.shutdownGracefully(quietPeriod,timeout,unit);
			
			fb.await();
			fw.await();
		}catch (InterruptedException e){
			e.printStackTrace();
		}
	}
}
