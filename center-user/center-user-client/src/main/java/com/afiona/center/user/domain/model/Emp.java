package com.afiona.center.user.domain.model;

import com.afiona.center.user.constants.enums.CompilationType;
import com.afiona.center.user.constants.enums.EmpType;
import com.afiona.center.user.constants.enums.WorkStatus;
import com.afiona.common.model.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;

import static com.afiona.center.user.util.PhoneCheck.PHONE_PATTERN;

/**
 * 员工
 *
 * @author dengweiyi
 * @date 2020-03-03
 */
@Data
@ApiModel("员工基础信息")
public class Emp extends SuperEntity {

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
    @NotBlank(message = "姓名不能为空", groups = Create.class)
    private String name;

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    @Pattern(regexp = PHONE_PATTERN)
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
    @ApiModelProperty("是否为美顾 0-否 1-是")
    private boolean consultant;

    /**
     * 编制类型
     */
    @ApiModelProperty("编制类型")
    private CompilationType compilationType;

    /**
     * 用户
     */
    @ApiModelProperty("用户")
    private User user;

    public interface Create extends Default {
    }

    public interface Update extends Default {
    }

}
