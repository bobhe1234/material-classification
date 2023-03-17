package com.jswy.domain.generic.demo.model;

/**
 * '序列'数据(代表应用A发送给应用B的消息)
 * 
 * @author admin
 *
 */
public class MsgData {
	/**
	 * 设备信息
	 */
	private String deviceInfoStr;

	public MsgData() {
	}

	public MsgData(String deviceInfoStr) {
		this.deviceInfoStr = deviceInfoStr;
	}

	public String getDeviceInfoStr() {
		return deviceInfoStr;
	}

	public void setDeviceInfoStr(String deviceInfoStr) {
		this.deviceInfoStr = deviceInfoStr;
	}

	@Override
	public String toString() {
		return "SeriesData{" + "deviceInfoStr='" + deviceInfoStr + '\'' + '}';
	}
}