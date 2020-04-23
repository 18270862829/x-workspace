package com.afiona.center.web.promotion.util;

import com.afiona.center.client.promotion.model.Benefit;
import com.afiona.center.client.promotion.model.MaterialBenefits;
import com.afiona.common.pojo.JsonResult;

/**
 * TODO
 *
 * @author LiJinXing
 * @date 2020/3/27
 */
public class FillUtils {

    public static JsonResult fillResult(MaterialBenefits materialBenefits,JsonResult<Benefit> result){
        Benefit benefit = result.getData();
        if(!result.success()){
            return JsonResult.create(result.getCode(),result.getMsg());
        }
        if(benefit!=null){
            materialBenefits.getBenefitList().add(benefit);
        }
        return JsonResult.create();
    }


}
