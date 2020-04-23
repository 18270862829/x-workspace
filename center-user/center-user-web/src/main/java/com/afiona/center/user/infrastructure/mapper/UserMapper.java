package com.afiona.center.user.infrastructure.mapper;

import com.afiona.center.user.infrastructure.model.UserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户mapper
 *
 * @author dengweiyi
 * @date 2020-03-09
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
}
