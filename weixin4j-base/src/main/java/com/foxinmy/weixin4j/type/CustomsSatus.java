package com.foxinmy.weixin4j.type;

/**
 * 报关状态
 * 
 * @className CustomsSatus
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年3月27日
 * @since JDK 1.6
 * @see
 */
public enum CustomsSatus {
	UNDECLARED("未申报"), SUBMITTED("申报已提交"), PROCESSING("申报中"), SUCCESS("申报成功"), FAIL(
			"申报失败"), EXCEPT("海关接口异常");
	private String sate;

	CustomsSatus(String sate) {
		this.sate = sate;
	}

	public String getSate() {
		return this.sate;
	}
}
