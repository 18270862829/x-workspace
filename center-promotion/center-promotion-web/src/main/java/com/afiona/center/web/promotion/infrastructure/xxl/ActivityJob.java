package com.afiona.center.web.promotion.infrastructure.xxl;

import cn.hutool.core.collection.CollectionUtil;
import com.afiona.center.client.promotion.constants.StatusType;
import com.afiona.center.web.promotion.infrastructure.dao.ActivityDAO;
import com.afiona.center.web.promotion.infrastructure.model.ActivityDO;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 活动状态更新定时
 *
 * @author LiJinXing
 * @date 2020/4/15
 */
@Component
@Slf4j
public class ActivityJob {

    @Resource
    private ActivityDAO activityDAO;

    @XxlJob("activity_status_update_job")
    public ReturnT<String> activityStatusUpdateJob(){
        List<ActivityDO> dos = activityDAO.list();
        List<ActivityDO> activityList = dos.stream()
                .filter(this::statusVerify)
                .peek(this::statusSetting).collect(Collectors.toList());
        if(CollectionUtil.isEmpty(activityList)){
            return ReturnT.SUCCESS;
        }
        activityDAO.updateBatchById(activityList);
        return ReturnT.SUCCESS;
    }

    @SuppressWarnings("all")
    private boolean statusVerify(ActivityDO activityDO) {
        Date now = new Date();
        StatusType status = activityDO.getStatus();
        Date startTime = activityDO.getStartTime();
        Date endTime = activityDO.getEndTime();
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

    private void statusSetting(ActivityDO activity){
        Date now = new Date();
        if (now.before(activity.getStartTime())) {
            activity.setStatus(StatusType.HAS_NOT_STARTED);
        } else if (now.after(activity.getEndTime())) {
            activity.setStatus(StatusType.EXPIRED);
        } else {
            activity.setStatus(StatusType.PROCESSING);
        }
    }
}
