package com.foxinmy.weixin4j.mp.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.token.TokenManager;
import com.foxinmy.weixin4j.tuple.MassTuple;
import com.foxinmy.weixin4j.tuple.MpArticle;
import com.foxinmy.weixin4j.tuple.MpNews;
import com.foxinmy.weixin4j.tuple.Tuple;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * 群发相关API
 *
 * @className MassApi
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年9月25日
 * @since JDK 1.6
 */
public class MassApi extends MpApi {

    private final TokenManager tokenManager;

    public MassApi(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    /**
     * 上传图文消息,一个图文消息支持1到10条图文</br>
     * <font color=
     * "red">具备微信支付权限的公众号，在使用高级群发接口上传、群发图文消息类型时，可使用&lta&gt标签加入外链</font>
     *
     * @param articles
     *            图片消息
     * @return 媒体ID
     * @throws WeixinException
     * @see <a href=
     *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN">上传图文素材</a>
     * @see com.foxinmy.weixin4j.tuple.MpArticle
     */
    public String uploadArticle(List<MpArticle> articles) throws WeixinException {
        String article_upload_uri = getRequestUri("article_upload_uri");
        Token token = tokenManager.getCache();
        JSONObject obj = new JSONObject();
        obj.put("articles", articles);
        WeixinResponse response = weixinExecutor.post(String.format(article_upload_uri, token.getAccessToken()),
                obj.toJSONString());

        return response.getAsJson().getString("media_id");
    }

    /**
     * 分组群发
     * <p>
     * 在返回成功时,意味着群发任务提交成功,并不意味着此时群发已经结束,所以,仍有可能在后续的发送过程中出现异常情况导致用户未收到消息,
     * 如消息有时会进行审核、服务器不稳定等,此外,群发任务一般需要较长的时间才能全部发送完毕
     * </p>
     *
     * @param tuple
     *            消息元件
     * @param isToAll
     *            用于设定是否向全部用户发送，值为true或false，选择true该消息群发给所有用户，
     *            选择false可根据group_id发送给指定群组的用户
     * @param groupId
     *            分组ID
     * @return 第一个元素为消息发送任务的ID,第二个元素为消息的数据ID，该字段只有在群发图文消息时，才会出现,可以用于在图文分析数据接口中
     * @throws WeixinException
     * @see com.foxinmy.weixin4j.mp.model.Group
     * @see com.foxinmy.weixin4j.tuple.Text
     * @see com.foxinmy.weixin4j.tuple.Image
     * @see com.foxinmy.weixin4j.tuple.Voice
     * @see com.foxinmy.weixin4j.tuple.MpVideo
     * @see com.foxinmy.weixin4j.tuple.MpNews
     * @see com.foxinmy.weixin4j.tuple.Card
     * @see com.foxinmy.weixin4j.tuple.MassTuple
     * @see {@link GroupApi#getGroups()}
     * @see <a href=
     *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN">根据分组群发</a>
     */
    @Deprecated
    public String[] massByGroupId(MassTuple tuple, boolean isToAll, int groupId) throws WeixinException {
        if (tuple instanceof MpNews) {
            MpNews _news = (MpNews) tuple;
            List<MpArticle> _articles = _news.getArticles();
            if (StringUtil.isBlank(_news.getMediaId())) {
                if (_articles.isEmpty()) {
                    throw new WeixinException("mass fail:mediaId or articles is required");
                }
                tuple = new MpNews(uploadArticle(_articles));
            }
        }
        String msgtype = tuple.getMessageType();
        JSONObject obj = new JSONObject();
        JSONObject item = new JSONObject();
        item.put("is_to_all", isToAll);
        if (!isToAll) {
            item.put("group_id", groupId);
        }
        obj.put("filter", item);
        obj.put(msgtype, JSON.toJSON(tuple));
        obj.put("msgtype", msgtype);
        String mass_group_uri = getRequestUri("mass_group_uri");
        Token token = tokenManager.getCache();
        WeixinResponse response = weixinExecutor.post(String.format(mass_group_uri, token.getAccessToken()),
                obj.toJSONString());

        obj = response.getAsJson();
        return new String[] { obj.getString("msg_id"), obj.getString("msg_data_id") };
    }

    /**
     * 分组ID群发图文消息
     *
     * @param articles
     *            图文列表
     * @param groupId
     *            分组ID
     * @return 第一个元素为消息发送任务的ID,第二个元素为消息的数据ID，该字段只有在群发图文消息时，才会出现。
     * @see <a href=
     *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN">根据分组群发</a>
     * @see {@link #massByGroupId(Tuple,int)}
     * @see com.foxinmy.weixin4j.tuple.MpArticle
     * @throws WeixinException
     */
    @Deprecated
    public String[] massArticleByGroupId(List<MpArticle> articles, int groupId) throws WeixinException {
        String mediaId = uploadArticle(articles);
        return massByGroupId(new MpNews(mediaId), false, groupId);
    }

    /**
     * 群发消息
     * <p>
     * 在返回成功时,意味着群发任务提交成功,并不意味着此时群发已经结束,所以,仍有可能在后续的发送过程中出现异常情况导致用户未收到消息,
     * 如消息有时会进行审核、服务器不稳定等,此外,群发任务一般需要较长的时间才能全部发送完毕
     * </p>
     *
     * @param tuple
     *            消息元件
     * @param filter
     *            过滤条件
     * @return 第一个元素为消息发送任务的ID,第二个元素为消息的数据ID，该字段只有在群发图文消息时，才会出现,可以用于在图文分析数据接口中
     * @throws WeixinException
     * @see Tag
     * @see com.foxinmy.weixin4j.tuple.Text
     * @see com.foxinmy.weixin4j.tuple.Image
     * @see com.foxinmy.weixin4j.tuple.Voice
     * @see com.foxinmy.weixin4j.tuple.MpVideo
     * @see com.foxinmy.weixin4j.tuple.MpNews
     * @see com.foxinmy.weixin4j.tuple.Card
     * @see com.foxinmy.weixin4j.tuple.MassTuple
     */
    private String[] mass(MassTuple tuple, JSONObject filter) throws WeixinException {
        if (tuple instanceof MpNews) {
            MpNews _news = (MpNews) tuple;
            List<MpArticle> _articles = _news.getArticles();
            if (StringUtil.isBlank(_news.getMediaId())) {
                if (_articles.isEmpty()) {
                    throw new WeixinException("mass fail:mediaId or articles is required");
                }
                tuple = new MpNews(uploadArticle(_articles));
            }
            if (!filter.containsKey("send_ignore_reprint")) {
                filter.put("send_ignore_reprint", 0);
            }
        }
        String msgtype = tuple.getMessageType();
        JSONObject obj = new JSONObject();
        obj.putAll(filter);
        obj.put(msgtype, JSON.toJSON(tuple));
        obj.put("msgtype", msgtype);
        String mass_group_uri = getRequestUri("mass_group_uri");
        Token token = tokenManager.getCache();
        WeixinResponse response = weixinExecutor.post(String.format(mass_group_uri, token.getAccessToken()),
                obj.toJSONString());

        obj = response.getAsJson();
        return new String[] { obj.getString("msg_id"), obj.getString("msg_data_id") };
    }

    /**
     * 群发消息给所有粉丝
     *
     * @param tuple
     *            消息元件
     * @return 第一个元素为消息发送任务的ID,第二个元素为消息的数据ID，该字段只有在群发图文消息时，才会出现,可以用于在图文分析数据接口中
     * @throws WeixinException
     * @see <a href=
     *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN">根据标签群发</a>
     */
    public String[] massToAll(MassTuple tuple) throws WeixinException {
        String filter = String.format("{\"filter\":{\"is_to_all\":true}}");
        return mass(tuple, JSON.parseObject(filter));
    }

    /**
     * 标签群发消息
     *
     * @param tuple
     *            消息元件
     * @param tagId
     *            标签ID
     * @return 第一个元素为消息发送任务的ID,第二个元素为消息的数据ID，该字段只有在群发图文消息时，才会出现,可以用于在图文分析数据接口中
     * @throws WeixinException
     * @see Tag
     * @see {@link TagApi#listTags()}
     * @see #mass(MassTuple, JSONObject, boolean)
     * @see <a href=
     *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN">根据标签群发</a>
     */
    public String[] massByTagId(MassTuple tuple, int tagId) throws WeixinException {
        String filter = String.format("{\"filter\":{\"is_to_all\":false,\"tag_id\":%d}}", tagId);
        return mass(tuple, JSON.parseObject(filter));
    }

    /**
     * 标签群发图文消息
     *
     * @param articles
     *            图文列表
     * @param tagId
     *            标签ID
     * @param ignoreReprint
     *            图文消息被判定为转载时，是否继续群发
     * @return 第一个元素为消息发送任务的ID,第二个元素为消息的数据ID，该字段只有在群发图文消息时，才会出现。
     * @see <a href=
     *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN">根据标签群发</a>
     * @see {@link #massByTagId(Tuple,int)}
     * @see com.foxinmy.weixin4j.tuple.MpArticle
     * @throws WeixinException
     */
    public String[] massArticleByTagId(List<MpArticle> articles, int tagId, boolean ignoreReprint)
            throws WeixinException {
        String mediaId = uploadArticle(articles);
        String text = String.format("{\"filter\":{\"is_to_all\":false,\"tag_id\":%d}}", tagId);
        JSONObject filter = JSON.parseObject(text);
        filter.put("send_ignore_reprint", ignoreReprint ? 1 : 0);
        return mass(new MpNews(mediaId), filter);
    }

    /**
     * openId群发消息
     *
     * @param tuple
     *            消息元件
     * @param openIds
     *            openId列表
     * @return 第一个元素为消息发送任务的ID,第二个元素为消息的数据ID，该字段只有在群发图文消息时，才会出现,可以用于在图文分析数据接口中
     * @throws WeixinException
     * @see <a href=
     *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN">根据openid群发</a>
     * @see {@link UserApi#getUser(String)}
     * @see #mass(MassTuple, JSONObject, boolean)
     */
    public String[] massByOpenIds(MassTuple tuple, String... openIds) throws WeixinException {
        JSONObject filter = new JSONObject();
        filter.put("touser", openIds);
        return mass(tuple, filter);
    }

    /**
     * openid群发图文消息
     *
     * @param articles
     *            图文列表
     * @param ignoreReprint
     *            图文消息被判定为转载时，是否继续群发
     * @param openIds
     *            openId列表
     * @return 第一个元素为消息发送任务的ID,第二个元素为消息的数据ID，该字段只有在群发图文消息时，才会出现,可以用于在图文分析数据接口中.
     * @see <a href=
     *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN">根据openid群发</a>
     * @see {@link #massByOpenIds(Tuple,String...)}
     * @see com.foxinmy.weixin4j.tuple.MpArticle
     * @throws WeixinException
     */
    public String[] massArticleByOpenIds(List<MpArticle> articles, boolean ignoreReprint, String... openIds)
            throws WeixinException {
        String mediaId = uploadArticle(articles);
        JSONObject filter = new JSONObject();
        filter.put("touser", openIds);
        filter.put("send_ignore_reprint", ignoreReprint ? 1 : 0);
        return mass(new MpNews(mediaId), filter);
    }

    /**
     * 删除群发消息
     *
     * @param msgid
     *            发送出去的消息ID
     * @throws WeixinException
     * @see <a href=
     *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN">删除群发</a>
     * @see #deleteMassNews(String, int)
     */
    public ApiResult deleteMassNews(String msgid) throws WeixinException {
        return deleteMassNews(msgid, 0);
    }

    /**
     * 删除群发消息
     * <p>
     * 请注意,只有已经发送成功的消息才能删除删除消息只是将消息的图文详情页失效,已经收到的用户,还是能在其本地看到消息卡片
     * </p>
     *
     * @param msgid
     *            发送出去的消息ID
     * @param articleIndex
     *            要删除的文章在图文消息中的位置，第一篇编号为1，该字段不填或填0会删除全部文章
     * @throws WeixinException
     * @see <a href=
     *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN">删除群发</a>
     * @see {@link #massByTagId(Tuple, int)}
     * @see {@link #massByOpenIds(Tuple, String...)
     *
     */
    public ApiResult deleteMassNews(String msgid, int articleIndex) throws WeixinException {
        JSONObject obj = new JSONObject();
        obj.put("msgid", msgid);
        if (articleIndex > 0)
            obj.put("article_idx", articleIndex);
        String mass_delete_uri = getRequestUri("mass_delete_uri");
        Token token = tokenManager.getCache();
        WeixinResponse response = weixinExecutor.post(String.format(mass_delete_uri, token.getAccessToken()),
                obj.toJSONString());

        return response.getAsResult();
    }

    /**
     * 预览群发消息</br>
     * 开发者可通过该接口发送消息给指定用户，在手机端查看消息的样式和排版
     *
     * @param toUser
     *            接收用户的openID
     * @param toWxName
     *            接收用户的微信号 towxname和touser同时赋值时，以towxname优先
     * @param tuple
     *            消息元件
     * @return 处理结果
     * @throws WeixinException
     * @see com.foxinmy.weixin4j.tuple.MassTuple
     * @see <a href=
     *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN">预览群发消息</a>
     */
    public ApiResult previewMassNews(String toUser, String toWxName, MassTuple tuple) throws WeixinException {
        String msgtype = tuple.getMessageType();
        JSONObject obj = new JSONObject();
        obj.put("touser", toUser);
        obj.put("towxname", toWxName);
        obj.put(msgtype, JSON.toJSON(tuple));
        obj.put("msgtype", msgtype);
        String mass_preview_uri = getRequestUri("mass_preview_uri");
        Token token = tokenManager.getCache();
        WeixinResponse response = weixinExecutor.post(String.format(mass_preview_uri, token.getAccessToken()),
                obj.toJSONString());

        return response.getAsResult();
    }

    /**
     * 查询群发发送状态
     *
     * @param msgId
     *            消息ID
     * @return 消息发送状态,如sendsuccess:发送成功、sendfail:发送失败
     * @throws WeixinException
     * @see <a href=
     *      "https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN">查询群发状态</a>
     */
    public String getMassNewStatus(String msgId) throws WeixinException {
        JSONObject obj = new JSONObject();
        obj.put("msg_id", msgId);
        String mass_get_uri = getRequestUri("mass_get_uri");
        Token token = tokenManager.getCache();
        WeixinResponse response = weixinExecutor.post(String.format(mass_get_uri, token.getAccessToken()),
                obj.toJSONString());

        String status = response.getAsJson().getString("msg_status");
        String desc = massStatusMap.get(status);
        return String.format("%s:%s", status, desc);
    }

    private final static Map<String, String> massStatusMap;
    static {
        massStatusMap = new HashMap<String, String>();
        massStatusMap.put("sendsuccess", "发送成功");
        massStatusMap.put("send_success", "发送成功");
        massStatusMap.put("success", "发送成功");
        massStatusMap.put("send success", "发送成功");
        massStatusMap.put("sendfail", "发送失败");
        massStatusMap.put("send_fail", "发送失败");
        massStatusMap.put("fail", "发送失败");
        massStatusMap.put("send fail", "发送失败");
        massStatusMap.put("err(10001)", "涉嫌广告");
        massStatusMap.put("err(20001)", "涉嫌政治");
        massStatusMap.put("err(20004)", "涉嫌社会");
        massStatusMap.put("err(20006)", "涉嫌违法犯罪");
        massStatusMap.put("err(20008)", "涉嫌欺诈");
        massStatusMap.put("err(20013)", "涉嫌版权");
        massStatusMap.put("err(22000)", "涉嫌互推(互相宣传)");
        massStatusMap.put("err(21000)", "涉嫌其他");
    }
}
