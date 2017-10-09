package com.foxinmy.weixin4j.jssdk;

/**
 * JSSDK接口列表
 * 
 * @className JSSDKAPI
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年12月23日
 * @since JDK 1.6
 * @see <a href=
 *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115&token=&lang=zh_CN">
 *      公众平台JSSDK</a>
 * @see <a href=
 *      "http://qydev.weixin.qq.com/wiki/index.php?title=%E5%BE%AE%E4%BF%A1JS-SDK%E6%8E%A5%E5%8F%A3">
 *      企业号JSSDK</a>
 */
public enum JSSDKAPI {
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E8.8E.B7.E5.8F.96.E2.80.9C.E5.88.86.E4.BA.AB.E5.88.B0.E6.9C.8B.E5.8F.8B.E5.9C.88.E2.80.9D.E6.8C.89.E9.92.AE.E7.82.B9.E5.87.BB.E7.8A.B6.E6.80.81.E5.8F.8A.E8.87.AA.E5.AE.9A.E4.B9.89.E5.88.86.E4.BA.AB.E5.86.85.E5.AE.B9.E6.8E.A5.E5.8F.A3"
	 * >分享接口-获取“分享到朋友圈”按钮点击状态及自定义分享内容接口</a>
	 */
	onMenuShareTimeline,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E8.8E.B7.E5.8F.96.E2.80.9C.E5.88.86.E4.BA.AB.E7.BB.99.E6.9C.8B.E5.8F.8B.E2.80.9D.E6.8C.89.E9.92.AE.E7.82.B9.E5.87.BB.E7.8A.B6.E6.80.81.E5.8F.8A.E8.87.AA.E5.AE.9A.E4.B9.89.E5.88.86.E4.BA.AB.E5.86.85.E5.AE.B9.E6.8E.A5.E5.8F.A3"
	 * >分享接口-获取“分享给朋友”按钮点击状态及自定义分享内容接口</a>
	 */
	onMenuShareAppMessage,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E8.8E.B7.E5.8F.96.E2.80.9C.E5.88.86.E4.BA.AB.E5.88.B0QQ.E2.80.9D.E6.8C.89.E9.92.AE.E7.82.B9.E5.87.BB.E7.8A.B6.E6.80.81.E5.8F.8A.E8.87.AA.E5.AE.9A.E4.B9.89.E5.88.86.E4.BA.AB.E5.86.85.E5.AE.B9.E6.8E.A5.E5.8F.A3"
	 * >分享接口-获取“分享到QQ”按钮点击状态及自定义分享内容接口</a>
	 */
	onMenuShareQQ,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E8.8E.B7.E5.8F.96.E2.80.9C.E5.88.86.E4.BA.AB.E5.88.B0.E8.85.BE.E8.AE.AF.E5.BE.AE.E5.8D.9A.E2.80.9D.E6.8C.89.E9.92.AE.E7.82.B9.E5.87.BB.E7.8A.B6.E6.80.81.E5.8F.8A.E8.87.AA.E5.AE.9A.E4.B9.89.E5.88.86.E4.BA.AB.E5.86.85.E5.AE.B9.E6.8E.A5.E5.8F.A3"
	 * >分享接口-获取“分享到腾讯微博”按钮点击状态及自定义分享内容接口</a>
	 */
	onMenuShareWeibo,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E8.8E.B7.E5.8F.96.E2.80.9C.E5.88.86.E4.BA.AB.E5.88.B0QQ.E7.A9.BA.E9.97.B4.E2.80.9D.E6.8C.89.E9.92.AE.E7.82.B9.E5.87.BB.E7.8A.B6.E6.80.81.E5.8F.8A.E8.87.AA.E5.AE.9A.E4.B9.89.E5.88.86.E4.BA.AB.E5.86.85.E5.AE.B9.E6.8E.A5.E5.8F.A3"
	 * >分享接口-获取“分享到QQ空间”按钮点击状态及自定义分享内容接口</a>
	 */
	onMenuShareQZone,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E6.8B.8D.E7.85.A7.E6.88.96.E4.BB.8E.E6.89.8B.E6.9C.BA.E7.9B.B8.E5.86.8C.E4.B8.AD.E9.80.89.E5.9B.BE.E6.8E.A5.E5.8F.A3"
	 * >图像接口-拍照或从手机相册中选图接口</a>
	 */
	chooseImage,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E9.A2.84.E8.A7.88.E5.9B.BE.E7.89.87.E6.8E.A5.E5.8F.A3"
	 * >图像接口-预览图片接口</a>
	 */
	previewImage,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E4.B8.8A.E4.BC.A0.E5.9B.BE.E7.89.87.E6.8E.A5.E5.8F.A3"
	 * >图像接口-上传图片接口</a>
	 */
	uploadImage,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E4.B8.8B.E8.BD.BD.E5.9B.BE.E7.89.87.E6.8E.A5.E5.8F.A3"
	 * >图像接口-下载图片接口</a>
	 */
	downloadImage,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E5.BC.80.E5.A7.8B.E5.BD.95.E9.9F.B3.E6.8E.A5.E5.8F.A3"
	 * >音频接口-开始录音接口</a>
	 */
	startRecord,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E5.81.9C.E6.AD.A2.E5.BD.95.E9.9F.B3.E6.8E.A5.E5.8F.A3"
	 * >音频接口-停止录音接口</a>
	 */
	stopRecord,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E7.9B.91.E5.90.AC.E5.BD.95.E9.9F.B3.E8.87.AA.E5.8A.A8.E5.81.9C.E6.AD.A2.E6.8E.A5.E5.8F.A3"
	 * >音频接口-监听录音自动停止接口</a>
	 */
	onVoiceRecordEnd,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E6.92.AD.E6.94.BE.E8.AF.AD.E9.9F.B3.E6.8E.A5.E5.8F.A3"
	 * >音频接口-播放语音接口</a>
	 */
	playVoice,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E6.9A.82.E5.81.9C.E6.92.AD.E6.94.BE.E6.8E.A5.E5.8F.A3"
	 * >音频接口-暂停播放接口</a>
	 */
	pauseVoice,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E5.81.9C.E6.AD.A2.E6.92.AD.E6.94.BE.E6.8E.A5.E5.8F.A3"
	 * >音频接口-停止播放接口</a>
	 */
	stopVoice,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E7.9B.91.E5.90.AC.E8.AF.AD.E9.9F.B3.E6.92.AD.E6.94.BE.E5.AE.8C.E6.AF.95.E6.8E.A5.E5.8F.A3"
	 * >音频接口-监听语音播放完毕接口</a>
	 */
	onVoicePlayEnd,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E4.B8.8A.E4.BC.A0.E8.AF.AD.E9.9F.B3.E6.8E.A5.E5.8F.A3"
	 * >音频接口-上传语音接口</a>
	 */
	uploadVoice,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E4.B8.8B.E8.BD.BD.E8.AF.AD.E9.9F.B3.E6.8E.A5.E5.8F.A3"
	 * >音频接口-下载语音接口</a>
	 */
	downloadVoice,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E8.AF.86.E5.88.AB.E9.9F.B3.E9.A2.91.E5.B9.B6.E8.BF.94.E5.9B.9E.E8.AF.86.E5.88.AB.E7.BB.93.E6.9E.9C.E6.8E.A5.E5.8F.A3"
	 * >智能接口-识别音频并返回识别结果接口</a>
	 */
	translateVoice,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E8.8E.B7.E5.8F.96.E7.BD.91.E7.BB.9C.E7.8A.B6.E6.80.81.E6.8E.A5.E5.8F.A3"
	 * >设备信息-获取网络状态接口</a>
	 */
	getNetworkType,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E4.BD.BF.E7.94.A8.E5.BE.AE.E4.BF.A1.E5.86.85.E7.BD.AE.E5.9C.B0.E5.9B.BE.E6.9F.A5.E7.9C.8B.E4.BD.8D.E7.BD.AE.E6.8E.A5.E5.8F.A3"
	 * >地理位置-使用微信内置地图查看位置接口</a>
	 */
	openLocation,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E8.8E.B7.E5.8F.96.E5.9C.B0.E7.90.86.E4.BD.8D.E7.BD.AE.E6.8E.A5.E5.8F.A3"
	 * >地理位置-获取地理位置接口</a>
	 */
	getLocation,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E5.BC.80.E5.90.AF.E6.9F.A5.E6.89.BE.E5.91.A8.E8.BE.B9ibeacon.E8.AE.BE.E5.A4.87.E6.8E.A5.E5.8F.A3"
	 * >摇一摇周边-开启查找周边ibeacon设备接口</a>
	 */
	startSearchBeacons,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E5.85.B3.E9.97.AD.E6.9F.A5.E6.89.BE.E5.91.A8.E8.BE.B9ibeacon.E8.AE.BE.E5.A4.87.E6.8E.A5.E5.8F.A3"
	 * >摇一摇周边-关闭查找周边ibeacon设备接口</a>
	 */
	stopSearchBeacons,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E7.9B.91.E5.90.AC.E5.91.A8.E8.BE.B9ibeacon.E8.AE.BE.E5.A4.87.E6.8E.A5.E5.8F.A3"
	 * >摇一摇周边-监听周边ibeacon设备接口</a>
	 */
	onSearchBeacons,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E9.9A.90.E8.97.8F.E5.8F.B3.E4.B8.8A.E8.A7.92.E8.8F.9C.E5.8D.95.E6.8E.A5.E5.8F.A3"
	 * >界面操作-隐藏右上角菜单接口</a>
	 */
	hideOptionMenu,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E6.98.BE.E7.A4.BA.E5.8F.B3.E4.B8.8A.E8.A7.92.E8.8F.9C.E5.8D.95.E6.8E.A5.E5.8F.A3"
	 * >界面操作-显示右上角菜单接口</a>
	 */
	showOptionMenu,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E5.85.B3.E9.97.AD.E5.BD.93.E5.89.8D.E7.BD.91.E9.A1.B5.E7.AA.97.E5.8F.A3.E6.8E.A5.E5.8F.A3"
	 * >界面操作-关闭当前网页窗口接口</a>
	 */
	closeWindow,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E6.89.B9.E9.87.8F.E9.9A.90.E8.97.8F.E5.8A.9F.E8.83.BD.E6.8C.89.E9.92.AE.E6.8E.A5.E5.8F.A3"
	 * >界面操作-批量隐藏功能按钮接口</a>
	 */
	hideMenuItems,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E6.89.B9.E9.87.8F.E6.98.BE.E7.A4.BA.E5.8A.9F.E8.83.BD.E6.8C.89.E9.92.AE.E6.8E.A5.E5.8F.A3"
	 * >界面操作-批量显示功能按钮接口</a>
	 */
	showMenuItems,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E9.9A.90.E8.97.8F.E6.89.80.E6.9C.89.E9.9D.9E.E5.9F.BA.E7.A1.80.E6.8C.89.E9.92.AE.E6.8E.A5.E5.8F.A3"
	 * >界面操作-隐藏所有非基础按钮接口</a>
	 */
	hideAllNonBaseMenuItem,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E6.98.BE.E7.A4.BA.E6.89.80.E6.9C.89.E5.8A.9F.E8.83.BD.E6.8C.89.E9.92.AE.E6.8E.A5.E5.8F.A3"
	 * >界面操作-显示所有功能按钮接口</a>
	 */
	showAllNonBaseMenuItem,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E8.B0.83.E8.B5.B7.E5.BE.AE.E4.BF.A1.E6.89.AB.E4.B8.80.E6.89.AB.E6.8E.A5.E5.8F.A3"
	 * >微信扫一扫-调起微信扫一扫接口</a>
	 */
	scanQRCode,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E8.B7.B3.E8.BD.AC.E5.BE.AE.E4.BF.A1.E5.95.86.E5.93.81.E9.A1.B5.E6.8E.A5.E5.8F.A3"
	 * >微信小店-跳转微信商品页接口</a>
	 */
	openProductSpecificView,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E6.8B.89.E5.8F.96.E9.80.82.E7.94.A8.E5.8D.A1.E5.88.B8.E5.88.97.E8.A1.A8.E5.B9.B6.E8.8E.B7.E5.8F.96.E7.94.A8.E6.88.B7.E9.80.89.E6.8B.A9.E4.BF.A1.E6.81.AF"
	 * >微信卡券-拉取适用卡券列表并获取用户选择信息</a>
	 */
	chooseCard,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E6.89.B9.E9.87.8F.E6.B7.BB.E5.8A.A0.E5.8D.A1.E5.88.B8.E6.8E.A5.E5.8F.A3"
	 * >微信卡券-批量添加卡券接口</a>
	 */
	addCard,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E6.9F.A5.E7.9C.8B.E5.BE.AE.E4.BF.A1.E5.8D.A1.E5.8C.85.E4.B8.AD.E7.9A.84.E5.8D.A1.E5.88.B8.E6.8E.A5.E5.8F.A3"
	 * >微信卡券-查看微信卡包中的卡券接口</a>
	 */
	openCard,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E6.A0.B8.E9.94.80.E5.90.8E.E5.86.8D.E6.AC.A1.E8.B5.A0.E9.80.81.E5.8D.A1.E5.88.B8.E6.8E.A5.E5.8F.A3"
	 * >微信卡券-核销后再次赠送卡券接口</a>
	 */
	consumeAndShareCard,
	/***
	 * <a href=
	 * "http://mp.weixin.qq.com/wiki/11/74ad127cc054f6b80759c40f77ec03db.html#.E5.8F.91.E8.B5.B7.E4.B8.80.E4.B8.AA.E5.BE.AE.E4.BF.A1.E6.94.AF.E4.BB.98.E8.AF.B7.E6.B1.82"
	 * >微信支付-发起一个微信支付请求</a>
	 */
	chooseWXPay,
	/**
	 * <a href=
	 * "http://qydev.weixin.qq.com/wiki/index.php?title=%E5%BE%AE%E4%BF%A1JS-SDK%E6%8E%A5%E5%8F%A3#.E5.88.9B.E5.BB.BA.E4.BC.81.E4.B8.9A.E5.8F.B7.E4.BC.9A.E8.AF.9D"
	 * >企业号会话-创建企业号会话</a>
	 */
	openEnterpriseChat,
	
	/**
	 * <a href="http://qydev.weixin.qq.com/wiki/index.php?title=%E5%BE%AE%E4%BF%A1JS-SDK%E6%8E%A5%E5%8F%A3#.E6.89.93.E5.BC.80.E4.BC.81.E4.B8.9A.E9.80.9A.E8.AE.AF.E5.BD.95.E9.80.89.E4.BA.BA">企业号-选取联系人</a>
	 */
	openEnterpriseContact,
	selectEnterpriseContact,
	/**
	 * <a href=
	 * "http://qydev.weixin.qq.com/wiki/index.php?title=%E5%BE%AE%E4%BF%A1JS-SDK%E6%8E%A5%E5%8F%A3#.E5.90.91.E5.BD.93.E5.89.8D.E4.BC.81.E4.B8.9A.E4.BC.9A.E8.AF.9D.E5.8F.91.E9.80.81.E6.B6.88.E6.81.AF">企业号-向当前企业会话发送消息</a>
	 */
	sendEnterpriseChat;

	/**
	 * 分享接口集合
	 */
	public final static JSSDKAPI[] SHARE_APIS = { onMenuShareTimeline, onMenuShareAppMessage, onMenuShareQQ,
			onMenuShareWeibo, onMenuShareQZone };
	/**
	 * 图像接口集合
	 */
	public final static JSSDKAPI[] IMAGE_APIS = { chooseImage, previewImage, uploadImage, downloadImage };
	/**
	 * 音频接口集合
	 */
	public final static JSSDKAPI[] RECORD_APIS = { startRecord, stopRecord, onVoiceRecordEnd, playVoice, pauseVoice,
			stopVoice, onVoicePlayEnd, uploadVoice, downloadVoice };
	/**
	 * 智能接口集合
	 */
	public final static JSSDKAPI[] SEM_APIS = { translateVoice };
	/**
	 * 设备信息接口集合
	 */
	public final static JSSDKAPI[] DEVICE_APIS = { getNetworkType };
	/**
	 * 地理位置接口集合
	 */
	public final static JSSDKAPI[] LOCATION_APIS = { openLocation, getLocation };
	/**
	 * 摇一摇周边接口集合
	 */
	public final static JSSDKAPI[] BEACON_APIS = { startSearchBeacons, stopSearchBeacons, onSearchBeacons };
	/**
	 * 界面操作接口集合
	 */
	public final static JSSDKAPI[] UI_APIS = { hideOptionMenu, showOptionMenu, closeWindow, hideMenuItems,
			showMenuItems, hideAllNonBaseMenuItem, showAllNonBaseMenuItem };
	/**
	 * 微信扫一扫接口集合
	 */
	public final static JSSDKAPI[] SCAN_APIS = { scanQRCode };
	/**
	 * 微信小店接口集合
	 */
	public final static JSSDKAPI[] SHOP_APIS = { openProductSpecificView };
	/**
	 * 微信卡券接口集合
	 */
	public final static JSSDKAPI[] CARD_APIS = { chooseCard, addCard, openCard, consumeAndShareCard };
	/**
	 * 微信支付接口集合
	 */
	public final static JSSDKAPI[] PAY_APIS = { chooseWXPay };
	/**
	 * 企业号会话接口集合
	 */
	public final static JSSDKAPI[] CHAT_APIS = { openEnterpriseChat, sendEnterpriseChat };

	/**
	 * 公众平台全部接口集合
	 */
	public final static JSSDKAPI[] MP_ALL_APIS = { onMenuShareTimeline, onMenuShareAppMessage, onMenuShareQQ,
			onMenuShareWeibo, onMenuShareQZone, chooseImage, previewImage, uploadImage, downloadImage, startRecord,
			stopRecord, onVoiceRecordEnd, playVoice, pauseVoice, stopVoice, onVoicePlayEnd, uploadVoice, downloadVoice,
			translateVoice, getNetworkType, openLocation, getLocation, startSearchBeacons, stopSearchBeacons,
			onSearchBeacons, hideOptionMenu, showOptionMenu, closeWindow, hideMenuItems, showMenuItems,
			hideAllNonBaseMenuItem, showAllNonBaseMenuItem, scanQRCode, openProductSpecificView, chooseCard, addCard,
			openCard, consumeAndShareCard, chooseWXPay };
	/**
	 * 企业号全部接口集合
	 */
	public final static JSSDKAPI[] QY_ALL_APIS = { onMenuShareTimeline, onMenuShareAppMessage, onMenuShareQQ,
			onMenuShareWeibo, onMenuShareQZone, chooseImage, previewImage, uploadImage, downloadImage, startRecord,
			stopRecord, onVoiceRecordEnd, playVoice, pauseVoice, stopVoice, onVoicePlayEnd, uploadVoice, downloadVoice,
			translateVoice, getNetworkType, openLocation, getLocation, startSearchBeacons, stopSearchBeacons,
			onSearchBeacons, hideOptionMenu, showOptionMenu, closeWindow, hideMenuItems, showMenuItems,
			hideAllNonBaseMenuItem, showAllNonBaseMenuItem, scanQRCode, openEnterpriseChat, openEnterpriseContact };
}
