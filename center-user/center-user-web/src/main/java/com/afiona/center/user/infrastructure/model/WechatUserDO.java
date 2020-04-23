package com.afiona.center.user.infrastructure.model;

import com.afiona.common.model.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 微信账号DO
 *
 * @author dengweiyi
 * @date 2020-03-05
 */
@Data
@ApiModel("微信用户信息")
@TableName("wechat_user")
public class WechatUserDO extends SuperEntity {

    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Long userId;

    /**
     * openId
     */
    @ApiModelProperty("openId")
    private String openId;

    /**
     * 微信名
     */
    @ApiModelProperty("微信名")
    private String nickname;

    /**
     * 微信头像URL
     */
    @ApiModelProperty("微信头像URL")
    private String avatorUrl;

    /**
     * 会话key值
     */
    @ApiModelProperty("会话key值")
    private String sessionKey;

    /**
     * 微信用户ID
     */
    @ApiModelProperty("微信用户ID")
    private String unionId;

}
