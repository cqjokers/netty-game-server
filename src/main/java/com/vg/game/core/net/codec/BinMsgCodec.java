package com.vg.game.core.net.codec;

import java.nio.ByteOrder;
import java.util.List;
import com.vg.game.core.net.MessageHandlerMap;
import com.vg.game.core.net.MsgInfo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

/**
* <p>Title: BinMsgCodec.java</p>
* <p>Description: 二进制数据编码与解码</p>
* <p>Copyright: Copyright (c) 2018</p>
* @author moyang
* @date 2018年6月28日
 */
public class BinMsgCodec extends ByteToMessageCodec<Imessage> {

	private final ByteOrder byteOrder;

	private final MessageHandlerMap messageHandlerMap;
	
	public BinMsgCodec(MessageHandlerMap messageHandlerMap) {
		this(messageHandlerMap, ByteOrder.BIG_ENDIAN);
	}

	public BinMsgCodec(MessageHandlerMap messageHandlerMap, ByteOrder byteOrder) {

		this.messageHandlerMap = messageHandlerMap;
		if (byteOrder == null) {
			throw new NullPointerException("byteOrder");
		}
		this.byteOrder = byteOrder;
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Imessage msg, ByteBuf out) throws Exception {
		try {
			out = out.order(byteOrder);
			MsgInfo info = messageHandlerMap.getMsgInfo(msg);
			out.writeShort(info.getMsgId());
			msg.encode(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		try {
			in = in.order(byteOrder);
			int msgId = in.readShort();
			MsgInfo info = messageHandlerMap.getMsgInfo(msgId);
			Imessage msg = info.getMsgInstance().newInstance();
			msg.decode(in);
			if (in.readableBytes() > 0) {
				throw new Exception("more data in:" + Integer.toHexString(msgId));
			}
			BinMessage binMsg = (BinMessage)msg;
			binMsg.setMsgId((short) (msgId & 0xffff));
			out.add(binMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
