package com.afiona.center.web.promotion.infrastructure.dao;

import java.util.List;

import com.afiona.center.client.promotion.domain.model.Promotion;
import com.afiona.center.client.promotion.model.query.SuitPromotionQuery;
import com.deepexi.util.pageHelper.PageBean;
import org.apache.ibatis.annotations.Param;

import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.center.web.promotion.infrastructure.model.PromotionDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author clb
 * @date 2020/01/07
 */
public interface PromotionDAO extends IService<PromotionDO> {
    /**
     * 分页查询
     *
     * @param query
     * @return : java.util.List<com.afiona.center.web.promotion.infrastructure.model.PromotionDO>
     */
    PageBean<PromotionDO> listPage(@Param("query") PromotionQuery query);

    List<PromotionDO> suitPromotionQuery(SuitPromotionQuery query);

    List<PromotionDO> listAll(PromotionQuery query);

}
