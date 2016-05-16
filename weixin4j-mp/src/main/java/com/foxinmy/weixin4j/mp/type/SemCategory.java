package com.foxinmy.weixin4j.mp.type;

/**
 * 垂直服务协议
 * 
 * @className SemCategory
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年11月7日
 * @since JDK 1.6
 * @see
 */
public enum SemCategory {
	/**
	 * 生活类
	 */
	restaurant("生活类", "餐馆", "查询餐馆的服务,例如:中关村附近的面馆"), map("生活类", "地图",
			"查询地图服务,例如:从银科大厦到天坛公园怎么走"), nearby("生活类", "周边",
			"查询周边的服务,例如:我想去打保龄球"), coupon("生活类", "优惠券/团购",
			"查询优惠券/团购的服务,例如:“附 近有什么优惠券”;如果查已有类别 的优惠券,比如:“附近有什么酒店 优惠券”,那么就会优先是酒店类。"),
	/**
	 * 旅行类
	 */
	hotel("旅行类", "酒店", "查询酒店服务,例如:查一下中关村附近有没有七天酒店"), travel("旅行类", "旅游",
			"查询旅游服务,例如:故宫门票多少钱"), flight("旅行类", "航班", "查询航班的服务,例如:明天从北京到上海的机票"), train(
			"旅行类", "火车", "查询火车服务,例如:查一下从北京到西安的火车"),
	/**
	 * 娱乐类
	 */
	movie("娱乐类", "上映电影", "查询上映电影的服务,例如:最近有什么好看的电影"), music("娱乐类", "音乐",
			"￼查询音乐的服务,例如:来点刘德华的歌"), video("娱乐类", "视频", "查询视频服务,例如:我想看甄嬛传"), novel(
			"娱乐类", "小说", "查询小说的服务,例如:来点言情小说看看"),
	/**
	 * 工具类
	 */
	weather("工具类", "天气", "查询天气的服务,例如:明天北京天气"), stock("工具类", "股票",
			"查询股票的服务,例如:腾讯股价多少了"), remind("工具类", "提醒", "提醒服务,例如:提醒我明天上午十点开会"), telephone(
			"工具类", "常用电影", "查询常用电话号码服务,例如:查询一下招行信用卡的电话"),
	/**
	 * 知识类
	 */
	cookbook("知识类", "菜谱", "查询菜谱服务,例如:宫保鸡丁怎么做"), baike("知识类", "百科",
			"查询百科服务,例如:查一下刘德华的百科资料"), news("知识类", "资讯", "查询新闻服务,例如:今天有什么新闻"),
	/**
	 * 其他类
	 */
	tv("其他类", "电视节目预告", "查询电视节目服务,例如:湖南台今晚有什么节目"), instruction("其他类", "通用指令",
			"通用指令服务,例如:把声音调高一点"), tv_instruction("其他类", "电视指令",
			"电视指令服务,例如:切换到中央五台"), car_instruction("其他类", "车载指令",
			"车载指令服务,例如:把空调设为 25 度"), app("其他类", "应用", "查询应用服务,例如:打开愤怒的小鸟"), website(
			"其他类", "网址", "查询网址服务,例如:帮我打开腾讯网"), search("其他类", "网页搜索",
			"网页搜索服务,例如:百度一下意大利对乌拉圭");
	SemCategory(String main, String desc, String remark) {
		this.main = main;
		this.desc = desc;
		this.remark = remark;
	}

	private String main;
	private String desc;
	private String remark;

	public String getMain() {
		return main;
	}

	public String getDesc() {
		return desc;
	}

	public String getRemark() {
		return remark;
	}
}
