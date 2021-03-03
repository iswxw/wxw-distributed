package com.wxw.cached;

import com.wxw.BaseTest;
import com.wxw.dao.EnginesMapper;
import com.wxw.domain.Engines;
import com.wxw.service.EnginesService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：wxw.
 * @date ： 16:06 2021/3/2
 * @description：Mybatis 缓存测试
 * @link: https://tech.meituan.com/2018/01/19/mybatis-cache.html
 * @version: v_0.0.1
 */
@Slf4j
public class MyBatisCachedTest extends BaseTest {

    @Resource
    private EnginesService enginesService;

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 测试一级缓存
     *  1.可以看到，只有第一次真正查询了数据库，后续的查询使用了一级缓存。
     */
    @Test
    public void test_3(){
        SqlSession sqlSession = sqlSessionFactory.openSession(true);// 启动提交服务
        EnginesMapper mapper = sqlSession.getMapper(EnginesMapper.class);
        log.info(" mapper1 = {}", mapper.selectByEngine("InnoDB"));
        log.info(" mapper2 = {}", mapper.selectByEngine("InnoDB"));
        log.info(" mapper3 = {}", mapper.selectByEngine("InnoDB"));
    }

    /**
     * 在修改操作后执行的相同查询，查询了数据库，一级缓存失效。
     */
    @Test
    public void test_4(){
        SqlSession sqlSession = sqlSessionFactory.openSession(true);// 启动提交服务
        EnginesMapper mapper = sqlSession.getMapper(EnginesMapper.class);
        log.info(" mapper1 = {}", mapper.selectByEngine("InnoDB"));
        sqlSession.clearCache();
    }


    @Test
    public void test_2(){
        Engines engines = enginesService.selectByEngine("InnoDB");
        log.info(" engines = {}", engines);
    }

    @Test
    public void test_1(){
        List<Engines> enginesList = enginesService.queryEngineList();
        enginesList.forEach(engine -> {
            log.info(" engine = {}", engine);
        });
    }
}
