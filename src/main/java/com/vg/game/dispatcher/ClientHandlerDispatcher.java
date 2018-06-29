package com.vg.game.dispatcher;

import com.vg.game.client.ConnClient;
import com.vg.game.core.concurrent.HFExecutorGroup;
import com.vg.game.core.dispatcher.MessageHandlerDispatcher;
import com.vg.game.core.net.MessageHandlerMap;
import com.vg.game.core.net.MsgInfo;
import com.vg.game.core.net.codec.BinMessage;
import com.vg.game.core.net.codec.BinMsgCodec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
* <p>Title: ClientHandlerDispatcher.java</p>
* <p>Description: 客户端消息分发器</p>
* <p>Copyright: Copyright (c) 2018</p>
* @author moyang
* @date 2018年6月28日
 */
public class ClientHandlerDispatcher extends MessageHandlerDispatcher {
	
	protected HFExecutorGroup exeGroup;
	
	protected BinMsgCodec codec;
	
	protected ConnClient client;
	
	public ClientHandlerDispatcher(MessageHandlerMap messageHandlerMap,
			HFExecutorGroup exeGroup, BinMsgCodec codec) {
		super(messageHandlerMap);
		this.exeGroup = exeGroup;
		this.codec = codec;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//连接上
		client = new ConnClient(ctx,exeGroup.next());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		//断开连接
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		//处于空闲状态,需要对心跳进行处理
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			BinMessage binMsg = (BinMessage)msg;
			MsgInfo info = messageHandlerMap.getMsgInfo(binMsg.getMsgId());
			if(info != null) {
				if (info.getHandler()==null)
					throw new NullPointerException("MessageHandlerDispatcher no registered: "+msg.getClass().getSimpleName()+"'s handler");
				else
					info.getHandler().channelRead(client, msg, info.getMsgId());
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ReferenceCountUtil.release(msg);
		}
	}
	
	
}
