package com.afiona.center.client.promotion.model.annotation;

import com.afiona.center.client.promotion.constants.choice.OperatingTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 活动切入点注解标识
 *
 * @author LiJinXing
 * @date 2020/3/26
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityPoint {

    OperatingTypeEnum[] value() ;
}
