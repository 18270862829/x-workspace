package com.afiona.center.user.infrastructure.model;

import com.afiona.center.user.constants.enums.UserType;
import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户DO
 *
 * @author dengweiyi
 * @date 2020-03-09
 */
@Data
@ApiModel("用户信息")
@TableName("user")
public class UserDO extends SuperEntity {

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 用户类型
     */
    @ApiModelProperty("用户类型")
    private UserType userType;

}
