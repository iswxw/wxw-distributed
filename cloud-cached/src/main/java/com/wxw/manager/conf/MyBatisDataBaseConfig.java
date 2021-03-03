package com.wxw.manager.conf;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author ：wxw.
 * @date ： 17:31 2021/3/2
 * @description：
 * @link:
 * @version: v_0.0.1
 */
@Configuration
public class MyBatisDataBaseConfig {

    /**
     * dao层的包路径
     */
    private static final String PACKAGE = "com.xkw.marketing.dao.mapper";

    /**
     * mapper文件的相对路径
     */
    private static final String MAPPER_LOCATION = "classpath:mapper/*.xml";

    @Value("${mybatis.configuration.cache-enabled:false}")
    private boolean cacheEnabled;
    @Value("${mybatis.configuration.log-impl:}")
    private Class<? extends Log> logImpl;

    // 创建Mybatis的连接会话工厂实例
    @Bean
    public SqlSessionFactory primarySqlSessionFactory(DataSource primaryDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(primaryDataSource);
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(MyBatisDataBaseConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
