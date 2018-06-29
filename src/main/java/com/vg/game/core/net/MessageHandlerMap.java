package com.vg.game.core.net;

import java.util.HashMap;

import com.vg.game.core.net.codec.Imessage;
import com.vg.game.core.net.handler.IMessageHandler;

/**
* <p>Title: MessageHandlerMap.java</p>
* <p>Description: 消息映射器</p>
* <p>Copyright: Copyright (c) 2018</p>
* @author moyang
* @date 2018年6月28日
 */
public class MessageHandlerMap {

	private HashMap<Class<?>, MsgInfo> classToMsgMap;
	
	private HashMap<Integer, MsgInfo> idToMsgMap;
	
	public MessageHandlerMap() {
		classToMsgMap = new HashMap<Class<?>, MsgInfo>();
		idToMsgMap = new HashMap<Integer, MsgInfo>();
	}
	
	/**
	 * 消息注册
	 * @param msgId 消息号
	 * @param msg 消息
	 * @return
	 */
	public MessageHandlerMap register(int msgId,Imessage msg) {
		MsgInfo info = new MsgInfo();
		info.setType(msg.getClass());
		info.setMsgId(msgId);
		info.setMsgInstance(msg);
		classToMsgMap.put(info.getType(), info);
		idToMsgMap.put(msgId, info);
		return this;
	}
	
	/**
	 * 消息注册 
	 * @param msgId 消息号
	 * @param msg 消息
	 * @param handler 消息处理器
	 * @return
	 */
	public MessageHandlerMap register(int msgId,Imessage msg,IMessageHandler handler) {
		MsgInfo info = new MsgInfo();
		info.setType(msg.getClass());
		info.setMsgInstance(msg);
		info.setMsgId(msgId);
		info.setHandler(handler);
		idToMsgMap.put(msgId, info);
		classToMsgMap.put(info.getType(), info);
		return this;
	}
	
	/**
	 * 获取消息信息
	 * @param msg
	 * @return
	 */
	public MsgInfo getMsgInfo(Imessage msg) {
		MsgInfo info = classToMsgMap.get(msg.getClass());
		if(info == null) {
			throw new NullPointerException("BinMessageHandlerMap no registered: "+msg.getClass().getSimpleName()+"'s info");
		}
		return info;
	}
	
	/**
	 * 获取消息信息
	 * @param type
	 * @return
	 */
	public MsgInfo getMsgInfo(Class<?> type) {
		MsgInfo info = classToMsgMap.get(type);
		if(info == null) {
			throw new NullPointerException("BinMessageHandlerMap no registered: "+type.getSimpleName()+"'s info");
		}
		return info;
	}
	
	/**
	 * 获取消息
	 * @param msgId
	 * @return
	 */
	public MsgInfo getMsgInfo(int msgId) {
		MsgInfo info = idToMsgMap.get(msgId);
		if (info == null) {
			throw new NullPointerException("BinMessageHandlerMap no registered: "+msgId+"=0x"+Integer.toHexString(msgId)+"'s info");
		}
		return info;
	}
}
