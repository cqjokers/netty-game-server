package com.vg.game.core.net.codec;

import io.netty.buffer.ByteBuf;

/**
* <p>Title: Imessage.java</p>
* <p>Description: 二进制消息编码解码接口</p>
* <p>Copyright: Copyright (c) 2018</p>
* @author moyang
* @date 2018年6月28日
 */
public interface Imessage {
	
	/**
	 * 编码
	 * @param out
	 * @throws Exception
	 */
	void encode(ByteBuf out) throws Exception;
	
	/**
	 * 解码
	 * @param in
	 * @throws Exception
	 */
	void decode(ByteBuf in) throws Exception;
	
	/**
	 * 新的实例
	 * @return IMessage
	 */
	Imessage newInstance();
}
