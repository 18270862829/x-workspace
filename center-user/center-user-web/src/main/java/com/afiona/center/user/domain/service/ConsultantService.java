package com.afiona.center.user.domain.service;

import cn.hutool.core.util.ObjectUtil;
import com.afiona.center.client.consultant.client.ConsultantApiClient;
import com.afiona.center.client.consultant.client.ShopApiClient;
import com.afiona.center.client.consultant.constants.common.RemoteAccessEnum;
import com.afiona.center.client.consultant.domain.aggregate.ConsultantAggregate;
import com.afiona.center.client.consultant.domain.model.Shop;
import com.afiona.center.user.domain.aggregate.EmpAggregate;
import com.afiona.center.user.domain.model.Emp;
import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.BizException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 美顾服务
 *
 * @author dengweiyi
 * @date 2020-03-11
 */
@Service
public class ConsultantService {
    @Resource
    private ConsultantApiClient consultantApiClient;

    @Resource
    private ShopApiClient shopApiClient;

    public void createConsultant(Emp emp) {
        ConsultantAggregate consultantAggregate = new ConsultantAggregate();
        consultantAggregate.setEmpId(emp.getId());
//        consultantAggregate.setCode(emp.getJobNumber());
        consultantAggregate.setName(emp.getName());
        consultantAggregate.setPhone(emp.getPhone());
        JsonResult<Shop> shopJsonResult = shopApiClient.getShop(emp.getShopId());
        if (!shopJsonResult.success()) {
            throw new BizException(RemoteAccessEnum.REMOTE_ACCESS_FAILED);
        }
        Shop shop = shopJsonResult.getData();
        if (ObjectUtil.isNotNull(shop)) {
            String shopName = shop.getName();
            Shop insertShop = new Shop();
            insertShop.setName(shopName);
            consultantAggregate.setShop(insertShop);
        }
        consultantApiClient.insertOneForUserCenter(consultantAggregate);
    }

    public Boolean createConsultantAggregate(EmpAggregate empAggregate) {
        ConsultantAggregate consultantAggregate = new ConsultantAggregate();
        consultantAggregate.setEmpId(empAggregate.getId());
//        consultantAggregate.setCode(emp.getJobNumber());
        consultantAggregate.setName(empAggregate.getName());
        consultantAggregate.setPhone(empAggregate.getPhone());
        Shop insertShop = new Shop();
        insertShop.setName(empAggregate.getShopName());
        consultantAggregate.setShop(insertShop);
        JsonResult<Boolean> booleanJsonResult = consultantApiClient.insertOneForUserCenter(consultantAggregate);
        if (!booleanJsonResult.success()) {
            return false;
        }
        return booleanJsonResult.getData();
    }

    public Boolean deleteConsultant(long empId) {
        JsonResult<ConsultantAggregate> consultantAggregateJsonResult = consultantApiClient.selectOneByEmpId(empId);
        if (!consultantAggregateJsonResult.success()) {
            throw new BizException(RemoteAccessEnum.REMOTE_ACCESS_FAILED);
        }
        ConsultantAggregate consultantAggregate = consultantAggregateJsonResult.getData();
        JsonResult<Boolean> booleanJsonResult = null;
        if (ObjectUtil.isNotNull(consultantAggregate)) {
            Long id = consultantAggregate.getId();
            booleanJsonResult = consultantApiClient.delete(id);
            if (!booleanJsonResult.success()) {
                return false;
            }
        }
        return booleanJsonResult.getData();
    }

    public Boolean updateConsultant(EmpAggregate empAggregate) {
        String phone = empAggregate.getPhone();
        String name = empAggregate.getName();
        Long empId = empAggregate.getId();
        String shopName = empAggregate.getShopName();
        JsonResult<ConsultantAggregate> consultantAggregateJsonResult = consultantApiClient.selectOneByEmpId(empId);
        if (!consultantAggregateJsonResult.success()) {
            throw new BizException(RemoteAccessEnum.REMOTE_ACCESS_FAILED);
        }
        ConsultantAggregate consultantAggregate = consultantAggregateJsonResult.getData();
        if (ObjectUtil.isNotNull(consultantAggregate)) {
            consultantAggregate.setPhone(phone);
            consultantAggregate.setName(name);
            Shop shop = new Shop();
            shop.setName(shopName);
            consultantAggregate.setShop(shop);
        }
        JsonResult<Boolean> booleanJsonResult = consultantApiClient.update(consultantAggregate);
        if (!booleanJsonResult.success()) {
            return false;
        }
        return booleanJsonResult.getData();
    }

}
