package com.afiona.center.web.promotion.infrastructure.dao.impl;

import cn.hutool.core.util.ObjectUtil;
import com.afiona.center.client.promotion.model.query.GetCouponCentreQuery;
import com.afiona.center.web.promotion.infrastructure.dao.GetCouponCentreDAO;
import com.afiona.center.web.promotion.infrastructure.mapper.GetCouponCentreMapper;
import com.afiona.center.web.promotion.infrastructure.model.GetCouponCentreDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepexi.util.pageHelper.PageBean;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 领券中心dao实现
 *
 * @author LiJinXing
 * @date 2020/3/5
 */
@Component
public class GetCouponCentreDAOImpl extends ServiceImpl<GetCouponCentreMapper, GetCouponCentreDO> implements GetCouponCentreDAO {
    @Override
    public PageBean<GetCouponCentreDO> listPage(GetCouponCentreQuery query) {
        PageHelper.startPage(query.getPage(),query.getSize());
        List<GetCouponCentreDO> page = this.list(new LambdaQueryWrapper<GetCouponCentreDO>()
                .eq(ObjectUtil.isNotNull(query.getMemberId()), GetCouponCentreDO::getMemberId, query.getMemberId()).orderByDesc(GetCouponCentreDO::getCreatedTime).orderByDesc(GetCouponCentreDO::getId));
        PageBean<GetCouponCentreDO> pageBean = new PageBean<>(page);
        return pageBean;
    }
}
