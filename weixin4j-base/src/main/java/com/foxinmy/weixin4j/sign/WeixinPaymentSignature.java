package com.foxinmy.weixin4j.sign;

import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.type.SignType;
import com.foxinmy.weixin4j.util.DigestUtil;
import com.foxinmy.weixin4j.xml.XmlStream;

import java.util.Map;

/**
 * 微信支付签名实现
 *
 * @author jinyu(foxinmy@gmail.com)
 * @className WeixinPaymentSignature
 * @date 2016年3月26日
 * @see <a
 * href="https://pay.weixin.qq.com/wiki/doc/api/external/jsapi.php?chapter=4_3">支付签名说明</a>
 * @since JDK 1.6
 */
public class WeixinPaymentSignature extends AbstractWeixinSignature {

    /**
     * 支付密钥
     */
    private final String paySignKey;

    public WeixinPaymentSignature(String paySignKey) {
        this.paySignKey = paySignKey;
    }

    @Override
    public SignType getSignType() {
        return SignType.MD5;
    }

    @Override
    public String sign(Object obj) {
        StringBuilder sb = join(obj).append("&key=").append(paySignKey);
        return DigestUtil.MD5(sb.toString()).toUpperCase();
    }


    public boolean validatePaySign(WeixinResponse weixinResponse) {
        return this.validatePaySign(weixinResponse.getAsString());

    }

    public boolean validatePaySign(String xmlResult) {
        return this.validatePaySign(XmlStream.xml2map(xmlResult));
    }

    public boolean validatePaySign(Map<String, String> map) {
        String sign1 = map.get("sign");
        map.remove("sign");
        String sign2 = this.sign(map);
        return sign1.equals(sign2);
    }


}
