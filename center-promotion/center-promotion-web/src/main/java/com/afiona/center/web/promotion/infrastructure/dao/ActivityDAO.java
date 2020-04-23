package com.afiona.center.web.promotion.infrastructure.dao;

import com.afiona.center.client.promotion.model.query.ActivityQuery;
import com.afiona.center.web.promotion.infrastructure.model.ActivityDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deepexi.util.pageHelper.PageBean;

import java.util.List;

/**
 * 活动dao
 *
 * @author LiJinXing
 * @date 2020/3/4
 */
public interface ActivityDAO extends IService<ActivityDO> {

    PageBean<ActivityDO> listPage(ActivityQuery query);

    List<ActivityDO> listAll(ActivityQuery query);

}
