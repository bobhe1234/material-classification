package com.jswy.infrastructure.queue.disruptor;

import com.jswy.domain.generic.demo.model.MsgData;
import com.jswy.domain.support.BaseEvent;

/**
 * 注册'序列数据'的事件,事件携带对应的数据
 * 
 * @author admin
 *
 */
public class MsgDataEvent extends BaseEvent<MsgData> {
	private static final long serialVersionUID = 1166270252985849587L;
}