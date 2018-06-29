package com.vg.game.initializer;

import java.nio.ByteOrder;

import com.vg.game.client.ConnClient;
import com.vg.game.core.concurrent.HFExecutorGroup;
import com.vg.game.core.net.MessageHandlerMap;
import com.vg.game.core.net.codec.BinMsgCodec;
import com.vg.game.core.net.initializer.HandlerInitializer;
import com.vg.game.dispatcher.ClientHandlerDispatcher;

import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;

/**
* <p>Title: ClientHandlerInitalizer.java</p>
* <p>Description: handler初始化</p>
* <p>Copyright: Copyright (c) 2018</p>
* @author moyang
* @date 2018年6月28日
 */
public class ClientHandlerInitalizer implements HandlerInitializer {

	protected HFExecutorGroup exeGroup;
	
	public ClientHandlerInitalizer(HFExecutorGroup exeGroup) {
		this.exeGroup=exeGroup;
	}
	
	@Override
	public void initHandler(ChannelPipeline p, MessageHandlerMap messageHandlerMap, ByteOrder byteOrder) {
		//心跳
		p.addLast(new IdleStateHandler(ConnClient.IDLE_TIME, ConnClient.IDLE_TIME, ConnClient.IDLE_TIME));
		BinMsgCodec codec=new BinMsgCodec(messageHandlerMap,byteOrder);
		p.addLast(codec);
		p.addLast(new ClientHandlerDispatcher(messageHandlerMap,exeGroup,codec));
	}

}
