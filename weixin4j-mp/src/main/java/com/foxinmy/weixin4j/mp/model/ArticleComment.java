package com.foxinmy.weixin4j.mp.model;

import java.io.Serializable;

/**
 * 文章评论
 *
 * @className ArticleComment
 * @author jinyu
 * @date May 19, 2017
 * @since JDK 1.6
 * @see
 */
public class ArticleComment implements Serializable {

    private static final long serialVersionUID = -8506024679132313314L;

    public enum ArticleCommentType {
        GENERAL, // 普通评论
        MARKELECT // 精选评论
    }
}
