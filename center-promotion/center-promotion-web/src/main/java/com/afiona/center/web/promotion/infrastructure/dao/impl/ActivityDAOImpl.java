package com.afiona.center.web.promotion.infrastructure.dao.impl;

import cn.hutool.core.util.ObjectUtil;
import com.afiona.center.client.promotion.model.query.ActivityQuery;
import com.afiona.center.client.promotion.util.RangeUtil;
import com.afiona.center.web.promotion.infrastructure.dao.ActivityDAO;
import com.afiona.center.web.promotion.infrastructure.mapper.ActivityMapper;
import com.afiona.center.web.promotion.infrastructure.model.ActivityDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepexi.util.CollectionUtil;
import com.deepexi.util.pageHelper.PageBean;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 活动dao 实现
 *
 * @author LiJinXing
 * @date 2020/3/4
 */
@Service
public class ActivityDAOImpl extends ServiceImpl<ActivityMapper, ActivityDO> implements ActivityDAO {

    @Override
    public PageBean<ActivityDO> listPage(ActivityQuery query) {

        PageHelper.startPage(query.getPage(),query.getSize());
        List<ActivityDO> page = getActivityDOS(query);

        return new PageBean<>(page);
    }

    @Override
    public List<ActivityDO> listAll(ActivityQuery query) {
        return getActivityDOS(query);
    }

    private List<ActivityDO> getActivityDOS(ActivityQuery query) {
        if(query.getTimeRange()==null){
            query.setTimeRange(new RangeUtil.TimeRange());
        }
        return this.list(new LambdaQueryWrapper<ActivityDO>()
                    .eq(ObjectUtil.isNotNull(query.getName()), ActivityDO::getName, query.getName())
                    .eq(ObjectUtil.isNotNull(query.getPurpose()), ActivityDO::getActivityPurpose, query.getPurpose())
                    .in(CollectionUtil.isNotEmpty(query.getStatus()), ActivityDO::getStatus, query.getStatus())
                    .gt(ObjectUtil.isNotNull(query.getTimeRange().getStartTime()), ActivityDO::getStartTime, query.getTimeRange().getStartTime())
                    .lt(ObjectUtil.isNotNull(query.getTimeRange().getEndTime()), ActivityDO::getEndTime, query.getTimeRange().getEndTime())
                    .in(CollectionUtil.isNotEmpty(query.getActivityIds()), ActivityDO::getId, query.getActivityIds())
                    .orderByDesc(ActivityDO::getUpdatedTime));
    }
}
