package com.vg.game.core.net.initializer;

import java.nio.ByteOrder;

import com.vg.game.core.dispatcher.MessageHandlerDispatcher;
import com.vg.game.core.net.MessageHandlerMap;
import com.vg.game.core.net.codec.BinMsgCodec;

import io.netty.channel.ChannelPipeline;

/**
* <p>Title: DefaultHandlerInitializer.java</p>
* <p>Description: 默认初始化</p>
* <p>Copyright: Copyright (c) 2018</p>
* @author moyang
* @date 2018年6月28日
 */
public class DefaultHandlerInitializer implements HandlerInitializer {

	@Override
	public void initHandler(ChannelPipeline p, MessageHandlerMap messageHandlerMap, ByteOrder byteOrder) {
		p.addLast(new BinMsgCodec(messageHandlerMap,byteOrder));
		p.addLast(new MessageHandlerDispatcher(messageHandlerMap));
	}

}
