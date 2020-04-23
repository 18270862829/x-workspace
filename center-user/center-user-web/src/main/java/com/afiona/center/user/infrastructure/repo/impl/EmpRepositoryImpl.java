package com.afiona.center.user.infrastructure.repo.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.afiona.center.client.consultant.client.ShopApiClient;
import com.afiona.center.client.consultant.constants.common.GlobalResultEnum;
import com.afiona.center.client.consultant.constants.common.RemoteAccessEnum;
import com.afiona.center.client.consultant.domain.model.Shop;
import com.afiona.center.user.constants.enums.UserResultEnum;
import com.afiona.center.user.constants.enums.UserType;
import com.afiona.center.user.domain.aggregate.EmpAggregate;
import com.afiona.center.user.domain.model.Emp;
import com.afiona.center.user.domain.model.User;
import com.afiona.center.user.domain.repo.EmpRepository;
import com.afiona.center.user.domain.repo.UserRepository;
import com.afiona.center.user.domain.repo.verify.service.EmpVerifyService;
import com.afiona.center.user.domain.service.ConsultantService;
import com.afiona.center.user.domain.service.UserService;
import com.afiona.center.user.infrastructure.dao.EmpDAO;
import com.afiona.center.user.infrastructure.dao.UserDAO;
import com.afiona.center.user.infrastructure.model.EmpDO;
import com.afiona.center.user.infrastructure.model.UserDO;
import com.afiona.center.user.model.query.EmpQuery;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.BizException;
import com.afiona.common.util.CloneUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.deepexi.util.pageHelper.PageBean;
import com.github.pagehelper.page.PageMethod;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 员工repo实现
 *
 * @author dengweiyi
 * @date 2020-03-04
 */

@Repository
public class EmpRepositoryImpl implements EmpRepository {
    @Resource
    private EmpDAO empDAO;

    @Resource
    private UserService userService;

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserDAO userDAO;

    @Resource
    private ConsultantService consultantService;

    @Resource
    private EmpVerifyService empVerifyService;

    @Resource
    private ShopApiClient shopApiClient;

    @Override
    public JsonResult<Emp> me() {
        User user = userService.me();
        if (user == null) {
            return JsonResult.create(UserResultEnum.NOT_LOGIN);
        }
        if (user.getUserType() != UserType.NORMAL) {
            return JsonResult.create(UserResultEnum.WRONG_TYPE);
        }
        EmpDO empDO = empDAO.getOne(new LambdaQueryWrapper<EmpDO>().eq(EmpDO::getUserId, user.getId()));
        if (empDO == null) {
            return JsonResult.create(UserResultEnum.EXCEPTION);
        }
        Emp emp = CloneUtil.clone(empDO, Emp.class);
        emp.setUser(user);
        return JsonResult.create(emp);
    }

    @Override
    public JsonResult<PageBean<EmpAggregate>> listPage(EmpQuery query) {
        PageMethod.startPage(query.getPage(), query.getSize());
        List<EmpDO> empDOList = empDAO.list(new LambdaQueryWrapper<EmpDO>()
                .like(StringUtils.isNotBlank(query.getName()), EmpDO::getName, query.getName())
                .like(StringUtils.isNotBlank(query.getPhone()), EmpDO::getPhone, query.getPhone())
                .eq(ObjectUtil.isNotNull(query.getEmpType()), EmpDO::getEmpType, query.getEmpType())
                .eq(ObjectUtil.isNotNull(query.getWorkStatus()), EmpDO::getCompilationType, query.getWorkStatus()));
        List<Long> userIds = empDOList.stream().map(EmpDO::getUserId).collect(Collectors.toList());
        List<User> userList = userRepository.listByIds(userIds);
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, Function.identity()));
        List<Emp> empList = CloneUtil.cloneList(empDOList, Emp.class);
        if (CollectionUtil.isNotEmpty(empList)) {
            empList.forEach(e -> {
                User user = userMap.get(e.getUserId());
                if (ObjectUtil.isNotNull(user)) {
                    return;
                }
                e.setUser(user);
            });
        }
        List<EmpAggregate> empAggregateList = getEmpAggregateList(empList);
        PageBean<EmpAggregate> pageBean = new PageBean<>(empAggregateList);
        return JsonResult.create(pageBean);
    }

    private List<EmpAggregate> getEmpAggregateList(List<Emp> empList) {
        List<Long> shopIds = empList.stream().map(Emp::getShopId).collect(Collectors.toList());
        List<EmpAggregate> aggregateList = CloneUtil.cloneList(empList, EmpAggregate.class);
        for (int i = 0; i < aggregateList.size(); i++) {
            EmpAggregate empAggregate = aggregateList.get(i);
            Long shopId = shopIds.get(i);
            JsonResult<Shop> shopJsonResult = shopApiClient.getShop(shopId);
            if (!shopJsonResult.success()) {
                continue;
            }
            Shop shop = shopJsonResult.getData();
            if (ObjectUtil.isNotNull(shop)) {
                empAggregate.setShopName(shop.getName());
            }
        }
        return aggregateList;
    }

    @Override
    public JsonResult<List<EmpAggregate>> listByIds(List<Long> empIds) {
        List<Emp> empList = doListByIds(empIds);
        List<EmpAggregate> empAggregateList = getEmpAggregateList(empList);
        return JsonResult.create(empAggregateList);
    }

    private List<Emp> doListByIds(List<Long> empIds) {
        List<EmpDO> wechatUserDOS = empDAO.list(new LambdaQueryWrapper<EmpDO>().in(EmpDO::getId, empIds));
        List<Emp> emps = CloneUtil.cloneList(wechatUserDOS, Emp.class);

        List<Long> userIds = emps.stream().map(Emp::getUserId).collect(Collectors.toList());
        List<User> users = userRepository.listByIds(userIds);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, Function.identity()));
        for (Emp emp : emps) {
            User user = userMap.get(emp.getUserId());
            if (user == null) {
                continue;
            }
            emp.setUser(user);
        }
        return emps;
    }

    @Override
    public JsonResult<EmpAggregate> getById(long empId) {
        List<Emp> emps = doListByIds(Lists.newArrayList(empId));
        if (CollectionUtil.isEmpty(emps)) {
            return JsonResult.create();
        }
        List<EmpAggregate> empAggregateList = getEmpAggregateList(emps);
        return JsonResult.create(empAggregateList.get(0));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JsonResult<Boolean> store(EmpAggregate empAggregate) {
        empVerifyService.insertEmpVerify(empAggregate);
        String phone = empAggregate.getPhone();
        UserDO userDO = new UserDO();
        //将手机号作为账号
        userDO.setUsername(phone);
        //设置初始密码
        userDO.setPassword("123456");
        Boolean success = userDAO.save(userDO);
        if (!success) {
            return JsonResult.create(false);
        }
        EmpDO empDO = CloneUtil.clone(empAggregate, EmpDO.class);
        empDO.setUserId(userDO.getId());
        String shopName = empAggregate.getShopName();
        Long shopId = null;
        if (StringUtils.isNotBlank(shopName)) {
            JsonResult<Shop> shopJsonResult = shopApiClient.getShopByName(shopName);
            if (!shopJsonResult.success()) {
                return JsonResult.create(GlobalResultEnum.SUCCESS.getCode(), shopJsonResult.getMsg());
            }
            Shop shop = shopJsonResult.getData();
            if (ObjectUtil.isNotNull(shop)) {
                shopId = shop.getId();
            }
        }
        empDO.setShopId(shopId);
        success = empDAO.save(empDO);
        if (!success) {
            return JsonResult.create(false);
        }
        empAggregate.setId(empDO.getId());
        if (empAggregate.isConsultant()) {
            success = consultantService.createConsultantAggregate(empAggregate);
            if (!success) {
                return JsonResult.create(false);
            }
        }
        return JsonResult.create(success);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JsonResult<Boolean> delete(long empId) {
        empVerifyService.deleteEmpVerify(empId);
        EmpDO empDO = empDAO.getById(empId);
        UserDO userDO = userDAO.getById(empDO.getUserId());
        Boolean success = empDAO.removeById(empId);
        if (!success) {
            return JsonResult.create(false);
        }
        success = userDAO.removeById(userDO.getId());
        if (!success) {
            return JsonResult.create(false);
        }
        if (empDO.isConsultant()) {
            success = consultantService.deleteConsultant(empId);
        }
        return JsonResult.create(success);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JsonResult<Boolean> deleteBatch(List<Long> empIds) {
        Collection<EmpDO> empDOS = empDAO.listByIds(empIds);
        List<EmpDO> empDOList = new ArrayList<>(empDOS);
        Boolean success = true;
        if (CollectionUtil.isNotEmpty(empDOList)) {
            List<Long> userIds = empDOList.stream().map(EmpDO::getUserId).collect(Collectors.toList());
            success = empDAO.removeByIds(empIds);
            if (!success) {
                return JsonResult.create(false);
            }
            success = userDAO.removeByIds(userIds);
            if (!success) {
                return JsonResult.create(false);
            }
            empDOList.forEach(e -> {
                if (e.isConsultant()) {
                    Boolean result = consultantService.deleteConsultant(e.getId());
                    if (!result) {
                        throw new BizException("删除失败");
                    }
                }
            });
        }
        return JsonResult.create(success);
    }

    @Override
    public Boolean update(EmpAggregate empAggregate) {
        empVerifyService.updateEmpVerify(empAggregate);
        Long empId = empAggregate.getId();
        EmpDO empDO = empDAO.getById(empId);
        Boolean success = true;
        //将员工修改为美顾
        if (empDO.isConsultant() == false && empAggregate.isConsultant() == true) {
            success = consultantService.createConsultantAggregate(empAggregate);
            if (!success) {
                return false;
            }
        }
        //将员工修改为不是美顾
        if (empDO.isConsultant() == true && empAggregate.isConsultant() == false) {
            success = consultantService.deleteConsultant(empAggregate.getId());
            if (!success) {
                return false;
            }
        }
        Long currentShopId = empDO.getShopId();
        if (StringUtils.isNotBlank(empAggregate.getShopName())) {

            String currentShopName = empAggregate.getShopName();
            JsonResult<Shop> shopJsonResult = shopApiClient.getShopByName(currentShopName);
            if (!shopJsonResult.success()) {
                throw new BizException(RemoteAccessEnum.REMOTE_ACCESS_FAILED);
            }
            Shop shop = shopJsonResult.getData();
            if (ObjectUtil.isNotNull(shop)) {
                currentShopId = shop.getId();
            }
        }
        //更改手机号或员工名称或门店
        if (!empDO.getPhone().equals(empAggregate.getPhone()) || !empDO.getName().equals(empAggregate.getName()) || !empDO.getShopId().equals(currentShopId)) {
            if (empAggregate.isConsultant()) {
                success = consultantService.updateConsultant(empAggregate);
                if (!success) {
                    return false;
                }
            }
        }
        EmpDO updateEmpDO = CloneUtil.clone(empAggregate, EmpDO.class);
        updateEmpDO.setShopId(currentShopId);
        success = empDAO.updateById(updateEmpDO);
        if (!success) {
            return false;
        }
        return success;
    }

}
