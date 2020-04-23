package com.afiona.center.user.domain.model;

import com.afiona.common.model.SuperEntity;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Date;

/**
 * 微信账号
 *
 * @author dengweiyi
 * @date 2020-03-04
 */
@Data
public class WechatUser extends SuperEntity {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * openId
     */
    private String openId;

    /**
     * 微信名
     */
    private String nickname;

    /**
     * 微信头像URL
     */
    private String avatorUrl;

    /**
     * 会话key值
     */
    private String sessionKey;

    /**
     * 微信用户ID
     */
    private String unionId;

    /**
     * 用户
     */
    private User user;
}
