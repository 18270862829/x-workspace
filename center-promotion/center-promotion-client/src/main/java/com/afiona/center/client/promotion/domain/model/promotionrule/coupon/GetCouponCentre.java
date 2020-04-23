package com.afiona.center.client.promotion.domain.model.promotionrule.coupon;

import java.util.Date;

import com.deepexi.util.config.JsonDateSerializer;
import io.swagger.annotations.ApiModel;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import com.afiona.common.model.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 领券中心
 *
 * @author LiJinXing
 * @date 2020/3/5
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel("领券中心")
public class GetCouponCentre  extends SuperEntity {

    /**
     * 优惠券模板id
     */
    private Long couponTemplateId;

    /**
     * 会员id
     */
    private Long memberId;

    /**
     * 领取状态（true-已领取，false-未领取）
     */
    private Boolean receiveState;

    /**
     * 领取开始时间
     */
   @JsonSerialize(using = JsonDateSerializer.class)
    private Date receiveStartTime;

    /**
     * 领取结束时间
     */
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date receiveEndTime;

}
