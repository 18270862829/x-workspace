package com.afiona.center.web.promotion.domain.repo;

import com.afiona.center.client.promotion.domain.aggregate.PromotionAggregate;
import com.afiona.center.client.promotion.model.query.PromotionQuery;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;

import java.util.List;

/**
 * 基础促销活动repo
 *
 * @author dengweiyi
 * @date 2020-02-09
 */
public interface BasePromotionRepository <T extends PromotionAggregate>{

    /**
     * 查询
     *
     * @param promotionId
     * @return : T
     */
    T get(long promotionId);

    /**
     * 列表查询
     *
     * @param query
     * @return : java.util.List<T>
     */
    PageBean<T> list(PromotionQuery query);

    /**
     * 全列表查询
     *
     * @param query 查询条件
     * @return : java.util.List<T>
     */
    List<T> listAll(PromotionQuery query);

    /**
     * 保存
     *
     * @param t
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    JsonResult store(T t);

    /**
     * 删除
     *
     * @param id
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    JsonResult remove(Long id);

    /**
     * 批量删除
     *
     * @param ids
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    JsonResult removeBatch(List<Long> ids);
}
