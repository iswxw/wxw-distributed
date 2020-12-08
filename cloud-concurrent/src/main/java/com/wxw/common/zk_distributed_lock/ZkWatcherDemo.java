package com.wxw.common.zk_distributed_lock;

import com.wxw.common.tools.MyZkSerializer;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.TimeUnit;

/**
 * @author: wxw
 * @date: 2020-11-28-23:05
 */
public class ZkWatcherDemo {
    public static void main(String[] args) {
        // 创建一个ZK客户端
        ZkClient client = new ZkClient("localhost:2181");
        client.setZkSerializer(new MyZkSerializer());
        client.subscribeDataChanges("/wxw/wxw-1", new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("=====收到节点数据变化了："+data);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println("=====收到节点删除了");
            }
        });
        try { TimeUnit.SECONDS.sleep(1000 * 60 * 2); }catch (InterruptedException e){ e.printStackTrace(); }
    }
}
