package com.afiona.center.user.model.query;

import com.afiona.center.user.constants.enums.EmpType;
import com.afiona.center.user.constants.enums.WorkStatus;
import com.afiona.common.pojo.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页查询
 *
 * @author dengweiyi
 * @date 2020-03-09
 */
@Data
@ApiModel("员工条件查询信息")
public class EmpQuery extends PageQuery {

    /**
     * 员工姓名
     */
    @ApiModelProperty("员工姓名")
    private String name;

    /**
     * 员工电话
     */
    @ApiModelProperty("员工电话")
    private String phone;

    /**
     * 员工类型
     */
    @ApiModelProperty("员工类型")
    private EmpType empType;

    /**
     * 工作状态
     */
    @ApiModelProperty("工作状态")
    private WorkStatus workStatus;

}
