package com.wxw.mysql;

import com.alibaba.druid.pool.DruidDataSource;
import com.wxw.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author ：wxw.
 * @date ： 16:45 2021/3/3
 * @description：
 * @link:
 * @version: v_0.0.1
 */
@Slf4j
public class MySqlTest extends BaseTest {

    @Resource
    private DataSource dataSource;

    @Resource
    private DruidDataSource druidDataSource;

    @Test
    public void test_1() throws SQLException {
        log.info(" DataSource = {}", dataSource.getConnection());
        log.info(" URL = {}", druidDataSource.getUrl());
    }
}
