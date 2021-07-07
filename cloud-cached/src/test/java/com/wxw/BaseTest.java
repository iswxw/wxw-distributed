package com.wxw;

import com.alibaba.druid.pool.DruidDataSource;
import com.wxw.manager.config.MyBatisDataBaseConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author ：wxw.
 * @date ： 16:06 2021/3/2
 * @description：
 * @link:
 * @version: v_0.0.1
 */
@SpringBootTest
@RunWith(SpringRunner.class) // 将bean注入到Spring容器
public class BaseTest {

    protected long timeDistance = 0;

    @Resource
    private DruidDataSource druidDataSource;

    @Before
    public void before() throws Exception {
        MyBatisDataBaseConfig myBatisDataBaseConfig = new MyBatisDataBaseConfig();
        myBatisDataBaseConfig.primarySqlSessionFactory(druidDataSource);
        timeDistance = System.currentTimeMillis();
    }

    @After
    public void after(){
        System.err.printf("执行总耗时：%s \n",( System.currentTimeMillis() - timeDistance) + "ms");
    }
}
