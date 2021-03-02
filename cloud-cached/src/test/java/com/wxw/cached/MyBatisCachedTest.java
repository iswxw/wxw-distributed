package com.wxw.cached;

import com.wxw.BaseTest;
import com.wxw.domain.Engines;
import com.wxw.service.EnginesService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：wxw.
 * @date ： 16:06 2021/3/2
 * @description：
 * @link:
 * @version: v_0.0.1
 */
@Slf4j
public class MyBatisCachedTest extends BaseTest {

    @Resource
    private EnginesService enginesService;
    

    @Test
    public void test_2(){

    }


    @Test
    public void test_1(){
        List<Engines> enginesList = enginesService.queryEngineList();
        enginesList.forEach(engine -> {
            log.info(" engine = {}", engine);
        });
    }
}
