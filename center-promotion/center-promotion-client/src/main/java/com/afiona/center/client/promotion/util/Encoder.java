package com.afiona.center.client.promotion.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 编码器
 *
 * @author LiJinXing
 * @date 2020/4/21
 */
public final class Encoder {

    private transient static final  int MAX =999998;

    private static volatile AtomicInteger counter = new AtomicInteger();

    private Encoder(){}

    public static String coding(){
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String format = dateTimeFormatter.format(date);
        if(counter.intValue()+1>MAX){
            counter.set(0);
        }
        int setSerialNumber = counter.getAndIncrement();
        return "C" + format + String.format("%06d", setSerialNumber);
    }

}
