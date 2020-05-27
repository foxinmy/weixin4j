package com.foxinmy.weixin4j.pay.api;

import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.pay.model.WeixinPayAccount;
import com.foxinmy.weixin4j.pay.profitsharing.*;
import com.foxinmy.weixin4j.pay.type.ProfitIdType;
import com.foxinmy.weixin4j.pay.type.SignType;
import com.foxinmy.weixin4j.pay.type.profitsharing.ReturnAccountType;
import com.foxinmy.weixin4j.xml.XmlStream;

import java.util.List;

/**
 * 微信商户平台分账接口（直连商户/服务商）
 *
 * @author kit (kit.li@qq.com)
 * @date 2020年05月20日
 * @since weixin4j-pay 1.1.0
 */
public class ProfitSharingApi extends MchApi {

    public ProfitSharingApi(WeixinPayAccount weixinAccount) {
        super(weixinAccount);
    }

    /**
     * 添加分账接收方
     * 服务商代子商户发起添加分账接收方请求，后续可通过发起分账请求将结算后的钱分到该分账接收方。
     *
     * @param receiver
     *          分帐接收方
     * @return
     * @see Receiver
     * @see ReceiverResult
     * @see <a href="https://pay.weixin.qq.com/wiki/doc/api/allocation_sl.php?chapter=25_3&index=4">添加分账接收方</a>
     *
     */
    public ReceiverResult addReceiver(Receiver receiver) throws WeixinException {
        ReceiverRequest receiverRequest = new ReceiverRequest(receiver);
        super.declareMerchant(receiverRequest);
        String url = getRequestUri("profit_sharing_add_receiver_uri");
        receiverRequest.setSign(weixinSignature.sign(receiverRequest, SignType.HMAC$SHA256));
        String para = XmlStream.toXML(receiverRequest);
        WeixinResponse response = weixinExecutor.post(url, para);
        return response.getAsObject(new TypeReference<ReceiverResult>(){});
    }

    /**
     * 删除分账接收方
     * 商户发起删除分账接收方请求，删除后不支持将结算后的钱分到该分账接收方。
     *
     * @param receiver
     *          分帐接收方
     * @return
     * @throws WeixinException
     * @see Receiver
     * @see ReceiverResult
     * @see <a href="https://pay.weixin.qq.com/wiki/doc/api/allocation.php?chapter=27_4&index=5">删除分账接收方</a>
     */
    public ReceiverResult removeReceiver(Receiver receiver) throws WeixinException {
        ReceiverRequest receiverRequest = new ReceiverRequest(receiver);
        super.declareMerchant(receiverRequest);
        String url = getRequestUri("profit_sharing_remove_receiver_uri");
        receiverRequest.setSign(weixinSignature.sign(receiverRequest, SignType.HMAC$SHA256));
        String para = XmlStream.toXML(receiverRequest);
        WeixinResponse response = weixinExecutor.post(url, para);
        return response.getAsObject(new TypeReference<ReceiverResult>(){});
    }

    /**
     * 请求分帐
     *
     * @param transactionId
     *          微信订单号
     * @param outOrderNo
     *          商户分帐单号
     * @param receivers
     *          分帐接收方
     * @param multi
     *          是否多次分帐，默认为单次分帐，即调用分帐成功后马上解冻剩余金额给商户，不需要完结分帐。多次分帐可多次调用分帐API，需调完结分帐结束分帐
     * @return
     * @throws WeixinException
     * @see <a href="https://pay.weixin.qq.com/wiki/doc/api/allocation.php?chapter=27_1&index=1">请求单次分帐</a>
     */
    public ProfitSharingResult profitSharing(String transactionId, String outOrderNo, List<ReceiverProfit> receivers,
                                             Boolean multi) throws WeixinException {
        ProfitSharingRequest request = new ProfitSharingRequest(transactionId, outOrderNo, receivers);
        super.declareMerchant(request);
        String url = multi==null || multi.booleanValue()==false ? getRequestUri("profit_sharing_uri") :
                getRequestUri("multi_profit_sharing_uri");
        request.setSign(weixinSignature.sign(request, SignType.HMAC$SHA256));
        String para = XmlStream.toXML(request);
        WeixinResponse response = getWeixinSSLExecutor().post(url, para);
        return response.getAsObject(new TypeReference<ProfitSharingResult>(){});
    }

    /**
     * 分帐查询
     *
     * @param transactionId
     *          微信订单号
     * @param outOrderNo
     *          商户分帐单号
     * @return
     * @throws WeixinException
     * @see <a href="https://pay.weixin.qq.com/wiki/doc/api/allocation.php?chapter=27_2&index=3">分帐查询</a>
     */
    public ProfitSharingResult profitSharingQuery(String transactionId, String outOrderNo) throws WeixinException {
        ProfitSharingRequest request = new ProfitSharingRequest(transactionId, outOrderNo, null);
        super.declareMerchant(request);
        String url = getRequestUri("profit_sharing_query_uri");
        request.setSign(weixinSignature.sign(request, SignType.HMAC$SHA256));
        String para = XmlStream.toXML(request);
        WeixinResponse response = weixinExecutor.post(url, para);
        return response.getAsObject(new TypeReference<ProfitSharingResult>(){});
    }

    /**
     * 完结分账
     * 1、不需要进行分账的订单，可直接调用本接口将订单的金额全部解冻给本商户
     * 2、调用多次分账接口后，需要解冻剩余资金时，调用本接口将剩余的分账金额全部解冻给特约商户
     * 3、已调用请求单次分账后，剩余待分账金额为零，不需要再调用此接口。
     *
     * @param transactionId
     *          微信订单号
     * @param outOrderNo
     *          商户分帐单号
     * @param description
     *          分帐完结描述
     * @return
     * @throws WeixinException
     * @see <a href="https://pay.weixin.qq.com/wiki/doc/api/allocation.php?chapter=27_5&index=6">完结分账</a>
     */
    public ProfitSharingResult profitSharingFinish(String transactionId, String outOrderNo, String description)
            throws WeixinException {
        ProfitSharingRequest request = new ProfitSharingRequest(transactionId, outOrderNo, null);
        request.setDescription(description);
        super.declareMerchant(request);
        String url = getRequestUri("profit_sharing_finish_uri");
        request.setSign(weixinSignature.sign(request, SignType.HMAC$SHA256));
        String para = XmlStream.toXML(request);
        WeixinResponse response = getWeixinSSLExecutor().post(url, para);
        return response.getAsObject(new TypeReference<ProfitSharingResult>(){});
    }

    /**
     * 分账回退
     *
     * @param id
     *          分帐单号
     * @param outReturnNo
     *          商户回退单号
     * @param returnAccountType
     *          回退方类型
     * @param returnAccount
     *          回退方账号
     * @param returnAmount
     *          回退金额
     * @param description
     *          回退描述
     * @return
     * @throws WeixinException
     * @see ProfitSharingReturnRequest
     * @see <a href="https://pay.weixin.qq.com/wiki/doc/api/allocation.php?chapter=27_7&index=7">分账回退</a>
     */
    public ProfitSharingReturnResult profitSharingReturn(ProfitId id, String outReturnNo,
                                                         ReturnAccountType returnAccountType, String returnAccount,
                                                         int returnAmount, String description) throws WeixinException{
        ProfitSharingReturnRequest request;
        if(id.getIdType()== ProfitIdType.ORDER_ID){
            request = new ProfitSharingReturnRequest(id.getId(), null, outReturnNo, returnAccountType,
                    returnAccount, returnAmount, description);
        }else{
            request = new ProfitSharingReturnRequest(null, id.getId(), outReturnNo, returnAccountType,
                    returnAccount, returnAmount, description);
        }
        super.declareMerchant(request);
        String url = getRequestUri("profit_sharing_return_uri");
        request.setSign(weixinSignature.sign(request, SignType.HMAC$SHA256));
        String para = XmlStream.toXML(request);
        WeixinResponse response = getWeixinSSLExecutor().post(url, para);
        return response.getAsObject(new TypeReference<ProfitSharingReturnResult>(){});
    }

    /**
     * 回退结果查询
     *
     * @param id
     *          分帐单号
     * @param outReturnNo
     *          商户回退单号
     * @return
     * @throws WeixinException
     * @see ProfitSharingReturnRequest
     * @see <a href="https://pay.weixin.qq.com/wiki/doc/api/allocation.php?chapter=27_8&index=8">回退结果查询</a>
     */
    public ProfitSharingReturnResult profitSharingReturnQuery(ProfitId id, String outReturnNo) throws WeixinException{
        ProfitSharingReturnRequest request;
        if(id.getIdType()== ProfitIdType.ORDER_ID){
            request = new ProfitSharingReturnRequest(id.getId(), null, outReturnNo);
        }else{
            request = new ProfitSharingReturnRequest(null, id.getId(), outReturnNo);
        }
        super.declareMerchant(request);
        String url = getRequestUri("profit_sharing_return_query_uri");
        request.setSign(weixinSignature.sign(request, SignType.HMAC$SHA256));
        String para = XmlStream.toXML(request);
        WeixinResponse response = weixinExecutor.post(url, para);
        return response.getAsObject(new TypeReference<ProfitSharingReturnResult>(){});
    }
}
