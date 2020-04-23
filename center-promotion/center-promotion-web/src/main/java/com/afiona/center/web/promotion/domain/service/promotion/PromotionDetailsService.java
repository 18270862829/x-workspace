package com.afiona.center.web.promotion.domain.service.promotion;

import com.afiona.center.client.promotion.constants.PromotionType;
import com.afiona.center.client.promotion.domain.aggregate.PromotionAggregate;
import com.afiona.center.client.promotion.domain.model.Promotion;
import com.afiona.center.client.promotion.model.PromotionGroup;
import com.afiona.center.client.promotion.model.PromotionSuitGoods;
import com.afiona.center.client.promotion.model.put.PromotionEnabledSettingPut;
import com.afiona.center.client.promotion.model.query.PromotionSuitGoodsQuery;
import com.afiona.center.client.promotion.model.query.SuitPromotionQuery;
import com.afiona.common.pojo.JsonResult;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 活动解析器
 *
 * @author LiJinXing
 * @date 2020/2/23
 */
public interface PromotionDetailsService {

    /**
     * 获取活动组信息
     *
     * @author LiJinXing
     * @param skuIds skuIds
     * @return 活动组
     */
    List<PromotionGroup> listGroupBySkuIds(Set<Long> skuIds);

    /**
     * 获取活动组信息
     *
     * @author LiJinXing
     * @param skuIds skuIds
     * @param promotionTypes 活动类型组
     * @return 活动组
     */
    List<PromotionGroup> listGroupBySkuIds(Set<Long> skuIds, List<PromotionType> promotionTypes);

    /**
     * 获取活动组信息
     *
     * @author LiJinXing
     * @param skuIds skuIds
     * @param promotionTypes 活动类型组
     * @return 活动组
     */
    Map<Long,List<Promotion>> listGroupBySkuIdsMap(Set<Long> skuIds, List<PromotionType> promotionTypes);
    /**
     * skuId列表，获取适用商品的 活动id
     *
     * @param skuIds 商品列表
     * @return 活动列表
     */
    Set<Long> listSuitGoodsBySkuIds(Set<Long> skuIds);

    /**
     * skuId对应活动id
     *
     * @param skuIds 商品列表
     * @return 活动列表
     */
    Map<Long,Set<Long>> listSuitGoodsBySkuIdsMap(Set<Long> skuIds);

    /**
     * skuId列表，获取套装活动的 活动id映射
     *
     * @param skuIds 商品列表
     * @return 活动列表
     */
    Map<Long,Set<Long>> listSuitGroupBySkuIdsMap(Set<Long> skuIds);
    /**
     * skuId列表，获取适用一口价活动的 活动id
     *
     * @param skuIds 商品列表
     * @return 活动列表
     */
    Set<Long> listFixedPriceBySkuIds(Set<Long> skuIds);

    /**
     * skuId列表，获取适用一口价活动的 活动id映射
     *
     * @param skuIds 商品列表
     * @return 活动列表
     */
    Map<Long,Set<Long>> listFixedPriceBySkuIdsMap(Set<Long> skuIds);

    /**
     * skuId列表，获取适用限时折扣的 活动id
     *
     * @param skuIds 商品列表
     * @return 活动列表
     */
    Map<Long,Set<Long>> listSpecialDiscountBySkuIdsMap(Set<Long> skuIds);
    /**
     * 获取活动详情
     *
     * @author LiJinXing
     * @param promotionId 活动id
     * @return 活动详情
     */
    PromotionAggregate details(long promotionId);

    /**
     * 促销活动 启用和停用
     *
     * @param put 启用或停用选择
     * @return JsonResult
     */
    JsonResult enabledSetting(PromotionEnabledSettingPut put);

    Map<Long, List<Promotion>> listSinglePromotionBySkuIds(List<Long> skuIds);


}
