package com.afiona.center.client.promotion.domain.model;

import com.afiona.center.client.promotion.constants.PromotionType;
import com.afiona.center.client.promotion.constants.StatusType;

import java.util.Date;

/**
 * 促销接口
 *
 * @author LiJinXing
 * @date 2020/4/20
 */
public interface IPromotion<T extends IPromotion> extends Comparable<T>{

    Long getId();

    Long getActivityId();

    String getName();

    String getEncoding();

    PromotionType getType();

    String getPurpose();

    Boolean getEnabled();

    StatusType getStatus();

    Date getStartTime();

    Date getEndTime();

    String getDescription();

    String getPosterUrl();

    Long getPriority();
}
