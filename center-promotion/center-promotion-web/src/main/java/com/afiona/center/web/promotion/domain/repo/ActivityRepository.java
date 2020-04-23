package com.afiona.center.web.promotion.domain.repo;

import com.afiona.center.client.promotion.domain.model.Activity;
import com.afiona.center.client.promotion.model.put.ActivityEnabledSettingPut;
import com.afiona.center.client.promotion.model.query.ActivityQuery;
import com.afiona.center.web.promotion.infrastructure.model.ActivityDO;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;

import java.util.List;

/**
 * 活动repo
 *
 * @author LiJinXing
 * @date 2020/3/4
 */

public interface ActivityRepository {

    /**
     * 查询
     */
    Activity get(Long activityId);

    /**
     * 列表查询
     * @return
     */
    PageBean<Activity> listPage(ActivityQuery query);

    /**
     * 批量查询
     */
    List<Activity> listAll(ActivityQuery query);

    /**
     * 保存
     */
    JsonResult store(Activity activity);

    /**
     * 删除
     */
    JsonResult remove(Activity activity);

    /**
     * 列表删除
     */
    JsonResult removeBatch(List<Activity> list);

    /**
     * 活动启动或停用
     */
    JsonResult enabledSetting(ActivityEnabledSettingPut put);
}
