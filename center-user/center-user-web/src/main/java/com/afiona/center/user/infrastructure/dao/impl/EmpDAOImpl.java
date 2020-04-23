package com.afiona.center.user.infrastructure.dao.impl;

import com.afiona.center.user.infrastructure.dao.EmpDAO;
import com.afiona.center.user.infrastructure.mapper.EmpMapper;
import com.afiona.center.user.infrastructure.model.EmpDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 员工DAO实现
 *
 * @author dengweiyi
 * @date 2020-03-04
 */
@Repository
public class EmpDAOImpl extends ServiceImpl<EmpMapper, EmpDO> implements EmpDAO {
}
