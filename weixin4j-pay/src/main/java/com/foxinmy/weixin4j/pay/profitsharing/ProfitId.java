package com.foxinmy.weixin4j.pay.profitsharing;

import com.foxinmy.weixin4j.pay.type.ProfitIdType;

/**
 * 分帐单单号
 *
 * @author kit
 * @date 2020年05月25日
 * @since weixin4j-pay 1.1.0
 */
public class ProfitId {
    private String id;
    private ProfitIdType idType;

    public ProfitId(String id, ProfitIdType type){
        this.id = id;
        this.idType = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProfitIdType getIdType() {
        return idType;
    }

    public void setIdType(ProfitIdType idType) {
        this.idType = idType;
    }
}
