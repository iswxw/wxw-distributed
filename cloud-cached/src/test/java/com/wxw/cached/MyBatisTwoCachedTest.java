package com.wxw.cached;

import com.wxw.BaseTest;
import com.wxw.dao.PersonMapper;
import com.wxw.domain.PersonExample;
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
     *   结论1：当sqlsession没有调用commit()或close()方法时，二级缓存并没有起到作用。
     */
    @Test
    public void test_1(){
        SqlSession sqlSession1 = sqlSessionFactory.openSession(true);
        SqlSession sqlSession2 = sqlSessionFactory.openSession(true);

        PersonMapper personMapper1 = sqlSession1.getMapper(PersonMapper.class);
        log.info(" personMapper1 = {}", personMapper1.selectById(3));
        sqlSession1.commit(); // 缓存命中50%
        PersonMapper personMapper2 = sqlSession2.getMapper(PersonMapper.class);
        log.info(" personMapper2 = {}", personMapper2.selectById(3));
    }

    /**
     * 结论2：DML.insert,update/delete 等操作并提交后，会清空缓存
     */
    @Test
    public void test_2(){
        SqlSession sqlSession1 = sqlSessionFactory.openSession(true);
        SqlSession sqlSession2 = sqlSessionFactory.openSession(true);
        SqlSession sqlSession3 = sqlSessionFactory.openSession(true);

        PersonMapper personMapper1 = sqlSession1.getMapper(PersonMapper.class);
        log.info(" personMapper1 = {}", personMapper1.selectById(3));
        sqlSession1.commit(); // 缓存刷新到SqlSessionFactory|| namespace
        PersonMapper personMapper2 = sqlSession2.getMapper(PersonMapper.class);
        // 缓存命中50%
        log.info(" personMapper2 = {}", personMapper2.selectById(3));

        PersonMapper personMapper3 = sqlSession3.getMapper(PersonMapper.class);
        PersonExample example = new PersonExample();
        example.createCriteria().andIdEqualTo(2);
        personMapper3.deleteByExample(example);
        log.info(" personMapper3 = {}", personMapper3.selectById(3));

    }

}
