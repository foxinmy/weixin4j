package com.foxinmy.weixin4j.mp.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.card.MemberInitInfo;
import com.foxinmy.weixin4j.model.card.MemberUpdateInfo;
import com.foxinmy.weixin4j.model.card.MemberUserForm;
import com.foxinmy.weixin4j.model.card.MemberUserInfo;
import com.foxinmy.weixin4j.token.TokenManager;

/**
 * 会员卡的api
 *
 * @auther: Feng Yapeng
 * @since: 2016/12/20 14:43
 * @see <a href='https://mp.weixin.qq.com/wiki/wiki?action=doc&id=mp1451025283&t=0.8029895777585161'>创建会员卡&会员卡管理</a>
 */
public class MemberCardApi extends CardApi {


    public MemberCardApi(TokenManager tokenManager) {
        super(tokenManager);
    }

    /**
     * 激活方式说明
     * 接口激活通常需要开发者开发用户填写资料的网页。通常有两种激活流程：
     * 1. 用户必须在填写资料后才能领卡，领卡后开发者调用激活接口为用户激活会员卡；
     * 2. 是用户可以先领取会员卡，点击激活会员卡跳转至开发者设置的资料填写页面，填写完成后开发者调用激活接口为用户激活会员卡。
     *
     * @see <a href='https://mp.weixin.qq.com/wiki?action=doc&id=mp1451025283&t=0.8029895777585161#6.1'>接口激活</a>
     */
    public ApiResult activate(MemberInitInfo memberInitInfo) throws WeixinException {
        String card_member_card_activate_uri = getRequestUri("card_member_card_activate_uri");
        Token token = tokenManager.getCache();
        WeixinResponse response = weixinExecutor
                .post(String.format(card_member_card_activate_uri, token.getAccessToken()), JSON.toJSONString(memberInitInfo));
        return response.getAsResult();
    }

    /**
     * 设置开卡字段接口
     * 开发者在创建时填入wx_activate字段后，
     * 需要调用该接口设置用户激活时需要填写的选项，否则一键开卡设置不生效。
     *
     * @see <a href='https://mp.weixin.qq.com/wiki?action=doc&id=mp1451025283&t=0.8029895777585161#6.2'>一键激活</a>
     */
    public ApiResult setActivateUserForm(MemberUserForm memberUserForm) throws WeixinException {
        String user_form_uri = getRequestUri("card_member_card_activate_user_form_uri");
        Token token = tokenManager.getCache();
        WeixinResponse response = weixinExecutor
                .post(String.format(user_form_uri, token.getAccessToken()), JSON.toJSONString(memberUserForm));
        return response.getAsResult();
    }

    /**
     * 拉取会员信息接口。
     *
     * @param cardId the card id
     * @param code   the code
     * @author fengyapeng
     * @since 2016 -12-21 11:28:45
     */
    public MemberUserInfo getMemberUserInfo(String cardId, String code) throws WeixinException {
        String user_info_uri = getRequestUri("card_member_card_user_info_uri");
        Token token = tokenManager.getCache();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("card_id", cardId);
        jsonObject.put("code", code);
        WeixinResponse response = weixinExecutor.post(String.format(user_info_uri, token.getAccessToken()), JSON.toJSONString(jsonObject));
        return response.getAsObject(new TypeReference<MemberUserInfo>() {
        });
    }

    /**
     * 更新会员
     * @param updateInfo
     * @return
     * @throws WeixinException
     */
    public JSONObject updateUserInfo(MemberUpdateInfo updateInfo) throws WeixinException {
        String card_member_card_update_user_uri = getRequestUri("card_member_card_update_user_uri");
        Token token = tokenManager.getCache();
        WeixinResponse response = weixinExecutor
                .post(String.format(card_member_card_update_user_uri, token.getAccessToken()), JSON.toJSONString(updateInfo));
        return response.getAsJson();
    }


}
