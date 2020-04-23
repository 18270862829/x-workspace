package com.afiona.center.user.infrastructure.model;

import com.afiona.center.user.constants.enums.CompilationType;
import com.afiona.center.user.constants.enums.EmpType;
import com.afiona.center.user.constants.enums.WorkStatus;
import com.afiona.center.user.domain.model.User;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 员工DO
 *
 * @author dengweiyi
 * @date 2020-03-04
 */
@Data
@ApiModel("员工信息")
@TableName("emp")
public class EmpDO extends SuperEntity {

    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Long userId;

    /**
     * 门店ID
     */
    @ApiModelProperty("门店ID")
    private Long shopId;

    /**
     * 预留字段 商家ID
     */
    @ApiModelProperty("商家ID")
    private Long businessId;

    /**
     * 工号
     */
    @ApiModelProperty("工号")
    private String jobNumber;

    /**
     * 用户中心账号编码
     */
//    private String userCode;

    /**
     * OA系统编码
     */
    @ApiModelProperty("OA系统编码")
    private String oaCode;

    /**
     * EHR系统编号
     */
    @ApiModelProperty("EHR系统编号")
    private String ehrCode;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String phone;

    /**
     * 工作状态
     */
    @ApiModelProperty("工作状态")
    private WorkStatus workStatus;

    /**
     * 员工类型
     */
    @ApiModelProperty("员工类型")
    private EmpType empType;

    /**
     * 是否为美顾 0-否 1-是
     */
    @ApiModelProperty("是否为美顾")
    private boolean consultant;

    /**
     * 编制类型
     */
    @ApiModelProperty("编制类型")
    private CompilationType compilationType;

}
