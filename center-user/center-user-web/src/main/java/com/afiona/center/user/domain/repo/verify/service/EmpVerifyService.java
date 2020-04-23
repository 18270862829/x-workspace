package com.afiona.center.user.domain.repo.verify.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.afiona.center.client.consultant.client.ConsultantApiClient;
import com.afiona.center.client.consultant.domain.aggregate.ConsultantAggregate;
import com.afiona.center.client.consultant.domain.model.Consultant;
import com.afiona.center.user.constants.enums.UserResultEnum;
import com.afiona.center.user.domain.aggregate.EmpAggregate;
import com.afiona.center.user.infrastructure.dao.EmpDAO;
import com.afiona.center.user.infrastructure.dao.UserDAO;
import com.afiona.center.user.infrastructure.model.EmpDO;
import com.afiona.center.user.infrastructure.model.UserDO;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.BizException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: lujunhan
 * @date: 2020/3/25 16:46
 */

@Component
public class EmpVerifyService {

    @Resource
    private EmpDAO empDAO;

    @Resource
    private UserDAO userDAO;

    @Resource
    private ConsultantApiClient consultantApiClient;

    public void insertEmpVerify(EmpAggregate empAggregate) {
        List<EmpDO> empDOList = empDAO.list(new LambdaQueryWrapper<EmpDO>()
                .eq(EmpDO::getPhone, empAggregate.getPhone()));
        if (CollectionUtil.isNotEmpty(empDOList)) {
            throw new BizException("手机号为：" + empAggregate.getPhone() + "的员工已存在");
        }
        if (empAggregate.isConsultant()) {
            Consultant consultant = new Consultant();
            consultant.setPhone(empAggregate.getPhone());
            JsonResult<ConsultantAggregate> consultantAggregateJsonResult = consultantApiClient.selectOne(consultant);
            if (ObjectUtil.isNotNull(consultantAggregateJsonResult.getData())) {
                throw new BizException("手机号为：" + empAggregate.getPhone() + "的美顾已存在");
            }
        }
    }

    public void deleteEmpVerify(Long empId) {
        EmpDO empDO = empDAO.getById(empId);
        if (empDO == null) {
            throw new BizException("微信账号不存在", UserResultEnum.NOT_EXIST.getCode());
        }
        UserDO userDO = userDAO.getById(empDO.getUserId());
        if (userDO == null) {
            throw new BizException("用户不存在", UserResultEnum.NOT_EXIST.getCode());
        }
    }

    public void deleteBatchEmpVerify(List<Long> empIds) {

    }

    public void updateEmpVerify(EmpAggregate empAggregate) {
        EmpDO empDO = empDAO.getById(empAggregate.getId());
        if (ObjectUtil.isNull(empDO)) {
            throw new BizException("员工不存在", UserResultEnum.NOT_EXIST.getCode());
        }
    }
}
