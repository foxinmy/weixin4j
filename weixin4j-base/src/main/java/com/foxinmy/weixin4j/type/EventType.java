package com.foxinmy.weixin4j.type;

/**
 * 事件类型
 * 
 * @className EventType
 * @author jy
 * @date 2014年9月30日
 * @since JDK 1.7
 * @see
 */
public enum EventType {
	/**
	 * 关注事件
	 * 
	 */
	subscribe,
	/**
	 * 取消关注事件
	 * 
	 */
	unsubscribe,
	/**
	 * 上报地理位置事件
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.LocationEventMessage
	 */
	location,
	/**
	 * 菜单扫描事件
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.menu.MenuScanEventMessage
	 */
	scancode_push,
	/**
	 * 菜单点击关键字事件
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.menu.MenuEventMessage
	 */
	view,
	/**
	 * 菜单点击链接事件
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.menu.MenuEventMessage
	 */
	click,
	/**
	 * 菜单扫描并调出等待界面事件
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.menu.MenuScanEventMessage
	 */
	scancode_waitmsg,
	/**
	 * 菜单弹出拍照事件
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.menu.MenuPhotoEventMessage
	 */
	pic_sysphoto,
	/**
	 * 菜单弹出发图事件
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.menu.MenuPhotoEventMessage
	 */
	pic_photo_or_album,
	/**
	 * 菜单弹出发图事件
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.menu.MenuPhotoEventMessage
	 */
	pic_weixin,
	/**
	 * 菜单发送地理位置事件
	 * 
	 * @see com.foxinmy.weixin4j.msg.event.menu.MenuLocationEventMessage
	 */
	location_select;
}
