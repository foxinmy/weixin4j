package com.foxinmy.weixin4j.mp.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.model.media.MediaUploadResult;
import com.foxinmy.weixin4j.mp.api.MassApi;
import com.foxinmy.weixin4j.mp.api.MediaApi;
import com.foxinmy.weixin4j.tuple.Image;
import com.foxinmy.weixin4j.tuple.MpArticle;
import com.foxinmy.weixin4j.tuple.Text;

/**
 * 群发消息
 *
 * @className MpNewsTest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年4月27日
 * @since JDK 1.6
 * @see
 */
public class MassTest extends TokenTest {
    private MassApi massApi;
    private MediaApi mediaApi;

    @Before
    public void init() {
        this.massApi = new MassApi(tokenManager);
        this.mediaApi = new MediaApi(tokenManager);
    }

    @Test
    public void uploadArticle() throws IOException, WeixinException {
        List<MpArticle> articles = new ArrayList<MpArticle>();
        File file = new File("/tmp/test.jpg");
        MediaUploadResult mediaResult = mediaApi.uploadMedia(false, new FileInputStream(file), file.getName());
        articles.add(new MpArticle(mediaResult.getMediaId(), "title", "content"));
        massApi.uploadArticle(articles);
    }

    @Test
    public void massByGroupId() throws WeixinException {
        String[] msgId = massApi.massByGroupId(new Image("mediaId"), true, 0);
        Assert.assertTrue(msgId[0] != null);
    }

    @Test
    public void massByOpenIds() throws WeixinException {
        String[] msgId = massApi.massByOpenIds(new Text("HI"), "oyFLst1bqtuTcxK-ojF8hOGtLQao");
        Assert.assertTrue(msgId[0] != null);
    }

    @Test
    public void massArticleByGroup() throws IOException, WeixinException {
        List<MpArticle> articles = new ArrayList<MpArticle>();
        File file = new File("/tmp/test.jpg");
        MediaUploadResult mediaResult = mediaApi.uploadMedia(false, new FileInputStream(file), file.getName());
        articles.add(new MpArticle(mediaResult.getMediaId(), "title", "content"));
        String[] massId = massApi.massArticleByGroupId(articles, 0);
        Assert.assertTrue(massId[0] != null);
    }

    @Test
    public void massArticleByOpenIds() throws IOException, WeixinException {
        List<MpArticle> articles = new ArrayList<MpArticle>();
        File file = new File("/tmp/test.jpg");
        MediaUploadResult mediaResult = mediaApi.uploadMedia(false, new FileInputStream(file), file.getName());
        articles.add(new MpArticle(mediaResult.getMediaId(), "title", "content"));
        String[] massId = massApi.massArticleByOpenIds(articles, false, "owGBft_vbBbOaQOmpEUE4xDLeRSU");
        Assert.assertTrue(massId[0] != null);
    }

    @Test
    public void deleteMass() throws WeixinException {
        ApiResult result = massApi.deleteMassNews("34182");
        Assert.assertEquals("0", result.getReturnCode());
    }

    @Test
    public void previewMass() throws WeixinException {
        ApiResult result = massApi.previewMassNews("oyFLst1bqtuTcxK-ojF8hOGtLQao", null, new Text("test"));
        Assert.assertEquals("0", result.getReturnCode());
    }

    @Test
    public void getMassNews() throws WeixinException {
        String status = massApi.getMassNewStatus("82358");
        System.out.println(status);
        Assert.assertNotNull(status);
    }
}
