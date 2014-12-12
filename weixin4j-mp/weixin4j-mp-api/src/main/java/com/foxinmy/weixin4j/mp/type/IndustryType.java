package com.foxinmy.weixin4j.mp.type;

/**
 * 模板消息所用到的行业信息
 * 
 * @className IndustryType
 * @author jy
 * @date 2014年12月12日
 * @since JDK 1.7
 * @see<a href=
 *        "http://mp.weixin.qq.com/wiki/17/304c1885ea66dbedf7dc170d84999a9d.html#.E8.AE.BE.E7.BD.AE.E6.89.80.E5.B1.9E.E8.A1.8C.E4.B8.9A"
 *        >所处行业</a>
 */
public enum IndustryType {
	ITKEJI_HULIANWANG$DIANZISHANGWU("IT科技", "互联网/电子商务", 1), ITKEJI_ITRUANJIANYUFUWU(
			"IT科技", "IT软件与服务", 2), ITKEJI_ITYINGJIANYUSHEBEI("IT科技", "IT硬件与设备",
			3), ITKEJI_DIANZIJISHU("IT科技", "电子技术", 4), ITKEJI_TONGXINYUYUNYINGSHANG(
			"IT科技", "通信与运营商", 5), ITKEJI_WANGLUOYOUXI("IT科技", "网络游戏", 6), JINRONGYE_YINXING(
			"金融业", "银行", 7), JINRONGYE_JIJIN$LICAI$XINTUO("金融业", "基金|理财|信托", 8), JINRONGYE_BAOXIAN(
			"金融业", "保险", 9), CANYIN_CANYIN("餐饮", "餐饮", 10), JIUDIANLUYOU_JIUDIAN(
			"酒店旅游", "酒店", 11), JIUDIANLUYOU_LUYOU("酒店旅游", "旅游", 12), YUNSHUYUCANGCHU_KUAIDI(
			"运输与仓储", "快递", 13), YUNSHUYUCANGCHU_WULIU("运输与仓储", "物流", 14), YUNSHUYUCANGCHU_CANGCHU(
			"运输与仓储", "仓储", 15), JIAOYU_PEIXUN("教育", "培训", 16), JIAOYU_YUANXIAO(
			"教育", "院校", 17), ZHENGFUYUGONGGONGSHIYE_XUESHUKEYAN("政府与公共事业",
			"学术科研", 18), ZHENGFUYUGONGGONGSHIYE_JIAOJING("政府与公共事业", "交警", 19), ZHENGFUYUGONGGONGSHIYE_BOWUGUAN(
			"政府与公共事业", "博物馆", 20), ZHENGFUYUGONGGONGSHIYE_GONGGONGSHIYE$FEIYINGLIJIGOU(
			"政府与公共事业", "公共事业|非盈利机构", 21), YIYAOHULI_YIYAOYILIAO("医药护理", "医药医疗",
			22), YIYAOHULI_HULIMEIRONG("医药护理", "护理美容", 23), YIYAOHULI_BAOJIANYUWEISHENG(
			"医药护理", "保健与卫生", 24), JIAOTONGGONGJU_QICHEXIANGGUAN("交通工具", "汽车相关",
			25), JIAOTONGGONGJU_MOTUOCHEXIANGGUAN("交通工具", "摩托车相关", 26), JIAOTONGGONGJU_HUOCHEXIANGGUAN(
			"交通工具", "火车相关", 27), JIAOTONGGONGJU_FEIJIXIANGGUAN("交通工具", "飞机相关",
			28), FANGDICHAN_JIANZHU("房地产", "建筑", 29), FANGDICHAN_WUYE("房地产",
			"物业", 30), XIAOFEIPIN_XIAOFEIPIN("消费品", "消费品", 31), SHANGYEFUWU_FALU(
			"商业服务", "法律", 32), SHANGYEFUWU_HUIZHAN("商业服务", "会展", 33), SHANGYEFUWU_ZHONGJIEFUWU(
			"商业服务", "中介服务", 34), SHANGYEFUWU_RENZHENG("商业服务", "认证", 35), SHANGYEFUWU_SHENJI(
			"商业服务", "审计", 36), WENTIYULE_CHUANMEI("文体娱乐", "传媒", 37), WENTIYULE_TIYU(
			"文体娱乐", "体育", 38), WENTIYULE_YULEXIUXIAN("文体娱乐", "娱乐休闲", 39), YINSHUA_YINSHUA(
			"印刷", "印刷", 40), QITA_QITA("其它", "其它", 41);
	private String main;
	private String sub;
	private int value;

	IndustryType(String main, String sub, int value) {

		this.value = value;
	}

	public String getMain() {
		return main;
	}

	public String getSub() {
		return sub;
	}

	public int getValue() {
		return value;
	}
}
