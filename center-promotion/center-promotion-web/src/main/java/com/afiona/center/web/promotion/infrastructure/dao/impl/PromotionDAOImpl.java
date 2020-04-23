package com.afiona.center.web.promotion.infrastructure.dao.impl;

import cn.hutool.core.util.ObjectUtil;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.center.client.promotion.model.query.SuitPromotionQuery;
import com.afiona.center.web.promotion.infrastructure.dao.PromotionDAO;
import com.afiona.center.web.promotion.infrastructure.mapper.PromotionMapper;
import com.afiona.center.web.promotion.infrastructure.model.PromotionDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepexi.util.CollectionUtil;
import com.deepexi.util.pageHelper.PageBean;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 促销活动dao实现
 *
 * @author dengweiyi
 * @date 2020-02-09
 */
@Component
public class PromotionDAOImpl extends ServiceImpl<PromotionMapper, PromotionDO> implements PromotionDAO {

    @Override
    public PageBean<PromotionDO> listPage(PromotionQuery query) {
        PageHelper.startPage(query.getPage().intValue(),query.getSize().intValue());
        List<PromotionDO> promotionPage = getPromotionDOS(query);
        return new PageBean<>(promotionPage);
    }

    @Override
    public List<PromotionDO> suitPromotionQuery(SuitPromotionQuery query) {
        List<PromotionDO> promotionDOList = this.list(new LambdaQueryWrapper<PromotionDO>()
                .in(CollectionUtil.isNotEmpty(query.getPromotionIds()), PromotionDO::getId, query.getPromotionIds())
                .in(CollectionUtil.isNotEmpty(query.getPromotionTypes()), PromotionDO::getType, query.getPromotionTypes()));
        return promotionDOList;
    }

    @Override
    public List<PromotionDO> listAll(PromotionQuery query) {
        List<PromotionDO> promotionPage = getPromotionDOS(query);
        return promotionPage;
    }

    private List<PromotionDO> getPromotionDOS(PromotionQuery query) {
        return this.list(new LambdaQueryWrapper<PromotionDO>()
                .in(CollectionUtil.isNotEmpty(query.getActivityIds()),PromotionDO::getActivityId,query.getActivityIds())
                .eq(ObjectUtil.isNotNull(query.getName()), PromotionDO::getName, query.getName())
                .in(CollectionUtil.isNotEmpty(query.getPromotionTypes()), PromotionDO::getType, query.getPromotionTypes())
                .eq(ObjectUtil.isNotNull(query.getPurpose()), PromotionDO::getPurpose, query.getPurpose())
                .eq(ObjectUtil.isNotNull(query.getEncoding()), PromotionDO::getEncoding, query.getEncoding())
                .eq(ObjectUtil.isNotNull(query.getStatus()), PromotionDO::getStatus, query.getStatus())
                .gt(ObjectUtil.isNotNull(query.getStartTime()), PromotionDO::getStartTime, query.getStartTime())
                .lt(ObjectUtil.isNotNull(query.getEndTime()), PromotionDO::getEndTime, query.getEndTime())
                .in(CollectionUtil.isNotEmpty(query.getPromotionIds()), PromotionDO::getId, query.getPromotionIds()).orderByDesc(PromotionDO::getCreatedTime)
                .orderByDesc(PromotionDO::getUpdatedTime));
    }
}
