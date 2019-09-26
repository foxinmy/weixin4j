package com.foxinmy.weixin4j.pay.payment.face;

import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.pay.payment.mch.MerchantResult;

import javax.xml.bind.annotation.XmlElement;

/**
 * 微信刷脸支付交互流程--获取调用凭证(get_wxpayface_authinfo)接口响应结果
 *
 * @className PayfaceAuthinfo
 * @author kit(kit_21cn@21cn.com)
 * @date 2019年9月17日
 * @since JDK 1.6
 * @see
 */
public class PayfaceAuthinfo extends MerchantResult {
    /**
     * authinfo的有效时间, 单位秒。 例如: 3600
     * 在有效时间内, 对于同一台终端设备，相同的参数的前提下(如：相同的公众号、商户号、 门店编号等），可以用同一个authinfo，
     * 多次调用SDK的getWxpayfaceCode接口。
     *
     * @see <a href="https://pay.weixin.qq.com/wiki/doc/wxfacepay/develop/sdk-android.html#人脸支付凭证-getwxpayfacecode">
     *     人脸支付凭证(getWxpayfaceCode)</a>
     */
    @JSONField(name = "expires_in")
    @XmlElement(name = "expires_in")
    private int expiresIn;
    /**
     * SDK调用凭证。用于调用SDK的人脸识别接口。
     *
     * @see <a href="https://pay.weixin.qq.com/wiki/doc/wxfacepay/develop/sdk-android.html#人脸支付凭证-getwxpayfacecode">
     *     人脸支付凭证(getWxpayfaceCode)</a>
     */
    private String authinfo;

    @Override
    public String toString() {
        return "PayfaceAuthinfo{" +
                "expiresIn=" + expiresIn +
                ", authinfo='" + authinfo + '\'' +
                '}';
    }
}
