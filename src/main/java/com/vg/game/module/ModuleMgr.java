package com.vg.game.module;

import java.util.HashMap;

/**
* <p>Title: ModuleMgr.java</p>
* <p>Description: 模块管理器</p>
* <p>Copyright: Copyright (c) 2018</p>
* @author moyang
* @date 2018年6月28日
 */
public class ModuleMgr implements IModuleMgr {

	protected HashMap<Class<?>, IModule> modelMap;

	private static volatile ModuleMgr instance;

	public static ModuleMgr getInstance(){
		if (instance == null){
			instance = new ModuleMgr();
		}
		return instance;
	}
	
	private ModuleMgr(){
		modelMap = new HashMap<Class<?>, IModule>();
	}
	
	@Override
	public void register(IModule module) {
		modelMap.put(module.getClass(), module);
	}

	@Override
	public void unregister(Class<?> cls) {
		modelMap.remove(cls);
	}

	@Override
	public <T> T getModule(Class<T> cls) {
		return cls.cast(modelMap.get(cls));
	}
	
	/**
	 * 开启所有模块
	 */
	public void openAllModel(){
		for (IModule module : modelMap.values()){
			module.open(this);
		}
	}
}
