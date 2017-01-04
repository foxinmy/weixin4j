package com.foxinmy.weixin4j.mp.test;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.ApiResult;
import com.foxinmy.weixin4j.model.card.CardCoupons;
import com.foxinmy.weixin4j.model.card.CardQR;
import com.foxinmy.weixin4j.model.card.CouponAdvanceInfo;
import com.foxinmy.weixin4j.model.card.CouponBaseInfo;
import com.foxinmy.weixin4j.model.card.MemberCard;
import com.foxinmy.weixin4j.model.card.MemberInitInfo;
import com.foxinmy.weixin4j.model.card.MemberUpdateInfo;
import com.foxinmy.weixin4j.model.card.MemberUserForm;
import com.foxinmy.weixin4j.model.card.MemberUserInfo;
import com.foxinmy.weixin4j.model.qr.QRResult;
import com.foxinmy.weixin4j.mp.api.CardApi;
import com.foxinmy.weixin4j.type.card.ActivateCommonField;
import com.foxinmy.weixin4j.type.card.ActivateFormFieldType;
import com.foxinmy.weixin4j.type.card.CardCodeType;
import com.foxinmy.weixin4j.type.card.CardColor;
import com.foxinmy.weixin4j.type.card.FieldNameType;

import org.junit.Before;
import org.junit.Test;

/**
 * 会员卡测试
 *
 * @auther: Feng Yapeng
 * @since: 2016/12/21 16:37
 */
public class MemberCardTest extends TokenTest {



    private CardApi cardApi;

    @Before
    public void init() {

        cardApi = new CardApi(tokenManager);
    }

    /**
     * pn-YDwk59Ft0JSFdGqObxUccUQHw
     */
    @Test
    public void create() throws WeixinException {
        CouponBaseInfo.Builder builder = CardCoupons.customBase();
        // 基础必填字段
        builder.logoUrl(
                "http://mmbiz.qpic.cn/mmbiz_jpg/LtkLicv5iclfqzGpaDqDoMibM6FcMVTrmYXjLu7bJ1tM5MzCxNONQiaZHqrYzs0fTk2T5bLAAXLpvx32hQLmJTGBxQ/0")
               .codeType(CardCodeType.CODE_TYPE_BARCODE).brandName("***").title("***会员卡").cardColor(CardColor.Color010).notice("请出示会员卡")
               .description("***的会员卡的描述").quantity(10000);
        // 基础选填字段
        builder.canShare(false).canGiveFriend(false);
        builder.centerTitle("卡券居中按钮").centerSubTitle("显示在入口下方的提示语");
        MemberCard.Builder memberCardBuilder = CardCoupons.customMemberCard();
        //会员卡必填字段
        // 会员卡选填字段
        memberCardBuilder.prerogative("会员卡特权说明").supplyBalance(true).supplyBonus(false).activateWithWx(true);
        memberCardBuilder.customField1(FieldNameType.FIELD_NAME_TYPE_LEVEL, "等级", null);
        memberCardBuilder.backgroundPicUrl(
                "https://mmbiz.qlogo.cn/mmbiz/2FyQ9TURqmdibM6nYBiagZT49lSlY9Aicw4P3vsoa7dEZIYfNkiaMyzNVYT9jmYhjBbeC8jnkibwbibB5tghC5XcgysQ/0?wx_fmt=jpeg");

        MemberCard memberCard = CardCoupons.createMemberCard(builder, memberCardBuilder);
        String cardId = cardApi.createCardCoupon(memberCard);
        System.out.println(cardId);
    }


    @Test
    public void createCardQR() throws WeixinException {
        CardQR.Builder builder = new CardQR.Builder("pn-YDwk59Ft0JSFdGqObxUccUQHw");
        QRResult qrResult = cardApi.createCardQR(36000, builder.build());
        String showUrl = qrResult.getShowUrl();
        System.out.println(showUrl);
    }


    @Test
    public void setMemberUserForm() throws WeixinException {
        MemberUserForm memberUserForm = new MemberUserForm();
        memberUserForm.setCardId("pn-YDwk59Ft0JSFdGqObxUccUQHw");
        MemberUserForm.FormBudiler requiredForm = new MemberUserForm.FormBudiler();
        requiredForm.canModify(false);
        requiredForm.addCommonField(ActivateCommonField.USER_FORM_INFO_FLAG_EMAIL, ActivateCommonField.USER_FORM_INFO_FLAG_BIRTHDAY,
                                    ActivateCommonField.USER_FORM_INFO_FLAG_MOBILE)
                    .addRichField(ActivateFormFieldType.FORM_FIELD_CHECK_BOX, "checkBox", "value1", "value2", "value3");

        memberUserForm.setRequiredForm(requiredForm);
        MemberUserForm.FormBudiler optionalFormBuilder = new MemberUserForm.FormBudiler();
        optionalFormBuilder.canModify(false);
        optionalFormBuilder.addCommonField(ActivateCommonField.USER_FORM_INFO_FLAG_IDCARD)
                           .addRichField(ActivateFormFieldType.FORM_FIELD_CHECK_BOX, "checkBoxOPt", "value1", "value2", "value3");
        memberUserForm.setOptionalForm(optionalFormBuilder);
        memberUserForm.setServiceStatement("会员守则","https://www.baidu.com");
        ApiResult apiResult = cardApi.setActivateUserForm(memberUserForm);
    }


    @Test
    public void getMemberUserInfo() throws WeixinException {
       MemberUserInfo memberUserInfo =  cardApi.getMemberUserInfo("pn-YDwk59Ft0JSFdGqObxUccUQHw", "270869833860");
        System.out.println(memberUserInfo);
    }

    @Test
    public void initMemberUser() throws WeixinException {
        MemberInitInfo memberInitInfo = new MemberInitInfo();
        memberInitInfo.setCardId("pn-YDwk59Ft0JSFdGqObxUccUQHw");
        memberInitInfo.setCode("270869833860");
        memberInitInfo.setBackgroundPicUrl("https://mmbiz.qlogo.cn/mmbiz/2FyQ9TURqmdibM6nYBiagZT49lSlY9Aicw4HnSKzouD9iaksVA8vIbFT3RuqnWDVMNZib21NDdwKn5OMVMwfSsULXGw/0?wx_fmt=jpeg");
        memberInitInfo.setInit_custom_field_value1("铂金");
        memberInitInfo.setInitBalance(2);
        memberInitInfo.setInitBonus(2);
        memberInitInfo.setInitBonusRecord("初始化积分");
        ApiResult activate = cardApi.activateMemberCard(memberInitInfo);
        System.out.println(activate);
    }

    @Test
    public void updateMmemberUser() throws WeixinException {
        MemberUpdateInfo memberUpdateInfo = new MemberUpdateInfo();
        memberUpdateInfo.setCardId("pn-YDwk59Ft0JSFdGqObxUccUQHw");
        memberUpdateInfo.setCode("270869833860");
        memberUpdateInfo.setAddBalance(20);
        memberUpdateInfo.setRecordBalance("充值");
        memberUpdateInfo.setNOtify(true,true);
        memberUpdateInfo.setCustomFieldValue1("至尊铂金",true);
        cardApi.updateMemberUserInfo(memberUpdateInfo);
    }


    @Test
    public void update() throws WeixinException {
        CouponBaseInfo.Builder builder = CardCoupons.customBase();
        // 基础必填字段
        builder.logoUrl(
                "http://mmbiz.qpic.cn/mmbiz_jpg/LtkLicv5iclfqzGpaDqDoMibM6FcMVTrmYXjLu7bJ1tM5MzCxNONQiaZHqrYzs0fTk2T5bLAAXLpvx32hQLmJTGBxQ/0")
               .codeType(CardCodeType.CODE_TYPE_BARCODE).brandName("***").title("***会员卡").cardColor(CardColor.Color010).notice("请出示会员卡")
               .description("***的会员卡的描述").quantity(10000);
        // 基础选填字段
        builder.canShare(false).canGiveFriend(false);
        builder.centerTitle("卡券居中按钮").centerSubTitle("显示在入口下方的提示语");
        MemberCard.Builder memberCardBuilder = CardCoupons.customMemberCard();
        //会员卡必填字段
        // 会员卡选填字段
        memberCardBuilder.prerogative("会员卡特权说明").supplyBalance(false).supplyBonus(false).activateWithWx(true);
        memberCardBuilder.customField1(FieldNameType.FIELD_NAME_TYPE_LEVEL, "等级", null);
        memberCardBuilder.backgroundPicUrl(
                "https://mmbiz.qlogo.cn/mmbiz/2FyQ9TURqmdibM6nYBiagZT49lSlY9Aicw4P3vsoa7dEZIYfNkiaMyzNVYT9jmYhjBbeC8jnkibwbibB5tghC5XcgysQ/0?wx_fmt=jpeg");

        MemberCard memberCard = CardCoupons.createMemberCard(builder, memberCardBuilder);
        CouponAdvanceInfo.Builder advanceBuilder =  new CouponAdvanceInfo.Builder();
        advanceBuilder.slideImage("此菜品精选食材，以独特的烹饪方法，最大程度地刺激食 客的味蕾","http://mmbiz.qpic.cn/mmbiz/p98FjXy8LacgHxp3sJ3vn97bGLz0ib0Sfz1bjiaoOYA027iasqSG0sjpiby4vce3AtaPu6cIhBHkt6IjlkY9YnDsfw/0");
        memberCard.setCouponAdvanceInfo(advanceBuilder.build());
        Boolean cardCoupon = cardApi.updateCardCoupon("pn-YDwk59Ft0JSFdGqObxUccUQHw", memberCard);
        System.out.println(cardCoupon);

    }

}
