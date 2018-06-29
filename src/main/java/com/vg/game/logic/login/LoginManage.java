package com.vg.game.logic.login;

import com.vg.game.client.ConnClient;
import com.vg.game.core.net.MessageHandlerMap;
import com.vg.game.core.net.codec.BinMessage;
import com.vg.game.module.IModule;
import com.vg.game.module.IModuleMgr;
import com.vg.game.service.ServiceMgr;

public class LoginManage implements IModule{

	private static LoginManage instance;

	public static LoginManage getInstance() {
		if (instance == null)
			instance = new LoginManage();
		return instance;
	}

	public static void login(ConnClient client,BinMessage msg) {
		
	}
	
	@Override
	public void open(IModuleMgr moduleMgr) {
		MessageHandlerMap messageHandlerMap = ServiceMgr.getInstance().getMessageHandlerMap();
		//注册消息
//		messageHandlerMap.register(msgId, msg)
	}
}
