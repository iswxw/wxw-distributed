package com.wxw.jvm.monitoring;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：wxw.
 * @date ： 11:41 2020/12/17
 * @description：Jconsole 监控 oom异常
 * @link:
 * @version: v_0.0.1
 */
public class JConsoleMonitorOOMDemo {

    public static void main(String[] args)throws Exception {
        fillHeap(1000);
    }

    static class OOMObject{
        public byte[] placeholder = new byte[64 * 1024];
    }

    public static void fillHeap(int num)throws InterruptedException{
        List<OOMObject> list = new ArrayList<OOMObject>();
        for(int i = 0; i < num; i++){
            //稍作延迟，令监视曲线的变化更加明显
            Thread.sleep(50);
            list.add(new OOMObject());
        }
        System.gc();
    }
}
