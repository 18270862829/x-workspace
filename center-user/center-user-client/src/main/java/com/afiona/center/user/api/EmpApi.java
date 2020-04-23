package com.afiona.center.user.api;

import com.afiona.center.user.domain.aggregate.EmpAggregate;
import com.afiona.center.user.domain.model.Emp;
import com.afiona.center.user.model.query.EmpQuery;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 员工管理
 *
 * @author dengweiyi
 * @date 2020-03-09
 */

@RestController
@RequestMapping("/user/emp")
@Api(value = "员工管理", tags = "员工管理")
public interface EmpApi {

    /**
     * 查询当前登录员工
     *
     * @param
     * @return : com.afiona.common.pojo.JsonResult<com.afiona.center.user.domain.model.Emp>
     */
    @PostMapping("/me")
    JsonResult<Emp> me();

    /**
     * 分页查询
     *
     * @param
     * @return : com.afiona.common.pojo.JsonResult<com.afiona.center.user.domain.model.EmpAggregate>
     */
    @GetMapping("/listPage")
    JsonResult<PageBean<EmpAggregate>> listPage(EmpQuery query);

    /**
     * 列表查询
     *
     * @param empIds
     * @return : com.afiona.common.pojo.JsonResult<java.util.List<com.afiona.center.user.domain.model.EmpAggregate>>
     */
    @PostMapping("/listByIds")
    JsonResult<List<EmpAggregate>> listByIds(@RequestBody List<Long> empIds);

    /**
     * 查询
     *
     * @param empId
     * @return : com.afiona.common.pojo.JsonResult<com.afiona.center.user.domain.model.EmpAggregate>
     */
    @PostMapping("/getById")
    JsonResult<EmpAggregate> getById(@RequestParam("empId") long empId);

    /**
     * 存储
     *
     * @param empAggregate
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @PostMapping("/store")
    JsonResult<Boolean> store(@RequestBody EmpAggregate empAggregate);

    /**
     * 删除
     *
     * @param empId
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    @PostMapping("/delete")
    JsonResult<Boolean> delete(@RequestParam("empId") long empId);

    /**
     * 批量删除
     *
     * @param empIds 员工ids
     * @return JsonResult 是否成功
     */
    @DeleteMapping("/deleteBatch")
    JsonResult<Boolean> deleteBatch(@RequestBody List<Long> empIds);

    /**
     * 更新
     *
     * @param empAggregate 员工聚合信息
     * @return JsonResult 是否成功
     */
    @PutMapping("/update")
    JsonResult<Boolean> update(@RequestBody EmpAggregate empAggregate);

}
