package com.afiona.center.user.infrastructure.mapper;

import com.afiona.center.user.infrastructure.model.WechatUserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 微信账号mapper
 *
 * @author dengweiyi
 * @date 2020-03-05
 */
@Mapper
public interface WechatUserMapper extends BaseMapper<WechatUserDO> {
}
