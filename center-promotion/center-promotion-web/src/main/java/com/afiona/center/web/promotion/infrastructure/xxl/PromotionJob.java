package com.afiona.center.web.promotion.infrastructure.xxl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.client.promotion.constants.StatusType;
import com.afiona.center.web.promotion.infrastructure.dao.PromotionDAO;
import com.afiona.center.web.promotion.infrastructure.model.PromotionDO;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 促销状态定时任务
 *
 * @author LiJinXing
 * @date 2020/4/8
 */
@Component
@Slf4j
public class PromotionJob {

     @Resource
     private PromotionDAO promotionDAO;

     @XxlJob("promotion_status_update_job")
     public ReturnT<String> promotionStatusUpdateJob(){
         List<PromotionDO> dos = promotionDAO.list();
         List<PromotionDO> promotions = dos.stream()
                 .filter(this::statusVerify)
                 .peek(this::statusSetting).collect(Collectors.toList());
         if(CollectionUtil.isEmpty(promotions)){
             return ReturnT.SUCCESS;
         }
         promotionDAO.updateBatchById(promotions);
         return ReturnT.SUCCESS;
     }

    @SuppressWarnings("all")
     private boolean statusVerify(PromotionDO promotion){
         Date now = new Date();
         StatusType status = promotion.getStatus();
         Date startTime = promotion.getStartTime();
         Date endTime = promotion.getEndTime();
         if(startTime==null||endTime==null){
             return false;
         }
         if(status==null){
             return true;
         }
         return  (now.before(startTime) && !status.equals(StatusType.HAS_NOT_STARTED))
                 || (now.after(endTime) && !status.equals(StatusType.EXPIRED))
                 || ((now.before(endTime) && now.after(startTime) && !status.equals(StatusType.PROCESSING)));
     }

     private void statusSetting(PromotionDO promotion){
         Date now = new Date();
         if (now.before(promotion.getStartTime())) {
             promotion.setStatus(StatusType.HAS_NOT_STARTED);
         } else if (now.after(promotion.getEndTime())) {
             promotion.setStatus(StatusType.EXPIRED);
         } else {
             promotion.setStatus(StatusType.PROCESSING);
         }
     }

}
