package com.afiona.center.user.builder;

import com.afiona.center.user.domain.model.WechatUser;

/**
 * 微信账号构建器
 *
 * @author dengweiyi
 * @date 2020-03-09
 */
public interface WechatUserBuilder {
    /**
     * 构建
     *
     * @param code
     * @param encryptedData
     * @param iv
     * @param rawData
     * @param signature
     * @return : com.afiona.center.user.domain.model.WechatUser
     */
    WechatUser build(String code, String encryptedData, String iv, String rawData, String signature);
}
