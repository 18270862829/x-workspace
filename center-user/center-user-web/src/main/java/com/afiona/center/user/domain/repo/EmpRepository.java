package com.afiona.center.user.domain.repo;

import com.afiona.center.user.domain.aggregate.EmpAggregate;
import com.afiona.center.user.domain.model.Emp;
import com.afiona.center.user.model.query.EmpQuery;
import com.afiona.common.pojo.JsonResult;
import com.deepexi.util.pageHelper.PageBean;

import java.util.List;

/**
 * 员工repo
 *
 * @author dengweiyi
 * @date 2020-03-04
 */
public interface EmpRepository {
    /**
     * 查询当前登录员工
     *
     * @param
     * @return : com.afiona.common.pojo.JsonResult<com.afiona.center.user.domain.model.Emp>
     */
    JsonResult<Emp> me();

    /**
     * 分页查询
     *
     * @param
     * @return : com.afiona.common.pojo.JsonResult<com.afiona.center.user.domain.model.EmpAggregate>
     */
    JsonResult<PageBean<EmpAggregate>> listPage(EmpQuery query);

    /**
     * 列表查询
     *
     * @param empIds
     * @return : com.afiona.common.pojo.JsonResult<java.util.List<com.afiona.center.user.domain.model.EmpAggregate>>
     */
    JsonResult<List<EmpAggregate>> listByIds(List<Long> empIds);

    /**
     * 查询
     *
     * @param empId
     * @return : com.afiona.common.pojo.JsonResult<com.afiona.center.user.domain.model.EmpAggregate>
     */
    JsonResult<EmpAggregate> getById(long empId);

    /**
     * 存储
     *
     * @param empAggregate
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    JsonResult<Boolean> store(EmpAggregate empAggregate);

    /**
     * 删除
     *
     * @param empId
     * @return : com.afiona.common.pojo.JsonResult<java.lang.Void>
     */
    JsonResult<Boolean> delete(long empId);

    /**
     * 批量删除员工
     *
     * @param empIds 员工ids
     */
    JsonResult<Boolean> deleteBatch(List<Long> empIds);

    /**
     * 更新单个员工
     *
     * @param empAggregate 员工聚合根
     * @return 是否成功
     */
    Boolean update(EmpAggregate empAggregate);

}
