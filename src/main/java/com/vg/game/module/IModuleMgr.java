package com.vg.game.module;

/**
* <p>Title: IModuleMgr.java</p>
* <p>Description: 模块管理器接口</p>
* <p>Copyright: Copyright (c) 2018</p>
* @author moyang
* @date 2018年6月28日
 */
public interface IModuleMgr {

	/**
	 * 注册模块
	 * @param module 模块实例
	 */
	void register(IModule module);

	/**
	 * 取消注册模块，取消后则取不到该模块
	 * @param cls 模块类
	 */
	void unregister(Class<?> cls);

	/**
	 * 取得某个模块
	 * @param cls {@link #register(Class, IModule)}注册的模块类
	 * @return null 表示失败
	 */
	<T> T getModule(Class<T> cls);
}
