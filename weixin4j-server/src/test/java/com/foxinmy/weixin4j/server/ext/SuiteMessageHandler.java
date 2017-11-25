package com.foxinmy.weixin4j.server.ext;

import java.util.Set;

import com.foxinmy.weixin4j.handler.WeixinMessageHandler;
import com.foxinmy.weixin4j.qy.suite.SuiteEventType;
import com.foxinmy.weixin4j.qy.suite.SuiteMessage;
import com.foxinmy.weixin4j.request.WeixinMessage;
import com.foxinmy.weixin4j.request.WeixinRequest;
import com.foxinmy.weixin4j.response.BlankResponse;
import com.foxinmy.weixin4j.response.WeixinResponse;

/**
 * 企业号套件消息处理
 *
 * @className SuiteMessageHandler
 * @author jy
 * @date 2015年6月25日
 * @since JDK 1.6
 */
public class SuiteMessageHandler implements WeixinMessageHandler {

    @Override
    public boolean canHandle(WeixinRequest request, WeixinMessage message, Set<String> nodeNames) {
        return nodeNames.contains("suiteid");
    }

    @Override
    public WeixinResponse doHandle(WeixinRequest request, WeixinMessage message) {
        SuiteMessage suiteMessage = null; // 转换为 SuiteMessage
        SuiteEventType eventType = suiteMessage.getFormatEventType();
        if (eventType == SuiteEventType.suite_ticket) {
            // do something
        } else if (eventType == SuiteEventType.change_auth) {
            // do something
        } else if (eventType == SuiteEventType.cancel_auth) {
            // do something
        }
        return BlankResponse.global;
    }

    @Override
    public int weight() {
        return 0;
    }
}
