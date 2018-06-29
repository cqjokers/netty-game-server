package com.vg.game.adapter;

import com.vg.game.client.ConnClient;
import com.vg.game.core.net.codec.BinMessage;
import com.vg.game.core.net.handler.IMessageHandler;

/**
* <p>Title: MessageHandlerAdapter.java</p>
* <p>Description: 消息适配器</p>
* <p>Copyright: Copyright (c) 2018</p>
* @author moyang
* @date 2018年6月28日
 */
public abstract class MessageHandlerAdapter<M extends BinMessage> implements IMessageHandler {

	@Override
	public void channelRead(Object ctx, Object msg, int msgID) throws Exception {
		final M objMsg = (M)msg;
		final ConnClient client = (ConnClient)ctx;
		Runnable task = new Runnable() {
			
			@Override
			public void run() {
				long s = System.nanoTime();
				handleMessage(client, objMsg);
				long p = System.nanoTime() - s;
				System.out.println(msgID+":对应消息处理耗时-"+p+" us");
			}
		};
		//放入客户端自己的线程中去执行
		client.execute(task);
	}
	
	//所有逻辑处理都在此方法中进行
	public abstract void handleMessage(ConnClient client, M msg);
	
	
}
