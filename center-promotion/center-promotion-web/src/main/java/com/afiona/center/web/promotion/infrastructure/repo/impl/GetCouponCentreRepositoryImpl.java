package com.afiona.center.web.promotion.infrastructure.repo.impl;

import com.afiona.center.client.promotion.domain.model.promotionrule.coupon.GetCouponCentre;
import com.afiona.center.client.promotion.model.query.GetCouponCentreQuery;
import com.afiona.center.web.promotion.domain.repo.GetCouponCentreRepository;
import com.afiona.center.web.promotion.infrastructure.dao.GetCouponCentreDAO;
import com.afiona.center.web.promotion.infrastructure.model.GetCouponCentreDO;
import com.afiona.center.client.promotion.util.PageBeanUtils;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.CloneUtil;
import com.deepexi.util.pageHelper.PageBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 领券中心 repo
 *
 * @author LiJinXing
 * @date 2020/3/5
 */
@Repository
@Transactional(rollbackFor =Throwable.class)
public class GetCouponCentreRepositoryImpl implements GetCouponCentreRepository {

    @Resource
    private GetCouponCentreDAO getCouponCentreDAO;

    @Override
    public GetCouponCentre get(Long id) {
        return CloneUtil.clone(getCouponCentreDAO.getById(id),GetCouponCentre.class);
    }

    @Override
    public PageBean<GetCouponCentre> pageListByMemberId(GetCouponCentreQuery query) {
        PageBean<GetCouponCentreDO> pageBean = getCouponCentreDAO.listPage(query);
        List<GetCouponCentre> list = CloneUtil.cloneList(pageBean.getContent(), GetCouponCentre.class);
        return PageBeanUtils.pageBeanCopy(list,pageBean);
    }

    @Override
    public JsonResult store(GetCouponCentre getCouponCentre) {
        getCouponCentreDAO.saveOrUpdate(CloneUtil.clone(getCouponCentre, GetCouponCentreDO.class));
        return JsonResult.create();
    }

    @Override
    public JsonResult storeBatch(List<GetCouponCentre> list) {
        getCouponCentreDAO.saveOrUpdateBatch(CloneUtil.cloneList(list,GetCouponCentreDO.class));
        return JsonResult.create();
    }

    @Override
    public JsonResult removeBatch(List<Long> ids) {
        getCouponCentreDAO.removeByIds(ids);
        return JsonResult.create();
    }
}
