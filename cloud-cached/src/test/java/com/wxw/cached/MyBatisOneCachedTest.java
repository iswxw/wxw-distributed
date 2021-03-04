package com.wxw.cached;

import com.wxw.BaseTest;
import com.wxw.dao.PersonMapper;
import com.wxw.domain.Person;
import com.wxw.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author ：wxw.
 * @date ： 16:06 2021/3/2
 * @description：Mybatis 缓存测试
 * @link: https://tech.meituan.com/2018/01/19/mybatis-cache.html
 * @version: v_0.0.1
 */
@Slf4j
public class MyBatisOneCachedTest extends BaseTest {

    @Resource
    private PersonService personService;

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 测试一级缓存
     *  1.可以看到，只有第一次真正查询了数据库，后续的查询使用了一级缓存。
     */
    @Test
    public void test_3(){
        SqlSession sqlSession = sqlSessionFactory.openSession(true);// 启动提交服务
        PersonMapper mapper = sqlSession.getMapper(PersonMapper.class);
        log.info(" mapper1 = {}", mapper.selectById(1));
        log.info(" mapper2 = {}", mapper.selectById(1));
        log.info(" mapper3 = {}", mapper.selectById(1));
    }

    /**
     * 增加了对数据库的修改操作，验证在一次数据库会话中，如果对数据库发生了修改操作，一级缓存是否会失效。
     * 结果：在修改操作后执行的相同查询，查询了数据库，一级缓存失效。
     */
    @Test
    public void test_4(){
        SqlSession sqlSession = sqlSessionFactory.openSession(true);// 启动提交服务
        PersonMapper mapper = sqlSession.getMapper(PersonMapper.class);
        log.info(" person = {}", mapper.selectById(1));
        mapper.insertSelective(new Person(null,"张三",new Date(),"上海",new Date()));
        log.info(" person = {}", mapper.selectById(1));
    }

    @Test
    public void test_1(){
        List<Person> personList = personService.queryPersonList();
        personList.forEach(person -> {
            log.info(" person = {}", person);
        });
    }
}
