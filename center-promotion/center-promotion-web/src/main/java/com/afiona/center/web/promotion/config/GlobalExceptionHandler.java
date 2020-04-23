package com.afiona.center.web.promotion.config;

import com.afiona.common.pojo.JsonResult;
import com.afiona.common.util.BizException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author ZhangShuaiQiang
 * @date 2019/12/27 下午2:42
 */


@Aspect
@Component
@Slf4j
public class GlobalExceptionHandler{

    @Pointcut("execution(* com.afiona.center.web.promotion.api.impl.*.*(..))")
    public void globalPointcut(){}

    @Around("globalPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

       try {
          return point.proceed();
       }catch (Exception e){
           e.printStackTrace();
           if(e instanceof BizException){
               return JsonResult.create(4000,e.getMessage());
           }
          throw e;
       }
    }
}
