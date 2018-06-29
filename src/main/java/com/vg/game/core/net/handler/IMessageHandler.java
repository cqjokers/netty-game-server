package com.vg.game.core.net.handler;

/**
* <p>Title: IMessageHandler.java</p>
* <p>Description: 消息处理器</p>
* <p>Copyright: Copyright (c) 2018</p>
* @author moyang
* @date 2018年6月28日
 */
public interface IMessageHandler {
	
	/**
	 * 接收消息转发
	 * @param ctx
	 * @param msg
	 * @param msgID
	 * @throws Exception
	 */
	void channelRead(Object ctx, Object msg,int msgID) throws Exception;
}
