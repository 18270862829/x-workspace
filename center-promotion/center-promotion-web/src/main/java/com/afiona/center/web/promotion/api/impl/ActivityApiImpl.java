package com.afiona.center.web.promotion.api.impl;

import com.afiona.center.client.promotion.api.ActivityApi;
import com.afiona.center.client.promotion.domain.model.Activity;
import com.afiona.center.client.promotion.model.put.ActivityEnabledSettingPut;
import com.afiona.center.client.promotion.model.query.ActivityQuery;
import com.afiona.center.web.promotion.domain.repo.ActivityRepository;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

/**
 * 活动api 实现
 *
 * @author LiJinXing
 * @date 2020/3/4
 */
@RestController
public class ActivityApiImpl implements ActivityApi {

    @Resource
    private ActivityRepository activityRepository;

    @Override
    public JsonResult<Activity> get(Long activityId) {
        return JsonResult.create(activityRepository.get(activityId));
    }

    @Override
    public JsonResult<PageBean<Activity>> listPage(@RequestBody ActivityQuery query) {
        return JsonResult.create(activityRepository.listPage(query));
    }

    @Override
    public JsonResult<List<Activity>> listAll(@RequestBody ActivityQuery query) {
        return JsonResult.create(activityRepository.listAll(query));
    }

    @Override
    public JsonResult store(@RequestBody Activity activity) {
        return activityRepository.store(activity);
    }

    @Override
    public JsonResult remove(@RequestBody Activity activity) {
        return activityRepository.remove(activity);
    }

    @Override
    public JsonResult removeBatch(@RequestBody List<Activity> list) {
        return activityRepository.removeBatch(list);
    }

    @Override
    public JsonResult enabledSetting(@RequestBody ActivityEnabledSettingPut put) {
        return activityRepository.enabledSetting(put);
    }
}
