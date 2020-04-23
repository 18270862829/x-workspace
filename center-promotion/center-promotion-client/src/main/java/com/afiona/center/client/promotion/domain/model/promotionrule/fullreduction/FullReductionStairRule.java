package com.afiona.center.client.promotion.domain.model.promotionrule.fullreduction;

import com.afiona.center.client.promotion.constants.PreferentialType;
import com.afiona.center.client.promotion.constants.choice.*;
import com.afiona.center.client.promotion.model.Benefit;
import com.afiona.center.client.promotion.model.PromotionStairRuleKey;
import com.afiona.common.model.SuperEntity;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 阶梯满减规则
 *
 * @author dengweiyi
 * @date 2020-02-12
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("阶梯满减规则")
public class FullReductionStairRule extends SuperEntity implements Comparable<FullReductionStairRule>{
    /**
     * 促销活动ID
     */
    private Long promotionId;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 优惠门槛
     */
    @ApiModelProperty("满减送-优惠门槛")
    private PreferentialThreshold preferentialThreshold;

    /**
     * 优惠内容
     */
    private PreferentialContent preferentialContent;

    public PromotionStairRuleKey getKey(){
        return new PromotionStairRuleKey(promotionId, getId());
    }

    @Override
    public int compareTo(FullReductionStairRule o) {
        return this.level-o.level;
    }

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = false)
    @ApiModel("满减送-优惠门槛")
    public static class PreferentialThreshold {
        /**
         * 优惠门槛选择
         */
        private ThresholdChoiceEnum thresholdChoice;

        /**
         * 满x元
         */
        private BigDecimal fullAmount;

        /**
         * 满x元条件选择
         */
        private FullPriceChoiceEnum fullPriceChoice;

        /**
         * 满x件
         */
        private Integer fullNum;

        /**
         * 满x件条件选择
         */
        private FullNumChoiceEnum fullNumChoice;

        /**
         * 是否满足优惠门槛
         *
         * @param goodsTotalPrice
         * @param goodsNum
         * @return : boolean
         */
        public boolean qualified(BigDecimal goodsTotalPrice, int goodsNum){
            if(thresholdChoice == ThresholdChoiceEnum.AMOUNT){
                if(goodsTotalPrice.compareTo(fullAmount) >= 0){
                    return true;
                }
            }else{
                if(goodsNum >= fullNum){
                    return true;
                }
            }
            return false;
        }

        /**
         * 优惠次数（针对循环满减）
         *
         * @param goodsTotalPrice
         * @param goodsNum
         * @return : int
         */
        public int times(BigDecimal goodsTotalPrice, int goodsNum){
            if(thresholdChoice == ThresholdChoiceEnum.AMOUNT){
                return goodsTotalPrice.divide(fullAmount).intValue();
            }else{
                return goodsNum / fullNum;
            }
        }
    }

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = false)
    public static class Amount{
        /**
         * 金额优惠选择
         */
        private AmountChoiceEnum amountChoice;
        /**
         * 减x元
         */
        private BigDecimal reduceAmount;

        /**
         * 折扣
         */
        private BigDecimal discount;

        /**
         * 封顶
         */
        private BigDecimal limit;
    }

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = false)
    public static class Gift{
        /**
         * 送礼选择
         */
        private GiftChoiceEnum giftChoice;

        /**
         * 积分
         */
        private Integer score;

        /**
         * 关联优惠劵列表
         */
        private List<FullReductionCoupon> fullReductionCoupons;

        /**
         * 关联赠品列表
         */
        private List<FullReductionGitGoods> fullReductionGitGoods;

    }

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = false)
    public static class PreferentialContent {
        /**
         * 优惠内容类型选择
         */
        private List<ContentTypeChoiceEnum> contentTypeChoices;

        /**
         * 金额
         */
        private Amount amount;

        /**
         * 礼品
         */
        private Gift gift;

        /**
         * 计算福利
         *
         * @param params
         * @return : com.afiona.center.client.promotion.model.PromotionBenefits
         */
        public Benefit calcBenefits(BenefitParams params, PreferentialType preferentialType){
            BigDecimal preferentialAmount = new BigDecimal(0);
            int score = 0;
            List<Long> couponTemplateIdList = Lists.newArrayList();
            List<Long> giftSkuIds = Lists.newArrayList();
            boolean freeShipping = false;
            int discountTimes = 0;
            int times = params.getTimes();
            while(times-- > 0){
                if(contentTypeChoices!=null&&contentTypeChoices.contains(ContentTypeChoiceEnum.AMOUNT)){
                    if(amount.getAmountChoice() == AmountChoiceEnum.AMOUNT){
                        preferentialAmount= preferentialAmount.add(amount.getReduceAmount());
                    }else if(amount.getAmountChoice() == AmountChoiceEnum.DISCOUNT && discountTimes++ < 1){
                        preferentialAmount= preferentialAmount.add(params.getGoodsTotalPrice().multiply(amount.getDiscount().min(BigDecimal.ONE)));
                    }
                }
                if(contentTypeChoices!=null&&contentTypeChoices.contains(ContentTypeChoiceEnum.GIFT)){
                    if(gift.getGiftChoice() == GiftChoiceEnum.SCORE){
                        score += gift.getScore();
                    }else if(gift.getGiftChoice() == GiftChoiceEnum.COUPON){
                        List<Long> couponTemplateIds = gift.getFullReductionCoupons().stream()
                                .map(FullReductionCoupon::getCouponTemplateId).collect(Collectors.toList());
                        couponTemplateIdList.addAll(couponTemplateIds);
                    }else if(gift.getGiftChoice() == GiftChoiceEnum.GOODS){
                        List<Long> skuIds = gift.getFullReductionGitGoods().stream().map(FullReductionGitGoods::getSkuId)
                                .collect(Collectors.toList());
                        giftSkuIds.addAll(skuIds);
                    }
                }
                if(contentTypeChoices!=null&&contentTypeChoices.contains(ContentTypeChoiceEnum.FREE_SHIPPING)){
                    freeShipping = true;
                }
            }
            //封顶处理
            if(preferentialType.equals(PreferentialType.LOOP)&&amount.getLimit()!=null&&preferentialAmount.compareTo(amount.getLimit())>0){
                preferentialAmount=amount.getLimit();
            }

            return new Benefit()
                    .setPreferentialAmount(preferentialAmount)
                    .setScore(score)
                    .setCouponTemplateIdList(couponTemplateIdList)
                    .setGiftSkuIds(giftSkuIds)
                    .setFreeShipping(freeShipping);
        }
    }

    @Data
    @AllArgsConstructor
    public static class BenefitParams{
        /**
         * 总金额
         */
        private BigDecimal goodsTotalPrice;

        /**
         * 满减次数
         */
        private int times;
    }
}
