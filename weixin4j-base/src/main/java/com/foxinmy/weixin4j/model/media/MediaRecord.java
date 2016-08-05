package com.foxinmy.weixin4j.model.media;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.model.paging.Pageable;
import com.foxinmy.weixin4j.model.paging.Pagedata;
import com.foxinmy.weixin4j.type.MediaType;

/**
 * 媒体素材记录
 *
 * @className MediaRecord
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年3月22日
 * @since JDK 1.6
 * @see
 */
public class MediaRecord implements Serializable {

	private static final long serialVersionUID = 7017503153256241457L;

	/**
	 * 该类型的素材的总数
	 */
	@JSONField(name = "total_count")
	private int totalCount;
	/**
	 * 本次调用获取的素材的数量
	 */
	@JSONField(name = "item_count")
	private int itemCount;
	/**
	 * 媒体类型
	 */
	@JSONField(serialize = false, deserialize = false)
	private MediaType mediaType;
	/**
	 * 媒体信息
	 */
	@JSONField(name = "items")
	private List<MediaItem> items;
	/**
	 * 分页信息
	 */
	@JSONField(serialize = false, deserialize = false)
	private Pageable pageable;
	@JSONField(serialize = false, deserialize = false)
	private volatile Pagedata<MediaItem> pagedata;

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

	public Pageable getPageable() {
		return pageable;
	}

	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}

	public Pagedata<MediaItem> getPagedata() {
		if (pagedata == null) {
			pagedata = new Pagedata<MediaItem>(pageable, totalCount, items);
		}
		return pagedata;
	}

	@Override
	public String toString() {
		return "MediaRecord [totalCount=" + totalCount + ", itemCount="
				+ itemCount + ", mediaType=" + mediaType + ", items=" + items
				+ ", pageable=" + pageable + "]";
	}
}
