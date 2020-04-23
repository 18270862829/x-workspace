package com.afiona.center.web.promotion.infrastructure.repo.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.client.promotion.constants.choice.OperatingTypeEnum;
import com.afiona.center.client.promotion.domain.model.Activity;
import com.afiona.center.client.promotion.model.put.ActivityEnabledSettingPut;
import com.afiona.center.client.promotion.model.query.ActivityQuery;
import com.afiona.center.client.promotion.util.Encoder;
import com.afiona.center.client.promotion.util.OperatingVerifyUtils;
import com.afiona.center.web.promotion.domain.repo.ActivityRepository;
import com.afiona.center.web.promotion.infrastructure.dao.ActivityDAO;
import com.afiona.center.web.promotion.infrastructure.dao.PromotionDAO;
import com.afiona.center.web.promotion.infrastructure.model.ActivityDO;
import com.afiona.center.client.promotion.util.PageBeanUtils;
import com.afiona.center.web.promotion.infrastructure.model.PromotionDO;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.BizException;
import com.afiona.common.util.CloneUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.deepexi.util.pageHelper.PageBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 活动repo实现
 *
 * @author LiJinXing
 * @date 2020/3/4
 */
@Repository
@Transactional(rollbackFor =Throwable.class)
public class ActivityRepositoryImpl implements ActivityRepository {

    @Resource
    private ActivityDAO activityDAO;

    @Resource
    private PromotionDAO promotionDAO;

    @Override
    public Activity get(Long activityId) {
        return CloneUtil.clone(activityDAO.getById(activityId),Activity.class);
    }

    @Override
    public PageBean<Activity> listPage(ActivityQuery query) {
        PageBean<ActivityDO> pageBean = activityDAO.listPage(query);
        List<Activity> activities = CloneUtil.cloneList(pageBean.getContent(), Activity.class);
        setPromotionNum(activities);
        return PageBeanUtils.pageBeanCopy(activities,pageBean);
    }

    @Override
    public List<Activity> listAll(ActivityQuery query) {
        List<ActivityDO> activityDOList = activityDAO.listAll(query);
        List<Activity> activities = CloneUtil.cloneList(activityDOList, Activity.class);
        setPromotionNum(activities);
        return activities;
    }

    @Override
    public JsonResult store(Activity activity) {
        timeVerify(activity);
        activity.setEncoding(Encoder.coding());
        activityDAO.saveOrUpdate(CloneUtil.clone(activity,ActivityDO.class));
        return JsonResult.create();
    }

    @Override
    public JsonResult remove(Activity activity) {
         activityDAO.removeById(activity.getId());
         return JsonResult.create();
    }

    @Override
    public JsonResult removeBatch(List<Activity> list) {
         activityDAO.removeByIds(list.stream().map(Activity::getId).collect(Collectors.toSet()));
         return JsonResult.create();
    }

    private void timeVerify(Activity activity) {
        Date startTime = activity.getStartTime();
        Date endTime = activity.getEndTime();
        if(startTime.after(endTime)){
            throw new BizException("活动的开始时间不能在结束时间之后");
        }
    }

    @Override
    public JsonResult enabledSetting(ActivityEnabledSettingPut put) {
        Long activityId = put.getActivityId();
        Boolean enabled = put.getEnabled();
        ActivityDO activityDO = activityDAO.getById(activityId);
        if(activityDO==null){
            return JsonResult.create(4000,"参数有误，未能查询到对应的活动信息");
        }

        OperatingTypeEnum operatingType=enabled? OperatingTypeEnum.OPERATING_START:OperatingTypeEnum.OPERATING_STOP;
        if(OperatingVerifyUtils.enabledSetting(activityDO.getEnabled(),activityDO.getStatus(),operatingType)){
            List<PromotionDO> promotionDOList = promotionDAO.list(new LambdaQueryWrapper<PromotionDO>().eq(PromotionDO::getActivityId, activityId));
            boolean flg=true;
            if(!promotionDOList.isEmpty()){
                for (PromotionDO promotionDO : promotionDOList) {
                    if(promotionDO.getEnabled()){
                        flg=false;
                        break;
                    }
                }
            }
            if(flg){
                activityDAO.updateById(activityDO.setEnabled(enabled));
                return JsonResult.create();
            }
        }
        return JsonResult.create(4000,"活动已经启用或停用，不要重复操作");
    }

     private void setPromotionNum(List<Activity> activityList){
        if(CollectionUtil.isEmpty(activityList)){
            return;
        }
         List<Long> activityIds = activityList.stream().map(Activity::getId).collect(Collectors.toList());
        if(CollectionUtil.isEmpty(activityIds)){
            return;
        }
         List<PromotionDO> dos = promotionDAO.list(new LambdaQueryWrapper<PromotionDO>().in(PromotionDO::getActivityId, activityIds));
         Map<Long, List<PromotionDO>> map = dos.stream().collect(Collectors.groupingBy(PromotionDO::getActivityId));
         for (Activity activity : activityList) {
             List<PromotionDO> list = map.get(activity.getId());
             if(CollectionUtil.isEmpty(list)){
                 activity.setPromotionNum(0);
                 continue;
             }
             activity.setPromotionNum(list.size());
         }
     }

}
