package com.vg.game.core.net.initializer;

import java.nio.ByteOrder;

import com.vg.game.core.net.MessageHandlerMap;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
* <p>Title: HFBootBinMessageInitializer.java</p>
* <p>Description: 初始化ChannelHandler</p>
* <p>Copyright: Copyright (c) 2018</p>
* @author moyang
* @date 2018年6月28日
 */
public class HFBootBinMessageInitializer extends ChannelInitializer<SocketChannel>{

	private final MessageHandlerMap messageHandlerMap;
	
	private final ByteOrder byteOrder;
	
	private final int maxFrameLength;
	
	private final int lengthFieldLength;
	
	private final HandlerInitializer handlerInitializer;
	
	public HFBootBinMessageInitializer(MessageHandlerMap messageHandlerMap) {
		this(messageHandlerMap,new DefaultHandlerInitializer(),ByteOrder.BIG_ENDIAN,2048,2);
	}
	
	/**
	 * @param messageHandlerMap 消息和处理器的映射表
	 * @param handlerInitializer 消息处理初始化器,默认初始化器{@link DefaultHandlerInitializer}
	 * @param byteOrder 大小端
	 * @param maxFrameLength 帧大小最大值
	 * @param lengthFieldLength 包头长度值 
	 */
	public HFBootBinMessageInitializer(MessageHandlerMap messageHandlerMap,HandlerInitializer handlerInitializer,ByteOrder byteOrder, int maxFrameLength, int lengthFieldLength) {
		this.messageHandlerMap=messageHandlerMap;
		this.handlerInitializer=handlerInitializer;
		this.byteOrder=byteOrder;
		this.maxFrameLength=maxFrameLength;
		this.lengthFieldLength=lengthFieldLength;
	}
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		p.addLast(new LengthFieldBasedFrameDecoder(byteOrder,
				maxFrameLength, 0, lengthFieldLength, 0, lengthFieldLength, true));
		p.addLast(new LengthFieldPrepender(byteOrder,lengthFieldLength, 0,
				false));
		handlerInitializer.initHandler(p, messageHandlerMap,byteOrder);
	}

}
