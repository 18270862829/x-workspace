package com.afiona.center.web.promotion.domain.service.promotion.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import com.afiona.center.client.promotion.constants.PromotionType;
import com.afiona.center.client.promotion.constants.choice.OperatingTypeEnum;
import com.afiona.center.client.promotion.constants.choice.SuitChoiceEnum;
import com.afiona.center.client.promotion.domain.aggregate.PromotionAggregate;
import com.afiona.center.client.promotion.domain.model.Promotion;
import com.afiona.center.client.promotion.model.PromotionGroup;
import com.afiona.center.client.promotion.model.put.PromotionEnabledSettingPut;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.center.client.promotion.util.MergeUtils;
import com.afiona.center.web.promotion.domain.repo.*;
import com.afiona.center.web.promotion.domain.service.promotion.PromotionDetailsService;
import com.afiona.center.web.promotion.domain.service.verify.PromotionVerify;
import com.afiona.center.web.promotion.infrastructure.dao.*;
import com.afiona.center.web.promotion.infrastructure.model.*;
import com.afiona.center.client.promotion.util.OperatingVerifyUtils;
import com.afiona.center.client.promotion.util.PromotionUtils;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.afiona.center.client.promotion.util.GenericHelper.*;

/**
 * 活动详情
 *
 * @author LiJinXing
 * @date 2020/2/23
 */
@Service
public class PromotionDetailsServiceImpl implements PromotionDetailsService {

    @Resource
    private SuitGoodsDAO suitGoodsDAO;

    @Resource
    private SuitRuleDAO suitRuleDAO;

    @Resource
    private SuitFixedSuitGoodsDAO suitFixedSuitGoodsDAO;

    @Resource
    private FixedPriceGoodsDAO fixedPriceGoodsDAO;

    @Resource
    private SpecialDiscountGoodsDAO specialDiscountGoodsDAO;

    @Resource
    private PromotionDAO promotionDAO;

    @Resource
    private AddMoneyToBuyRepository addMoneyToBuyRepository;

    @Resource
    private CouponTemplateRepository couponTemplateRepository;

    @Resource
    private FixedPriceRepository fixedPriceRepository;

    @Resource
    private FullReductionRepository fullReductionRepository;

    @Resource
    private SpecialDiscountRepository specialDiscountRepository;

    @Resource
    private SuitGroupRepository suitGroupRepository;

    @Resource
    private PromotionVerify promotionVerify;

    @Override
    public List<PromotionGroup> listGroupBySkuIds(Set<Long> skuIds) {
        return listGroupBySkuIds(skuIds,null);
    }

    @Override
    public List<PromotionGroup> listGroupBySkuIds(Set<Long> skuIds,List<PromotionType> promotionTypes) {
        Map<Long, List<Promotion>> map = listGroupBySkuIdsMap(skuIds, promotionTypes);
        List<PromotionGroup> promotionGroups =new ArrayList<>();
        Set<Map.Entry<Long, List<Promotion>>> entrySet = map.entrySet();
        for (Map.Entry<Long, List<Promotion>> entry : entrySet) {
            Long skuId = entry.getKey();
            List<Promotion> promotionList = entry.getValue();
            promotionGroups.add(new PromotionGroup().setSkuId(skuId).setPromotions(promotionList));
        }
        return promotionGroups;
    }

    @Override
    public Map<Long, List<Promotion>> listGroupBySkuIdsMap(Set<Long> skuIds, List<PromotionType> promotionTypes) {
        Map<Long, Set<Long>> map = skuPromotionIdMap(skuIds, promotionTypes);
        if(CollectionUtil.isEmpty(map)){
            return null;
        }
        List<Promotion> validPromotion = getValidPromotion(map, promotionTypes);
        if(CollectionUtil.isEmpty(validPromotion)){
            return null;
        }
        Map<Long, Promotion> promotionMap = PromotionUtils.mapBySkuIds(validPromotion);
        Set<Map.Entry<Long, Set<Long>>> entrySet = map.entrySet();
        Map<Long,List<Promotion>> resultMap =new HashMap<>();
        for (Map.Entry<Long, Set<Long>> entry : entrySet) {
            Long skuId = entry.getKey();
            Set<Long> ids = entry.getValue();
            List<Promotion> promotionList =new ArrayList<>();
            for (Long id : ids) {
                promotionList.add(promotionMap.get(id));
            }
            resultMap.put(skuId,promotionList);
        }
        return resultMap;
    }

    @Override
    public Map<Long, List<Promotion>> listSinglePromotionBySkuIds(List<Long> skuIds) {
        if(CollectionUtil.isEmpty(skuIds)){
            return null;
        }
        //限时折扣
        return listGroupBySkuIdsMap(Sets.newHashSet(skuIds),Lists.newArrayList(PromotionType.SPECIAL_DISCOUNT));
    }

    @Override
    public Set<Long> listSuitGoodsBySkuIds(Set<Long> skuIds){
        //适用skuIds商品的列表
        Set<Long> promotionIds=new HashSet<>();
        Map<Long, Set<Long>> map = listSuitGoodsBySkuIdsMap(skuIds);
        if(map==null||map.isEmpty()){
            return promotionIds;
        }
        Collection<Set<Long>> values = listSuitGoodsBySkuIdsMap(skuIds).values();
        values.forEach(promotionIds::addAll);
        return promotionIds;
    }

    @Override
    public Map<Long, Set<Long>> listSuitGoodsBySkuIdsMap(Set<Long> skuIds) {
        //适用商品
        List<SuitGoodsDO> suitGoodsDOList = suitGoodsDAO.list(new LambdaQueryWrapper<SuitGoodsDO>().in(SuitGoodsDO::getSkuId, skuIds));
        Map<Long, Set<Long>> suitGoodsMap = toMap(suitGoodsDOList, SuitGoodsDO::getSkuId, SuitGoodsDO::getPromotionId);

        //适用规则
        List<SuitRuleDO> suitRuleDOList = suitRuleDAO.list();
        List<SuitRuleDO> suitAllList = suitRuleDOList.stream().filter(rule -> rule.getSuitGoodsChoice() != null &&
                rule.getSuitGoodsChoice() == SuitChoiceEnum.ALL).collect(Collectors.toList());
        List<Long> suitAllPromotionIds = suitAllList.stream().map(SuitRuleDO::getPromotionId).collect(Collectors.toList());;
        Map<Long, SuitRuleDO> suitRuleDOMap = suitRuleDOList.stream()
                .collect(Collectors.toMap(SuitRuleDO::getPromotionId, Function.identity()));

        Map<Long, Set<Long>> map = Maps.newHashMap();
        for(long skuId : skuIds){
            Set<Long> promotionIds = suitGoodsMap.get(skuId);
            if(CollectionUtil.isEmpty(promotionIds)){
                continue;
            }
            Set<Long> effectivePromotionIds = Sets.newHashSet();
            for(long promotionId : promotionIds){
                SuitRuleDO rule = suitRuleDOMap.get(promotionId);
                if(rule == null || rule.getSuitGoodsChoice() == null){
                    continue;
                }
                SuitChoiceEnum choice = rule.getSuitGoodsChoice();
                if(choice == null || !choice.isEffective()){
                    continue;
                }
                effectivePromotionIds.add(promotionId);
            }
            effectivePromotionIds.addAll(suitAllPromotionIds);
            if(CollectionUtil.isEmpty(effectivePromotionIds)){
                continue;
            }
            map.put(skuId, effectivePromotionIds);
        }
        return map;
    }

    @Override
    public Map<Long, Set<Long>> listSuitGroupBySkuIdsMap(Set<Long> skuIds) {
        Map<Long,Set<Long>> map =new HashMap<>(16);
        List<SuitFixedSuitGoodsDO> suitFixedSuitGoodsDOList = suitFixedSuitGoodsDAO.list(new LambdaQueryWrapper<SuitFixedSuitGoodsDO>().in(SuitFixedSuitGoodsDO::getSkuId, skuIds));
        if(CollectionUtil.isNotEmpty(suitFixedSuitGoodsDOList)){
           return map;
        }
        for (SuitFixedSuitGoodsDO suitFixedSuitGoodsDO : suitFixedSuitGoodsDOList) {
            Long skuId = suitFixedSuitGoodsDO.getSkuId();
            Long promotionId = suitFixedSuitGoodsDO.getPromotionId();
            if (map.get(skuId) == null) {
                map.put(skuId, Sets.newHashSet(promotionId));
            } else {
                map.get(skuId).add(promotionId);
            }
        }
        return map;
    }

    @Override
    public Set<Long> listFixedPriceBySkuIds(Set<Long> skuIds) {
        Set<Long> promotionIds=new HashSet<>();
        //一口价
        Map<Long, Set<Long>> map = listFixedPriceBySkuIdsMap(skuIds);
        if(MapUtil.isNotEmpty(map)){
            map.values().forEach(promotionIds::addAll);
        }
        return promotionIds;
    }

    @Override
    public Map<Long, Set<Long>> listFixedPriceBySkuIdsMap(Set<Long> skuIds) {
        List<FixedPriceGoodsDO> allGoodsDOS = fixedPriceGoodsDAO.list(new LambdaQueryWrapper<FixedPriceGoodsDO>()
                .in(FixedPriceGoodsDO::getSkuId, skuIds));
        if(CollectionUtil.isEmpty(allGoodsDOS)){
            return Maps.newHashMap();
        }
        return toMap(allGoodsDOS, FixedPriceGoodsDO::getSkuId, FixedPriceGoodsDO::getPromotionId);
    }

    @Override
    public Map<Long, Set<Long>> listSpecialDiscountBySkuIdsMap(Set<Long> skuIds) {
        Map<Long, Set<Long>> map =new HashMap<>(16);
        List<SpecialDiscountGoodsDO> specialDiscountGoodsDOList = specialDiscountGoodsDAO.list(new LambdaQueryWrapper<SpecialDiscountGoodsDO>().in(SpecialDiscountGoodsDO::getSkuId, skuIds));
        for (SpecialDiscountGoodsDO specialDiscountGoodsDO : specialDiscountGoodsDOList) {
            Long promotionId = specialDiscountGoodsDO.getPromotionId();
            Long skuId = specialDiscountGoodsDO.getSkuId();
            if(map.get(skuId)==null){
                map.put(skuId,Sets.newHashSet(promotionId));
            }else {
                map.get(skuId).add(promotionId);
            }
        }
        return map;
    }


    @Override
    public PromotionAggregate details(long promotionId) {
        PromotionDO promotionDO = promotionDAO.getById(promotionId);
        if(promotionDO.getType().equals(PromotionType.ADD_MONEY_TO_BUY_GOODS)){
            return addMoneyToBuyRepository.get(promotionId);
        }
        if(promotionDO.getType().equals(PromotionType.COUPON)){
            return couponTemplateRepository.get(promotionId);
        }
        if(promotionDO.getType().equals(PromotionType.FIXED_PRICE)){
            return fixedPriceRepository.get(promotionId);
        }
        if(promotionDO.getType().equals(PromotionType.FULL_REDUCTION)){
            return fullReductionRepository.get(promotionId);
        }
        if(promotionDO.getType().equals(PromotionType.SPECIAL_DISCOUNT)){
            return specialDiscountRepository.get(promotionId);
        }
        if(promotionDO.getType().equals(PromotionType.SUIT_GROUP)){
            return suitGroupRepository.get(promotionId);
        }
        return null;
    }

    @Override
    public JsonResult enabledSetting(PromotionEnabledSettingPut put) {
        Boolean enabled = put.getEnabled();
        Long promotionId = put.getPromotionId();
        OperatingTypeEnum operatingType=enabled? OperatingTypeEnum.OPERATING_START:OperatingTypeEnum.OPERATING_STOP;
        PromotionDO promotionDO = promotionDAO.getById(promotionId);
        if(promotionDO==null){
            return JsonResult.create(4000,"参数有误，没有查询到对应活动");
        }
        if(OperatingVerifyUtils.enabledSetting(promotionDO.getEnabled(),promotionDO.getStatus(),operatingType)){
            promotionDAO.updateById(promotionDO.setEnabled(enabled));
            return JsonResult.create();
        }
        return JsonResult.create(4000,"活动已经启用或停用，不要重复启用或停用");
    }

    private Map<Long,Set<Long>> skuPromotionIdMap(Set<Long> skuIds,List<PromotionType> promotionTypes){
        List<Map<Long, Set<Long>>> mapList =new ArrayList<>();
        if(CollectionUtil.isEmpty(promotionTypes)||promotionTypes.contains(PromotionType.FIXED_PRICE)){
            mapList.add(listFixedPriceBySkuIdsMap(skuIds));
        }
        if(CollectionUtil.isEmpty(promotionTypes)||promotionTypes.contains(PromotionType.SUIT_GROUP)){
            mapList.add(listSuitGroupBySkuIdsMap(skuIds));
        }
        if(CollectionUtil.isEmpty(promotionTypes)||promotionTypes.contains(PromotionType.SPECIAL_DISCOUNT)){
            mapList.add(listSpecialDiscountBySkuIdsMap(skuIds));
        }
        boolean flg =true;
        if(CollectionUtil.isEmpty(promotionTypes)){
            mapList.add(listSuitGoodsBySkuIdsMap(skuIds));
            flg=false;
        }
        promotionTypes.removeAll(Lists.newArrayList(PromotionType.FIXED_PRICE,PromotionType.SUIT_GROUP,PromotionType.SPECIAL_DISCOUNT));
        if (flg&&CollectionUtil.isNotEmpty(promotionTypes)){
            mapList.add(listSuitGoodsBySkuIdsMap(skuIds));
        }
        //合并map
        return MergeUtils.mergeMap(mapList.toArray(new Map[0]));
    }

    private List<Promotion> getValidPromotion(Map<Long, Set<Long>> map,List<PromotionType> promotionTypes){
        Set<Long> promotionIds =new HashSet<>();
        map.values().forEach(promotionIds::addAll);
        if(CollectionUtil.isEmpty(promotionIds)){
            return null;
        }
        List<PromotionDO> promotionDOS = promotionDAO.listAll(new PromotionQuery().setPromotionIds(Lists.newArrayList(promotionIds)).setPromotionTypes(promotionTypes));
        //过滤无效的促销
        return promotionVerify.validFilter(CloneUtil.cloneList(promotionDOS,Promotion.class));
    }


}