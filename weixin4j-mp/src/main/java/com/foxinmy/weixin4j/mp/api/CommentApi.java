package com.foxinmy.weixin4j.mp.api;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.paging.Pageable;
import com.foxinmy.weixin4j.model.paging.Pagedata;
import com.foxinmy.weixin4j.mp.model.ArticleComment;
import com.foxinmy.weixin4j.mp.model.ArticleComment.ArticleCommentType;
import com.foxinmy.weixin4j.token.TokenManager;

/**
 * 文章评论API
 *
 * @className CommentApi
 * @author jinyu
 * @date May 19, 2017
 * @since JDK 1.6
 * @see <a href=
 *      "https://mp.weixin.qq.com/wiki?action=doc&id=mp1494572718_WzHIY&t=0.6758084213658122">图文消息留言管理接口</a>
 */
public class CommentApi extends MpApi {
    private final TokenManager tokenManager;

    public CommentApi(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    /**
     * 打开/关闭已群发文章评论
     *
     * @param open
     *            true为打开，false为关闭
     * @param msgid
     *            群发返回的msg_data_id
     * @param index
     *            多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
     * @return 操作结果
     * @see {@link MassApi#massByTagId(com.foxinmy.weixin4j.tuple.MassTuple, int)}
     * @see {@link MassApi#massByOpenIds(com.foxinmy.weixin4j.tuple.MassTuple, String...)}
     * @throws WeixinException
     */
    public ApiResult openComment(boolean open, String msgid, int index) throws WeixinException {
        String news_comment = open ? getRequestUri("news_comment_open") : getRequestUri("news_comment_close");
        Token token = tokenManager.getCache();
        JSONObject obj = new JSONObject();
        obj.put("msg_data_id", msgid);
        obj.put("index", index);
        WeixinResponse response = weixinExecutor.post(String.format(news_comment, token.getAccessToken()),
                obj.toJSONString());

        return response.getAsResult();
    }

    /**
     * 获取评论列表
     *
     * @param page
     *            分页信息
     * @param commentType
     *            评论类型 为空获取全部类型
     * @param msgid
     *            群发返回的msg_data_id
     * @param index
     *            多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
     * @return 分页数据
     * @see ArticleComment
     * @see ArticleCommentType
     * @see {@link MassApi#massByTagId(com.foxinmy.weixin4j.tuple.MassTuple, int)}
     * @see {@link MassApi#massByOpenIds(com.foxinmy.weixin4j.tuple.MassTuple, String...)}
     * @throws WeixinException
     */
    public Pagedata<ArticleComment> listArticleComments(Pageable page, ArticleCommentType commentType, String msgid,
            int index) throws WeixinException {
        String news_comment_list = getRequestUri("news_comment_list");
        Token token = tokenManager.getCache();
        JSONObject obj = new JSONObject();
        obj.put("msg_data_id", "msgid");
        obj.put("index", index);
        obj.put("begin", page.getOffset());
        obj.put("count", Math.max(50, page.getPageSize())); // 获取数目（>=50会被拒绝）
        if (commentType != null) {
            obj.put("type", commentType.ordinal() + 1);
        } else {
            obj.put("type", 0);
        }
        WeixinResponse response = weixinExecutor.post(String.format(news_comment_list, token.getAccessToken()),
                obj.toJSONString());

        JSONObject result = response.getAsJson();
        int total = result.getIntValue("total");
        List<ArticleComment> content = JSON.parseArray(result.getString("comment"), ArticleComment.class);
        return new Pagedata<ArticleComment>(page, total, content);
    }

    /**
     * 获取评论列表
     *
     * @param commentType
     *            评论类型 为空获取全部类型
     * @param msgid
     *            群发返回的msg_data_id
     * @param index
     *            多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
     * @return 分页数据
     * @see #listArticleComments(Pageable, ArticleCommentType, String, int)
     * @throws WeixinException
     */
    public List<ArticleComment> listAllArticleComments(ArticleCommentType commentType, String msgid, int index)
            throws WeixinException {
        List<ArticleComment> comments = new ArrayList<ArticleComment>();
        Pagedata<ArticleComment> page = null;
        Pageable pageable = null;
        for (pageable = new Pageable(1, 50);; pageable = pageable.next()) {
            page = listArticleComments(pageable, commentType, msgid, index);
            if (!page.hasContent()) {
                break;
            }
            comments.addAll(page.getContent());
        }
        return comments;
    }

    /**
     * 评论标记/取消精选
     *
     * @param markelect
     *            true为标记，false为取消
     * @param msgid
     *            群发返回的msg_data_id
     * @param index
     *            多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
     * @param commentId
     *            用户评论ID
     * @return 操作结果
     * @see #listArticleComments(Pageable, ArticleCommentType, String, int)
     * @throws WeixinException
     */
    public ApiResult markelectComment(boolean markelect, String msgid, int index, String commentId)
            throws WeixinException {
        String news_comment = markelect ? getRequestUri("news_comment_markelect")
                : getRequestUri("news_comment_unmarkelect");
        Token token = tokenManager.getCache();
        JSONObject obj = new JSONObject();
        obj.put("msg_data_id", "msgid");
        obj.put("index", index);
        obj.put("user_comment_id", commentId);
        WeixinResponse response = weixinExecutor.post(String.format(news_comment, token.getAccessToken()),
                obj.toJSONString());

        return response.getAsResult();
    }

    /**
     * 删除评论
     *
     * @param msgid
     *            群发返回的msg_data_id
     * @param index
     *            多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
     * @param commentId
     *            用户评论ID
     * @return 操作结果
     * @see #listArticleComments(Pageable, ArticleCommentType, String, int)
     * @throws WeixinException
     */
    public ApiResult deleteComment(String msgid, int index, String commentId) throws WeixinException {
        String news_comment_delete = getRequestUri("news_comment_delete");
        Token token = tokenManager.getCache();
        JSONObject obj = new JSONObject();
        obj.put("msg_data_id", "msgid");
        obj.put("index", index);
        obj.put("user_comment_id", commentId);
        WeixinResponse response = weixinExecutor.post(String.format(news_comment_delete, token.getAccessToken()),
                obj.toJSONString());

        return response.getAsResult();
    }

    /**
     * 回复评论
     *
     * @param msgid
     *            群发返回的msg_data_id
     * @param index
     *            多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
     * @param commentId
     *            用户评论ID
     * @param content
     *            回复内容
     * @return 操作结果
     * @see #listArticleComments(Pageable, ArticleCommentType, String, int)
     * @throws WeixinException
     */
    public ApiResult replyComment(String msgid, int index, String commentId, String content) throws WeixinException {
        String news_comment_reply = getRequestUri("news_comment_reply_add");
        Token token = tokenManager.getCache();
        JSONObject obj = new JSONObject();
        obj.put("msg_data_id", "msgid");
        obj.put("index", index);
        obj.put("user_comment_id", commentId);
        obj.put("content", content);
        WeixinResponse response = weixinExecutor.post(String.format(news_comment_reply, token.getAccessToken()),
                obj.toJSONString());

        return response.getAsResult();
    }

    /**
     * 删除回复
     *
     * @param msgid
     *            群发返回的msg_data_id
     * @param index
     *            多图文时，用来指定第几篇图文，从0开始，不带默认操作该msg_data_id的第一篇图文
     * @param commentId
     *            用户评论ID
     * @return 操作结果
     * @see #listArticleComments(Pageable, ArticleCommentType, String, int)
     * @throws WeixinException
     */
    public ApiResult deleteCommentReply(String msgid, int index, String commentId) throws WeixinException {
        String news_comment_reply = getRequestUri("news_comment_reply_delete");
        Token token = tokenManager.getCache();
        JSONObject obj = new JSONObject();
        obj.put("msg_data_id", "msgid");
        obj.put("index", index);
        obj.put("user_comment_id", commentId);
        WeixinResponse response = weixinExecutor.post(String.format(news_comment_reply, token.getAccessToken()),
                obj.toJSONString());

        return response.getAsResult();
    }
}
