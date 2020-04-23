package com.afiona.center.client.promotion.util;

import java.util.Date;

import com.deepexi.util.config.JsonDateSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.deepexi.util.config.JsonDateSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 区间辅助类
 *
 * @author dengweiyi
 * @date 2020-02-06
 */
public class RangeUtil {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TimeRange{
        /**
         * 开始时间
         */
        @JsonSerialize(using = JsonDateSerializer.class)
        private Date startTime;

        /**
         * 结束时间
         */
        @JsonSerialize(using = JsonDateSerializer.class)
        private Date endTime;
    }
}
