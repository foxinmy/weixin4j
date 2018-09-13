package com.foxinmy.weixin4j.payment.mch;

import java.util.HashMap;
import java.util.Map;

import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.payment.PayRequest;
import com.foxinmy.weixin4j.type.SignType;
import com.foxinmy.weixin4j.type.TradeType;
import com.foxinmy.weixin4j.util.DigestUtil;
import com.foxinmy.weixin4j.util.MapUtil;

/**
 * APP支付
 *
 * @className APPPayRequest
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年12月25日
 * @since JDK 1.6
 * @see com.foxinmy.weixin4j.payment.mch.PrePay
 * @see com.foxinmy.weixin4j.payment.PayRequest
 * @see <a href=
 *      "https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=8_1">APP支付</a>
 */
public class APPPayRequest extends AbstractPayRequest {
    public APPPayRequest(String prePayId, WeixinPayAccount payAccount) {
        super(prePayId, payAccount);
    }

    @Override
    public TradeType getPaymentType() {
        return TradeType.APP;
    }

    /**
     * <font color="red">只做查看之用,请不要尝试作为支付请求</font>
     */
    @Override
    public PayRequest toRequestObject() {
        PayRequest payRequest = new PayRequest(getPaymentAccount().getId(), "Sign=WXPay");
        payRequest.setPartnerId(getPaymentAccount().getMchId());
        payRequest.setPrepayId(getPrePayId());
        Map<String, String> map = new HashMap<String, String>();
        map.put("appid", payRequest.getAppId());
        // 因为partnerid和prepayid在PayRequest类中是不进行序列化的
        map.put("partnerid", payRequest.getPartnerId());
        map.put("prepayid", payRequest.getPrepayId());
        map.put("package", payRequest.getPackageInfo());
        map.put("timestamp", payRequest.getTimeStamp());
        map.put("noncestr", payRequest.getNonceStr());
        String sign = DigestUtil.MD5(
                String.format("%s&key=%s", MapUtil.toJoinString(map, false, true), getPaymentAccount().getPaySignKey()))
                .toUpperCase();
        payRequest.setPaySign(sign);
        payRequest.setSignType(SignType.MD5);
        return payRequest;
    }

    @Override
    public String toRequestString() {
        PayRequest payRequest = toRequestObject();
        StringBuilder content = new StringBuilder();
        content.append("<xml>");
        content.append(String.format("<appid><![CDATA[%s]]></appid>", payRequest.getAppId()));
        content.append(String.format("<partnerid><![CDATA[%s]]></partnerid>", payRequest.getPartnerId()));
        content.append(String.format("<prepayid><![CDATA[%s]]></prepayid>", payRequest.getPrepayId()));
        content.append(String.format("<package><![CDATA[%s]]></package>", payRequest.getPackageInfo()));
        content.append(String.format("<noncestr><![CDATA[%s]]></noncestr>", payRequest.getNonceStr()));
        content.append(String.format("<timestamp><![CDATA[%s]]></timestamp>", payRequest.getTimeStamp()));
        content.append(String.format("<sign><![CDATA[%s]]></sign>", payRequest.getPaySign()));
        content.append("</xml>");
        return content.toString();
    }
}
