package com.afiona.center.user.api.impl;

import com.afiona.center.user.api.EmpApi;
import com.afiona.center.user.domain.aggregate.EmpAggregate;
import com.afiona.center.user.domain.model.Emp;
import com.afiona.center.user.domain.repo.EmpRepository;
import com.afiona.center.user.model.query.EmpQuery;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 员工管理实现
 *
 * @author dengweiyi
 * @date 2020-03-09
 */
@RestController
public class EmpApiImpl implements EmpApi {
    @Resource
    private EmpRepository empRepository;

    @ApiOperation("查询当前登录员工")
    @Override
    public JsonResult<Emp> me() {
        return empRepository.me();
    }

    @ApiOperation("员工分页查询")
    @Override
    public JsonResult<PageBean<EmpAggregate>> listPage(@ApiParam(required = true, value = "员工条件查询信息") EmpQuery query) {
        return empRepository.listPage(query);
    }

    @ApiOperation("根据员工ids查询多个员工信息")
    @Override
    public JsonResult<List<EmpAggregate>> listByIds(@RequestBody @ApiParam(required = true, value = "员工ids") List<Long> empIds) {
        return empRepository.listByIds(empIds);
    }

    @ApiOperation("根据员工id查询单个员工信息")
    @Override
    public JsonResult<EmpAggregate> getById(@RequestParam("empId") @ApiParam(required = true, value = "员工id") long empId) {
        return empRepository.getById(empId);
    }

    @ApiOperation("新增单个员工")
    @Override
    public JsonResult<Boolean> store(@RequestBody @ApiParam(required = true, value = "员工基础信息") @Validated(Emp.Create.class) EmpAggregate empAggregate) {
        return empRepository.store(empAggregate);
    }

    @ApiOperation("删除单个员工")
    @Override
    public JsonResult<Boolean> delete(@RequestParam("empId") @ApiParam(required = true, value = "员工id") long empId) {
        return empRepository.delete(empId);
    }

    @ApiOperation("批量删除员工")
    @Override
    public JsonResult<Boolean> deleteBatch(@RequestBody @ApiParam(required = true, value = "员工ids") List<Long> empIds) {
        return empRepository.deleteBatch(empIds);
    }

    @ApiOperation("更新单个员工")
    @Override
    public JsonResult<Boolean> update(@RequestBody @ApiParam(required = true, value = "员工详细信息") EmpAggregate empAggregate) {
        return JsonResult.create(empRepository.update(empAggregate));
    }

}
