package com.afiona.center.client.promotion.constants;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 活动促销类型
 * @author LiJinXing
 * @date 2020/2/23
 * */

public enum PromotionType implements IEnum<Integer> {

    /**
     * 优惠券
     */
    COUPON(0, "优惠券",200000000L),

    /**
     * 满减/送
     */
    FULL_REDUCTION(1, "满减/送",500000000L),

    /**
     * 组合/套装
     */
    SUIT_GROUP(2, "组合/套装",300000000L),

    /**
     * 一口价
     */
    FIXED_PRICE(3, "一口价",600000000L),

    /**
     * 限时折扣
     */
    SPECIAL_DISCOUNT(4, "限时折扣",700000000L),

    /**
     * 加价购
     */
    ADD_MONEY_TO_BUY_GOODS(5,"加价购",400000000L),

    /**
     * 支付有礼
     */
    PAY_GIFT(6,"支付有礼",100000000L),

    /**
     * 秒杀
     */
    SECONDS_KILL(7,"秒杀",0L),

    /**
     * 预售
     */
    PRESELL(8,"预售",0L);

    @EnumValue
    private final int code;

    private final String message;

    private final Long priority;

    PromotionType(int code, String message,Long priority) {
        this.code = code;
        this.message = message;
        this.priority =priority;
    }

    /**
     * 获取code
     *
     * @return code
     */
    public int getCode() {
        return code;
    }

    /**
     * 获取信息
     *
     * @return 信息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 优先级值
     */
    public Long getPriority(){return priority;}

    @Override
    public Integer getValue() {
        return code;
    }
}
