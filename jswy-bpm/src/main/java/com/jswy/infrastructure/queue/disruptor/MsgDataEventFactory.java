package com.jswy.infrastructure.queue.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * 构造事件工厂
 * 
 * @author admin
 * @create 2018-01-18 下午6:24
 * @email bobhe123@foxmail.com
 * @description
 */
public class MsgDataEventFactory implements EventFactory<MsgDataEvent> {

	@Override
	public MsgDataEvent newInstance() {
		return new MsgDataEvent();
	}
}