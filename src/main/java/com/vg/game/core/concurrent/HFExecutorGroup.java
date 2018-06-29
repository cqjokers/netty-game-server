package com.vg.game.core.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
* <p>Title: HFExecutorGroup.java</p>
* <p>Description: 固定数目线程池，通过hash获取线程</p>
* <p>Copyright: Copyright (c) 2018</p>
* @author moyang
* @date 2018年6月27日
 */
public class HFExecutorGroup {

	protected final AtomicInteger exeIndex = new AtomicInteger();
	
	protected ExecutorService[] executors;
	
	protected ExecutorServiceChooser chooser;
	
	protected interface ExecutorServiceChooser{
		ExecutorService hash(Object key);
		ExecutorService next();
	}
	
	public HFExecutorGroup() {
		this(Runtime.getRuntime().availableProcessors());
	}
	
	public HFExecutorGroup(int threads) {
		this.executors = new ExecutorService[threads];
		for(int i = 0;i < threads;i++) {
			executors[i] = Executors.newSingleThreadExecutor(new GroupThreadFactory(i));
		}
		if(isPowerOfTwo(executors.length)) {
			chooser = new PowerOfTwoExecutorServiceChooser();
		} else {
			chooser = new GenericExecutorServiceChooser();
		}
	}
	
	/**
	 * 获得一个线程
	 * @return
	 */
	public ExecutorService next() {
		return chooser.next();
	}
	
	/**
	 * 根据hash值获得一个线程
	 * @param key
	 * @return
	 */
	public ExecutorService get(Object key) {
		return chooser.hash(key);
	}
	
	/**
	 * 取得线程池的数量
	 * @return
	 */
	public int size() {
		return executors.length;
	}
	
	/**
	 * 是否全部停止了
	 * @return
	 */
	public boolean isShutDown() {
		for(int i = 0;i < executors.length;i++) {
			if(!executors[i].isShutdown())
				return false;
		}
		return true;
	}
	
	/**
	 * 是否全部停止了
	 * @return
	 */
	public boolean isTerminated() {
		for(int i = 0;i < executors.length;i++) {
			if(!executors[i].isTerminated())
				return false;
		}
		return true;
	}
	
	/**
	 * 优雅的停止，默认超时时间5秒
	 */
	public void shutdownGracefully() {
		shutdownGracefully(5,TimeUnit.SECONDS);
	}
	
	/**
	 * 优雅的停止
	 * @param timeout 允许所有已提交的任务（包括正在跑的和队列中等待的）执行完的最大等待时间，等待超时时间
	 * @param unit 时间单位
	 */
	public void shutdownGracefully(long timeout,TimeUnit unit) {
		try {
			for(int i = 0;i < executors.length;i++) {
				executors[i].shutdown();
				executors[i].awaitTermination(timeout, unit);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 2的次方
	 * @param val
	 * @return
	 */
	protected static boolean isPowerOfTwo(int val) {
		return (val & val - 1) == 0;
	}
	
	protected final class PowerOfTwoExecutorServiceChooser implements ExecutorServiceChooser{

		@Override
		public ExecutorService hash(Object key) {
			//如果executors长度是2的幂时，采用此算法比取模效率更高,碰撞几率更低
			return executors[key.hashCode() & executors.length - 1];
		}

		@Override
		public ExecutorService next() {
			return executors[exeIndex.getAndIncrement() & executors.length - 1];
		}
		
	}
	
	protected final class GenericExecutorServiceChooser implements ExecutorServiceChooser {

		@Override
		public ExecutorService hash(Object key) {
			return executors[Math.abs(key.hashCode()) % executors.length];
		}

		@Override
		public ExecutorService next() {
			return executors[exeIndex.getAndIncrement() % executors.length];
		}
		
	}
	
	final class GroupThreadFactory implements ThreadFactory{
		private AtomicInteger count=new AtomicInteger();
		private int group;
		
		public GroupThreadFactory(int group) {
			this.group = group;
		}
		
		@Override
		public Thread newThread(Runnable r) {
			Thread ret = new Thread(r,"hf-exegroup-"+group+"-"+count.incrementAndGet());
			return ret;
		}
	}
}
