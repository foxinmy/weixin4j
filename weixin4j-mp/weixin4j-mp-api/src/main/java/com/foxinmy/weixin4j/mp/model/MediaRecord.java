package com.foxinmy.weixin4j.mp.model;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.MediaType;

/**
 * 媒体素材记录
 * 
 * @className MediaRecord
 * @author jy
 * @date 2015年3月22日
 * @since JDK 1.7
 * @see
 */
public class MediaRecord implements Serializable {

	private static final long serialVersionUID = 7017503153256241457L;

	@JSONField(name = "total_count")
	private int totalCount;// 该类型的素材的总数
	@JSONField(name = "item_count")
	private int itemCount;// 本次调用获取的素材的数量
	@JSONField(serialize = false)
	private MediaType mediaType; // 媒体类型
	@JSONField(name = "item")
	private List<MediaItem> items; // 媒体信息

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}

	public List<MediaItem> getItems() {
		return items;
	}

	public void setItems(List<MediaItem> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "MediaRecord [totalCount=" + totalCount + ", itemCount="
				+ itemCount + ", mediaType=" + mediaType + ", items=" + items
				+ "]";
	}
}
