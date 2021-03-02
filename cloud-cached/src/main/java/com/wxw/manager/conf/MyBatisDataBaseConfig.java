package com.wxw.manager.conf;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author ：wxw.
 * @date ： 17:31 2021/3/2
 * @description：
 * @link:
 * @version: v_0.0.1
 */
public class MyBatisDataBaseConfig {

    /**
     * dao层的包路径
     */
    static final String PACKAGE = "com.xkw.marketing.dao.mapper";

    /**
     * mapper文件的相对路径
     */
    private static final String MAPPER_LOCATION = "classpath:mapper/*.xml";

    @Value("${mybatis.configuration.cache-enabled:false}")
    private boolean cacheEnabled;
    @Value("${mybatis.configuration.log-impl:}")
    private Class<? extends Log> logImpl;

    /**
     * 创建Mybatis的连接会话工厂实例
     * @param primaryDataSource
     * @return
     * @throws Exception
     */
    @Primary
    @Bean(name = "primarySqlSessionFactory")
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDataSource") DataSource primaryDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(primaryDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MyBatisDataBaseConfig.MAPPER_LOCATION));
        Configuration configuration = new Configuration();
        configuration.setCacheEnabled(cacheEnabled);
        configuration.setLogImpl(logImpl);
        sessionFactory.setConfiguration(configuration);
        return sessionFactory.getObject();
    }
}
