package com.afiona.center.client.promotion.api;

import com.afiona.center.client.promotion.domain.model.Activity;
import com.afiona.center.client.promotion.model.put.ActivityEnabledSettingPut;
import com.afiona.center.client.promotion.model.query.ActivityQuery;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 活动api
 *
 * @author LiJinXing
 * @date 2020/3/4
 */
@RequestMapping("/center/v1/activity")
@Api(value = "活动", tags = "活动")
public interface ActivityApi {

    /**
     * 查询
     *
     * @param activityId 活动id
     * @return 活动详情
     */
    @GetMapping("/get/activityId")
    @ApiOperation("查询")
    JsonResult<Activity> get(@RequestParam("activityId") Long activityId);

    /**
     * 列表查询
     *
     * @param query 查询条件
     * @return 活动列表
     */
    @PostMapping("/listPage/query")
    @ApiOperation("列表查询")
    JsonResult<PageBean<Activity>> listPage(@RequestBody ActivityQuery query);

    /**
     * 批量查询
     *
     * @param query 查询条件
     * @return 活动列表
     */
    @PostMapping("/listAll/query")
    @ApiOperation("批量查询")
    JsonResult<List<Activity>> listAll(@RequestBody ActivityQuery query);

    /**
     * 保存
     *
     * @param activity 活动信息
     * @return com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @PostMapping("/store/activity")
    @ApiOperation("保存")
    JsonResult store(@RequestBody Activity activity);

    /**
     * 删除
     *
     * @param activity 活动信息，主键删除
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @DeleteMapping("/remove/activity")
    @ApiOperation("删除")
    JsonResult remove(@RequestBody Activity activity);

    /**
     * 批量删除
     *
     * @param list 活动列表，主键删除
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @DeleteMapping("/removeBatch/list")
    @ApiOperation("批量删除")
    JsonResult removeBatch(@RequestBody List<Activity> list);

    /**
     * 活动启用或停用
     *
     * @param put 启用或停用修改条件
     * @return JsonResult
     */
    @PostMapping("/enabledSetting/activityId/enabled")
    @ApiOperation("活动启用或停用")
    JsonResult enabledSetting(@RequestBody ActivityEnabledSettingPut put);
}