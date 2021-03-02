package com.wxw.mybatis;

import com.wxw.BaseTest;
import com.wxw.dao.EnginesMapper;
import com.wxw.domain.Engines;
import com.wxw.domain.EnginesExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.Reader;

/**
 * @author ：wxw.
 * @date ： 16:36 2021/3/2
 * @description：
 * @link: https://cloud.tencent.com/developer/article/1457987 mybatis 源码
 * @version: v_0.0.1
 */
@Slf4j
public class SessionTest extends BaseTest {
    
    @Test
    public void test_1(){
        try {
            // 基本mybatis环境
            // 1.定义mybatis_config文件地址
            String resources = "mybatis_config.xml";
            // 2.获取InputStreamReaderIo流
            Reader reader = Resources.getResourceAsReader(resources);
            // 3.获取SqlSessionFactory
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            // 4.获取Session
            SqlSession sqlSession = sqlSessionFactory.openSession();
            // 5.操作Mapper接口
            EnginesMapper enginesMapper = sqlSession.getMapper(EnginesMapper.class);
            long count = enginesMapper.countByExample(new EnginesExample());
            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
