package com.vg.game.core.dispatcher;

import com.vg.game.core.net.MessageHandlerMap;
import com.vg.game.core.net.MsgInfo;
import com.vg.game.core.net.codec.BinMessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
* <p>Title: MessageHandlerDispatcher.java</p>
* <p>Description: 消息处理分发器</p>
* <p>Copyright: Copyright (c) 2018</p>
* @author moyang
* @date 2018年6月27日
 */
public class MessageHandlerDispatcher extends ChannelInboundHandlerAdapter {

	protected final MessageHandlerMap messageHandlerMap;
	
	public MessageHandlerDispatcher(MessageHandlerMap messageHandlerMap) {
		this.messageHandlerMap = messageHandlerMap;
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
					info.getHandler().channelRead(ctx, msg, info.getMsgId());
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ReferenceCountUtil.release(msg);
		}
	}
	
	
}
