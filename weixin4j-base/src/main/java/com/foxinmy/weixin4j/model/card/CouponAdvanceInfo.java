package com.foxinmy.weixin4j.model.card;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.Week;
import com.foxinmy.weixin4j.util.NameValue;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * 卡券高级信息: <li>1.高级字段为商户额外展示信息字段，非必填,但是填入某些结构体后，须填充完整方可显示：如填入text_image_list结构体
 * 时，须同时传入image_url和text，否则也会报错； <li>
 * 2.填入时间限制字段（time_limit）,只控制显示，不控制实际使用逻辑，不填默认不显示 <li>
 * 3.创建卡券时，开发者填入的时间戳须注意时间戳溢出时间，设置的时间戳须早于2038年1月19日
 *
 * @className CouponAdvancedInfo
 * @author jinyu(foxinmy@gmail.com)
 * @date 2016年6月1日
 * @since JDK 1.6
 * @see Builder
 */
public class CouponAdvanceInfo implements Serializable {

	private static final long serialVersionUID = 3626615706377721404L;
	/**
	 * 使用门槛（条件）字段，若不填写使用条件则在券面拼写 ：无最低消费限制，全场通用，不限品类；并在使用说明显示： 可与其他优惠共享
	 */
	@JSONField(name = "use_condition")
	private final JSONObject useCondition;
	/**
	 * 封面摘要结构
	 */
	@JSONField(name = "abstract")
	private final JSONObject abstractConver;
	/**
	 * 图文列表，显示在详情内页 ，优惠券券开发者须至少传入 一组图文列表
	 */
	@JSONField(name = "text_image_list")
	private final List<JSONObject> slideImages;
	/**
	 * 使用时段限制
	 */
	@JSONField(name = "time_limit")
	private final List<JSONObject> timeLimits;
	/**
	 * 商家服务类型
	 */
	@JSONField(name = "business_service")
	private final List<BusinessService> businessServices;

	private CouponAdvanceInfo(Builder builder) {
		this.useCondition = builder.useCondition;
		this.abstractConver = builder.abstractConver;
		this.slideImages = builder.slideImages;
		this.timeLimits = builder.timeLimits;
		this.businessServices = builder.businessServices;
	}


	public JSONObject getUseCondition() {
		return useCondition;
	}

	public JSONObject getAbstractConver() {
		return abstractConver;
	}

	public List<JSONObject> getSlideImages() {
		return slideImages;
	}

	public List<JSONObject> getTimeLimits() {
		return timeLimits;
	}

	public List<BusinessService> getBusinessServices() {
		return businessServices;
	}

	/**
	 * 卡券高级信息构造器
	 * 
	 * @className Builder
	 * @author jinyu(foxinmy@gmail.com)
	 * @date 2016年8月4日
	 * @since JDK 1.6
	 */
	public static final class Builder {
		/**
		 * 使用门槛（条件）字段，若不填写使用条件则在券面拼写 ：无最低消费限制，全场通用，不限品类；并在使用说明显示： 可与其他优惠共享
		 */
		private JSONObject useCondition;
		/**
		 * 封面摘要结构
		 */
		private JSONObject abstractConver;
		/**
		 * 图文列表，显示在详情内页 ，优惠券券开发者须至少传入 一组图文列表
		 */
		private List<JSONObject> slideImages;
		/**
		 * 使用时段限制
		 */
		private List<JSONObject> timeLimits;
		/**
		 * 商家服务类型
		 */
		private List<BusinessService> businessServices;

		public Builder() {
		}



		/**
		 * 设置使用门槛（条件）字段，若不填写使用条件则在券面拼写 ：无最低消费限制，全场通用，不限品类；并在使用说明显示： 可与其他优惠共享
		 * 
		 * @param acceptCategory
		 *            指定可用的商品类目，仅用于代金券类型 ，填入后将在券面拼写适用于xxx
		 * @param rejectCategory
		 *            指定可用的商品类目，仅用于代金券类型 ，填入后将在券面拼写不适用于xxxx
		 * @return
		 */
		public Builder useCondition(String acceptCategory, String rejectCategory) {
			return useCondition(acceptCategory, rejectCategory, 0, null, true);
		}

		/**
		 * 设置使用门槛（条件）字段，若不填写使用条件则在券面拼写 ：无最低消费限制，全场通用，不限品类；并在使用说明显示： 可与其他优惠共享
		 * 
		 * @param leastCost
		 *            满减门槛字段，可用于兑换券和代金券 ，填入后将在全面拼写消费满xx元可用。
		 * @param objectUseFor
		 *            购买xx可用类型门槛，仅用于兑换 ，填入后自动拼写购买xxx可用。
		 * @return
		 */
		public Builder useCondition(int leastCost, String objectUseFor) {
			return useCondition(null, null, leastCost, objectUseFor, true);
		}

		/**
		 * 设置使用门槛（条件）字段，若不填写使用条件则在券面拼写 ：无最低消费限制，全场通用，不限品类；并在使用说明显示： 可与其他优惠共享
		 * 
		 * @param acceptCategory
		 *            指定可用的商品类目，仅用于代金券类型 ，填入后将在券面拼写适用于xxx
		 * @param rejectCategory
		 *            指定可用的商品类目，仅用于代金券类型 ，填入后将在券面拼写不适用于xxxx
		 * @param leastCost
		 *            满减门槛字段，可用于兑换券和代金券 ，填入后将在全面拼写消费满xx元可用。
		 * @param objectUseFor
		 *            购买xx可用类型门槛，仅用于兑换 ，填入后自动拼写购买xxx可用。
		 * @param canUseWithOtherDiscount
		 *            不可以与其他类型共享门槛 ，填写false时系统将在使用须知里 拼写“不可与其他优惠共享”，
		 *            填写true时系统将在使用须知里 拼写“可与其他优惠共享”， 默认为true
		 * @return
		 */
		public Builder useCondition(String acceptCategory,
				String rejectCategory, int leastCost, String objectUseFor,
				boolean canUseWithOtherDiscount) {
			if(useCondition == null)
				useCondition = new JSONObject();
			useCondition.clear();
			if (StringUtil.isNotBlank(acceptCategory)) {
				useCondition.put("accept_category", acceptCategory);
			}
			if (StringUtil.isNotBlank(rejectCategory)) {
				useCondition.put("reject_category", rejectCategory);
			}
			if (leastCost > 0) {
				useCondition.put("least_cost", leastCost);
			}
			if (StringUtil.isNotBlank(objectUseFor)) {
				useCondition.put("object_use_for", objectUseFor);
			}
			useCondition.put("can_use_with_other_discount",
					canUseWithOtherDiscount);
			return this;
		}

		/**
		 * 设置封面摘要
		 * 
		 * @param abstracts
		 *            封面摘要简介
		 * @param convers
		 *            封面图片列表
		 * @return
		 */
		public Builder abstractConver(String abstracts, String... convers) {
			if(abstractConver == null)
				abstractConver = new JSONObject();
			abstractConver.clear();
			abstractConver.put("abstract", abstracts);
			abstractConver.put("icon_url_list", convers);
			return this;
		}

		/**
		 * 设置图文列表，显示在详情内页 ，优惠券券开发者须至少传入 一组图文列表
		 * 
		 * @param slideImages
		 *            图文列表，name为图片描述，value为图片链接
		 * @return
		 */
		public Builder slideImages(NameValue... slideImages) {
			if(this.slideImages == null)
				this.slideImages = new ArrayList<JSONObject>();
			this.slideImages.clear();
			for (NameValue nv : slideImages) {
				JSONObject slide = new JSONObject();
				slide.put("text", nv.getName());
				slide.put("image_url", nv.getValue());
				this.slideImages.add(slide);
			}
			return this;
		}

		/**
		 * 设置图文列表，显示在详情内页 ，优惠券券开发者须至少传入 一组图文列表
		 * 
		 * @param title
		 *            图片标题
		 * @param url
		 *            图片链接
		 * @return
		 */
		public Builder slideImage(String title, String url) {
			if(this.slideImages == null)
				this.slideImages = new ArrayList<JSONObject>();
			JSONObject slide = new JSONObject();
			slide.put("text", title);
			slide.put("image_url", url);
			this.slideImages.add(slide);
			return this;
		}

		/**
		 * 设置使用时段限制
		 * 
		 * @param week
		 *            星期，此处只控制显示， 不控制实际使用逻辑，不填默认不显示
		 * @param beginHour
		 *            当前week类型下的起始时间（小时） ，如当前结构体内填写了MONDAY， 此处填写了10，则此处表示周一
		 *            10:00可用
		 * @param beignMinute
		 *            当前week类型下的起始时间（分钟） ，如当前结构体内填写了MONDAY，
		 *            begin_hour填写10，此处填写了59， 则此处表示周一 10:59可用
		 * @return
		 */
		public Builder timeLimit(Week week, int beginHour, int beignMinute) {
			return timeLimit(week, beginHour, beignMinute, 0, 0);
		}

		/**
		 * 设置 使用时段限制
		 * 
		 * @param week
		 *            星期，此处只控制显示， 不控制实际使用逻辑，不填默认不显示
		 * @param beginHour
		 *            当前week类型下的起始时间（小时） ，如当前结构体内填写了MONDAY， 此处填写了10，则此处表示周一
		 *            10:00可用
		 * @param beignMinute
		 *            当前week类型下的起始时间（分钟） ，如当前结构体内填写了MONDAY，
		 *            begin_hour填写10，此处填写了59， 则此处表示周一 10:59可用
		 * @param endHour
		 *            当前week类型下的结束时间（小时） ，如当前结构体内填写了MONDAY， 此处填写了20，则此处表示周一
		 *            10:00-20:00可用
		 * @param endMinute
		 *            当前week类型下的结束时间（分钟） ，如当前结构体内填写了MONDAY，
		 *            begin_hour填写10，此处填写了59， 则此处表示周一 10:59-00:59可用
		 * @return
		 */
		public Builder timeLimit(Week week, int beginHour, int beignMinute,
				int endHour, int endMinute) {
			if(this.timeLimits == null)
				this.timeLimits = new ArrayList<JSONObject>();
			JSONObject timeLimit = new JSONObject();
			if (week != null) {
				timeLimit.put("type", week.name());
			}
			timeLimit.put("begin_hour", beginHour);
			if (beignMinute > 0) {
				timeLimit.put("begin_minute", beignMinute);
			}
			timeLimit.put("end_hour", endHour);
			if (endMinute > 0) {
				timeLimit.put("end_minute", endMinute);
			}
			this.timeLimits.add(timeLimit);
			return this;
		}

		/**
		 * 设置商家服务类型
		 * 
		 * @param businessServices
		 *            服务类型
		 * @return
		 */
		public Builder businessServices(BusinessService... businessServices) {
			if(this.businessServices == null)
				this.businessServices = new ArrayList<BusinessService>();
			this.businessServices.addAll(Arrays.asList(businessServices));
			return this;
		}

		public CouponAdvanceInfo build(){
			return new CouponAdvanceInfo(this);
		}
	}

	/**
	 * 商家服务
	 * 
	 * @className BusinessService
	 * @author jinyu(foxinmy@gmail.com)
	 * @date 2016年8月5日
	 * @since JDK 1.6
	 */
	public enum BusinessService {
		/**
		 * 外卖服务
		 */
		BIZ_SERVICE_DELIVER,
		/**
		 * 停车位
		 */
		BIZ_SERVICE_FREE_PARK,
		/**
		 * 可带宠物
		 */
		BIZ_SERVICE_WITH_PET,
		/**
		 * 免费wifi
		 */
		BIZ_SERVICE_FREE_WIFI;
	}
}
