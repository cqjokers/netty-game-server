package com.vg.game.core.net.codec;

import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;

/**
* <p>Title: BinMessage.java</p>
* <p>Description: 二进制消息</p>
* <p>Copyright: Copyright (c) 2018</p>
* @author moyang
* @date 2018年6月28日
 */
public abstract class BinMessage implements Imessage {

	// 消息号
	private short msgId;

	public short getMsgId() {
		return msgId;
	}

	public void setMsgId(short msgId) {
		this.msgId = msgId;
	}

	public boolean readBool(ByteBuf buf) {
		return buf.readBoolean();
	}

	public byte readByte(ByteBuf buf) {
		return buf.readByte();
	}

	public short readShort(ByteBuf buf) {
		return buf.readShort();
	}

	public int readInt(ByteBuf buf) {
		return buf.readInt();
	}

	public float readFloat(ByteBuf buf) {
		return buf.readFloat();
	}

	public long readLong(ByteBuf buf) {
		return buf.readLong();
	}

	public double readDouble(ByteBuf buf) {
		return buf.readDouble();
	}

	/**
	 * 读取字符串时，数据格式为: 数据长度｜数据 如果长度为0则返回空字符串
	 * 
	 * @param buf
	 * @return
	 */
	public String readString(ByteBuf buf) {
		int len = buf.readShort();
		if (len > 0) {
			String ret = buf.toString(buf.readerIndex(), len, CharsetUtil.UTF_8);
			buf.readerIndex(buf.readerIndex() + len);
			return ret;
		}
		return "";
	}

	public void writeBool(ByteBuf buf, boolean v) {
		buf.writeBoolean(v);
	}

	public void writeByte(ByteBuf buf, int v) {
		buf.writeByte(v);
	}

	public void writeShort(ByteBuf buf, int v) {
		buf.writeShort(v);
	}

	public void writeInt(ByteBuf buf, int v) {
		buf.writeInt(v);
	}

	public void writeFloat(ByteBuf buf, float v) {
		buf.writeFloat(v);
	}

	public void writeLong(ByteBuf buf, long v) {
		buf.writeLong(v);
	}

	public void writeDouble(ByteBuf buf, double v) {
		buf.writeDouble(v);
	}
	
	public void writeString(ByteBuf buf,String v) {
		if(v != null) {
			byte[] data = v.getBytes(CharsetUtil.UTF_8);
			buf.writeShort(data.length);
			buf.writeBytes(data);
		} else {
			buf.writeShort(0);
		}
	}
}
