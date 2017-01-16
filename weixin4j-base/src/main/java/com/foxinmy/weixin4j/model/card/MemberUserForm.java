package com.foxinmy.weixin4j.model.card;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.foxinmy.weixin4j.type.card.ActivateCommonField;
import com.foxinmy.weixin4j.type.card.ActivateFormFieldType;

import java.util.HashSet;

/**
 * 普通一键激活 中设置会员卡
 *
 * @auther: Feng Yapeng
 * @since: 2016/12/20 16:25
 */
public class MemberUserForm {

    /**
     * 卡券ID。
     */
    @JSONField(name = "card_id")
    private String cardId;

    /**
     * 服务声明，用于放置商户会员卡守则
     */
    @JSONField(name = "service_statement")
    private JSONObject serviceStatement;
    /**
     * 绑定老会员链接
     */
    @JSONField(name = "bind_old_card")
    private JSONObject bindOldCard;

    /**
     *设置必填的from
     */
    @JSONField(name = "required_form")
    private FormBudiler requiredForm;

    /**
     * 设置选填的form
     */
    @JSONField(name = "optional_form")
    private FormBudiler optionalForm;


    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public JSONObject getServiceStatement() {
        return serviceStatement;
    }

    public void setServiceStatement(String name,String url) {
        JSONObject serviceStatement = new JSONObject();
        serviceStatement.put("name",name);
        serviceStatement.put("url",url);
        this.serviceStatement = serviceStatement;
    }

    public JSONObject getBindOldCard() {
        return bindOldCard;
    }

    public void setBindOldCard(String name,String url) {
        JSONObject bindOldCard = new JSONObject();
        bindOldCard.put("name",name);
        bindOldCard.put("url",url);
        this.bindOldCard = bindOldCard;
    }

    public void setRequiredForm(FormBudiler formBudiler) {
        this.requiredForm = formBudiler;
    }

    public void setOptionalForm(FormBudiler formBudiler) {
        this.optionalForm = formBudiler;
    }

    public FormBudiler getRequiredForm() {
        return requiredForm;
    }

    public FormBudiler getOptionalForm() {
        return optionalForm;
    }

    public final static class FormBudiler {

        /**
         * 当前结构（required_form或者optional_form ）内
         * 的字段是否允许用户激活后再次修改，商户设置为true
         * 时，需要接收相应事件通知处理修改事件
         */
        @JSONField(name = "can_modify")
        private boolean   canModify;
        /**
         * 自定义富文本类型，包含以下三个字段
         */
        @JSONField(name = "rich_field_list")
        private JSONArray richFieldList;

        /**
         * 微信格式化的选项类型
         */
        @JSONField(name = "common_field_id_list")
        private HashSet<ActivateCommonField> commonFieldIdList;

        /**
         * 自定义选项名称。
         */
        @JSONField(name = "custom_field_list")
        private HashSet<String> customFieldList;

        /**
         * 自定义富文本类型
         */
        public FormBudiler addRichField(ActivateFormFieldType fieldType, String name, String... values) {
            if (richFieldList == null) {
                richFieldList = new JSONArray();
            }
            JSONObject obj = new JSONObject();
            obj.put("type", fieldType);
            obj.put("name", name);
            obj.put("values", values);
            richFieldList.add(obj);
            return this;
        }

        public FormBudiler canModify(boolean modify){
            this.canModify = modify;
            return this;
        }


        /**
         * 自定义公共字段
         */
        public FormBudiler addCommonField(ActivateCommonField... fields) {
            if (commonFieldIdList == null) {
                commonFieldIdList = new HashSet<ActivateCommonField>();
            }
            for (ActivateCommonField field : fields) {
                commonFieldIdList.add(field);
            }
            return this;
        }

        /**
         * 增加自定义的内容
         * @param names
         */
        public FormBudiler addCustomField(String... names) {
            if (customFieldList == null) {
                customFieldList = new HashSet<String>();
            }
            for (String name : names) {
                customFieldList.add(name);
            }
            return this;
        }

        public boolean isCanModify() {
            return canModify;
        }

        public JSONArray getRichFieldList() {
            return richFieldList;
        }

        public HashSet<ActivateCommonField> getCommonFieldIdList() {
            return commonFieldIdList;
        }

        public HashSet<String> getCustomFieldList() {
            return customFieldList;
        }
    }

}
