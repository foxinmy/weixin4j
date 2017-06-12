package com.foxinmy.weixin4j.tuple;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 群发消息图文(消息内容存储在微信后台)
 *
 * @className MpArticle
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年4月26日
 * @since JDK 1.6
 */
public class MpArticle implements Serializable {

    private static final long serialVersionUID = 5583479943661639234L;

    /**
     * 图文消息缩略图的media_id，可以在基础支持-上传多媒体文件接口中获得 非空
     */
    @JSONField(name = "thumb_media_id")
    private String thumbMediaId;
    /**
     * 图文消息的封面图片的地址(不一定有，请关注thumbMediaId)
     */
    @JSONField(name = "thumb_url")
    private String thumbUrl;
    /**
     * 图文消息的作者 可为空
     */
    private String author;
    /**
     * 图文消息的标题 非空
     */
    private String title;
    /**
     * 图文页的URL 获取图文消息时，群发消息时填写无效。
     */
    private String url;
    /**
     * 在图文消息页面点击“阅读原文”后的页面 可为空
     */
    @JSONField(name = "content_source_url")
    private String sourceUrl;
    /**
     * 图文消息页面的内容，支持HTML标签 非空
     */
    private String content;
    /**
     * 图文消息的描述 可为空
     */
    private String digest;
    /**
     * 是否显示封面，1为显示，0为不显示 可为空
     */
    @JSONField(name = "show_cover_pic")
    private String showCoverPic;
    /**
     * 是否打开评论，0不打开，1打开
     */
    @JSONField(name = "need_open_comment")
    private String openComment;
    /**
     * 是否粉丝才可评论，0所有人可评论，1粉丝才可评论
     */
    @JSONField(name = "only_fans_can_comment")
    private String onlyFansCanComment;

    protected MpArticle() {
    }

    /**
     * @param thumbMediaId
     *            缩略图
     * @param title
     *            标题
     * @param content
     *            内容
     */
    public MpArticle(String thumbMediaId, String title, String content) {
        this.thumbMediaId = thumbMediaId;
        this.title = title;
        this.content = content;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getShowCoverPic() {
        return showCoverPic;
    }

    public void setShowCoverPic(String showCoverPic) {
        this.showCoverPic = showCoverPic;
    }

    public void setShowCoverPic(boolean showCoverPic) {
        this.showCoverPic = showCoverPic ? "1" : "0";
    }

    @JSONField(serialize = false)
    public boolean getFormatShowCoverPic() {
        return this.showCoverPic != null && this.showCoverPic.equals("1");
    }

    public void setOpenComment(boolean openComment) {
        this.openComment = openComment ? "1" : "0";
    }

    public String getOpenComment() {
        return openComment;
    }

    public void setOpenComment(String openComment) {
        this.openComment = openComment;
    }

    public String getOnlyFansCanComment() {
        return onlyFansCanComment;
    }

    public void setOnlyFansCanComment(String onlyFansCanComment) {
        this.onlyFansCanComment = onlyFansCanComment;
    }

    @JSONField(serialize = false)
    public boolean getFormatOpenComment() {
        return this.openComment != null && this.openComment.equals("1");
    }

    public void setOnlyFansCanComment(boolean onlyFansCanComment) {
        this.onlyFansCanComment = onlyFansCanComment ? "1" : "0";
    }

    @JSONField(serialize = false)
    public boolean getFormatOnlyFansCanComment() {
        return this.onlyFansCanComment != null && this.onlyFansCanComment.equals("1");
    }

    @Override
    public String toString() {
        return "MpArticle [thumbMediaId=" + thumbMediaId + ", thumbUrl=" + thumbUrl + ", author=" + author + ", title="
                + title + ", url=" + url + ", sourceUrl=" + sourceUrl + ", content=" + content + ", digest=" + digest
                + ", showCoverPic=" + showCoverPic + ", openComment=" + openComment + ", onlyFansCanComment="
                + onlyFansCanComment + "]";
    }
}
