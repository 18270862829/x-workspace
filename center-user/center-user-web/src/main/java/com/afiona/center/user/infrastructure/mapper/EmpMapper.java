package com.afiona.center.user.infrastructure.mapper;

import com.afiona.center.user.infrastructure.model.EmpDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 员工mapper
 *
 * @author dengweiyi
 * @date 2020-03-04
 */
@Mapper
public interface EmpMapper extends BaseMapper<EmpDO> {
}
