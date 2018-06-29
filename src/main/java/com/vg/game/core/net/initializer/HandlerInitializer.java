package com.vg.game.core.net.initializer;

import java.nio.ByteOrder;

import com.vg.game.core.net.MessageHandlerMap;

import io.netty.channel.ChannelPipeline;

/**
* <p>Title: HandlerInitializer.java</p>
* <p>Description: 消息处理器初始化</p>
* <p>Copyright: Copyright (c) 2018</p>
* @author moyang
* @date 2018年6月28日
 */
public interface HandlerInitializer {

	/**
	 * 添加处理器
	 * @param p
	 * @param messageHandlerMap
	 * @param byteOrder
	 */
	void initHandler(ChannelPipeline p,MessageHandlerMap messageHandlerMap,ByteOrder byteOrder);
}
