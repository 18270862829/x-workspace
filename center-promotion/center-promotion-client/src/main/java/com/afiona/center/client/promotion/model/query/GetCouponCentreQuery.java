package com.afiona.center.client.promotion.model.query;

import com.afiona.common.pojo.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 领券中心条件查询
 *
 * @author LiJinXing
 * @date 2020/3/5
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class GetCouponCentreQuery  extends PageQuery {

    /**
     * 会员id
     */
    private Long memberId;

}
