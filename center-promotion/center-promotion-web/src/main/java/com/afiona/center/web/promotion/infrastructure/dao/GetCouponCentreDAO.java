package com.afiona.center.web.promotion.infrastructure.dao;

import com.afiona.center.client.promotion.model.query.GetCouponCentreQuery;
import com.afiona.center.web.promotion.infrastructure.model.GetCouponCentreDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deepexi.util.pageHelper.PageBean;

/**
 * 领券中心dao
 *
 * @author LiJinXing
 * @date 2020/3/5
 */
public interface GetCouponCentreDAO extends IService<GetCouponCentreDO> {

    PageBean<GetCouponCentreDO> listPage(GetCouponCentreQuery query);
}
