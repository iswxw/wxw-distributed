package com.wxw.cached;

import com.wxw.BaseTest;
import com.wxw.dao.PersonMapper;
import com.wxw.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import javax.annotation.Resource;

/**
 * @author ：wxw.
 * @date ： 16:06 2021/3/2
 * @description：Mybatis 二级缓存测试
 * @link: https://tech.meituan.com/2018/01/19/mybatis-cache.html
 * @version: v_0.0.1
 */
@Slf4j
public class MyBatisTwoCachedTest extends BaseTest {

    @Resource
    private PersonService personService;

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 测试二级缓存效果，
     *  (1) 不提交事务，sqlSession1查询完数据后，sqlSession2相同的查询是否会从缓存中获取数据。
     *   结论：当sqlsession没有调用commit()方法时，二级缓存并没有起到作用。
     *   结论：调用commit()方法时，二级缓存起作用，命中率为50%
     */
    @Test
    public void test_1(){
        SqlSession sqlSession1 = sqlSessionFactory.openSession(true);
        SqlSession sqlSession2 = sqlSessionFactory.openSession(true);

        PersonMapper personMapper1 = sqlSession1.getMapper(PersonMapper.class);
        sqlSession1.commit(); // 缓存命中50%
        PersonMapper personMapper2 = sqlSession2.getMapper(PersonMapper.class);

        log.info(" personMapper1 = {}", personMapper1.selectById(1));
        log.info(" personMapper2 = {}", personMapper2.selectById(1));
    }

}
