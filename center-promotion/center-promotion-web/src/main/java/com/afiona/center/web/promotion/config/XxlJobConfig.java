package com.afiona.center.web.promotion.config;

import com.xxl.job.core.executor.XxlJobExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * XXL定时任务
 *
 * @author LiJinXing
 * @date 2020/4/8
 */
@Configuration
public class XxlJobConfig {

    @Value("${deepexi.job.executor.admin-addresses}")
    private String adminAddresses;

    @Value("${deepexi.job.executor.app-name}")
    private String appName;

    @Value("${deepexi.job.executor.ip}")
    private String ip;

    @Value("${deepexi.job.executor.port}")
    private int port;

    @Value("${deepexi.job.executor.accessToken}")
    private String accessToken;

    @Value("${deepexi.job.executor.log-path}")
    private String logPath;

    @Value("${deepexi.job.executor.log-retention-days}")
    private int logRetentionDays;

    @Bean(initMethod = "start", destroyMethod = "destroy")
    public XxlJobExecutor xxlJobExecutor() {
        XxlJobExecutor xxlJobExecutor = new XxlJobExecutor();
        xxlJobExecutor.setAdminAddresses(adminAddresses);
        xxlJobExecutor.setAppName(appName);
        xxlJobExecutor.setIp(ip);
        xxlJobExecutor.setPort(port);
        xxlJobExecutor.setAccessToken(accessToken);
        xxlJobExecutor.setLogPath(logPath);
        xxlJobExecutor.setLogRetentionDays(logRetentionDays);
        return xxlJobExecutor;
    }
}
