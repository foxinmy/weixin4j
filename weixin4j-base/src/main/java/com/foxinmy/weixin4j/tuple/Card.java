package com.foxinmy.weixin4j.tuple;

import javax.xml.bind.annotation.XmlElement;

import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 卡券对象
 * <p>
 * <font color="red">可用于「群发消息」和「客服消息」</font>
 * </p>
 *
 * @className Card
 * @author jinyu(foxinmy@gmail.com)
 * @date 2015年6月8日
 * @since JDK 1.6
 * @see
 */
public class Card implements MassTuple, NotifyTuple {

    private static final long serialVersionUID = 6119453633595102147L;

    @Override
    public String getMessageType() {
        return "wxcard";
    }

    /**
     * 上传后的微信返回的媒体ID
     */
    @JSONField(name = "card_id")
    @XmlElement(name = "CardId")
    private String cardId;

    @JSONCreator
    public Card(@JSONField(name = "cardId") String cardId) {
        this.cardId = cardId;
    }

    public String getCardId() {
        return cardId;
    }

    @Override
    public String toString() {
        return "Card [cardId=" + cardId + "]";
    }
}
