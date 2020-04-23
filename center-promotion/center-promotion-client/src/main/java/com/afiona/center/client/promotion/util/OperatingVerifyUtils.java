package com.afiona.center.client.promotion.util;

import com.afiona.center.client.promotion.constants.StatusType;
import com.afiona.center.client.promotion.constants.choice.OperatingTypeEnum;
/**
 * 活动矩阵操作校验
 *
 * @author LiJinXing
 * @date 2020/3/25
 */
public class OperatingVerifyUtils{

    public static Boolean enabledSetting(Boolean currentEnabled, StatusType status, OperatingTypeEnum operatingType) {
        if(operatingType.equals(OperatingTypeEnum.OPERATING_START)){
            return !currentEnabled;
        }
        else if(operatingType.equals(OperatingTypeEnum.OPERATING_STOP)){
            return currentEnabled;
        }
        if(operatingType.equals(OperatingTypeEnum.OPERATING_UPDATE)){
             return !((status.equals(StatusType.PROCESSING)&&currentEnabled)||status.equals(StatusType.EXPIRED));
        }
        else if(operatingType.equals(OperatingTypeEnum.OPERATING_DELETE)){
            return !((status.equals(StatusType.HAS_NOT_STARTED)&&currentEnabled)||(status.equals(StatusType.PROCESSING)&&currentEnabled));
        }
        return true;
    }
}
