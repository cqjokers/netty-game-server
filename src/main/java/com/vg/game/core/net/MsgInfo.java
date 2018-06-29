package com.vg.game.core.net;

import com.vg.game.core.net.codec.Imessage;
import com.vg.game.core.net.handler.IMessageHandler;

/**
* <p>Title: MsgInfo.java</p>
* <p>Description: 消息信息</p>
* <p>Copyright: Copyright (c) 2018</p>
* @author moyang
* @date 2018年6月28日
 */
public class MsgInfo {

	private Class<?> type;
	
	private Imessage msgInstance;
	
	private int msgId;//消息号
	
	private IMessageHandler handler;

	public Class<?> getType() {
		return type;
	}

	public Imessage getMsgInstance() {
		return msgInstance;
	}

	public int getMsgId() {
		return msgId;
	}

	public IMessageHandler getHandler() {
		return handler;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public void setMsgInstance(Imessage msgInstance) {
		this.msgInstance = msgInstance;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public void setHandler(IMessageHandler handler) {
		this.handler = handler;
	}
	
}
