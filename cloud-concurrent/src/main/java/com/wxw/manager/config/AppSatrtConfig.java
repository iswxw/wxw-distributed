package com.wxw.manager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @author weixiaowei
 * @desc:
 * @date: 2021/7/8
 */
@EnableRetry
@EnableAspectJAutoProxy
@Configuration
public class AppSatrtConfig {
}
