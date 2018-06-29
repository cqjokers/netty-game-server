package com.vg.game.logic.login;

import com.vg.game.adapter.MessageHandlerAdapter;
import com.vg.game.client.ConnClient;

public class LoginHandler extends MessageHandlerAdapter<M> {

	@Override
	public void handleMessage(ConnClient client, M msg) {
		LoginManage.login(client, msg);
	}

}
