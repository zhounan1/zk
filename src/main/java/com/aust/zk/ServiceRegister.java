package com.aust.zk;/*
 * Copyright © 2016 睿泰集团 版权所有
 */

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;


/**
 * @Autor zhouNan
 * @Date 2019/9/19 11:31
 * @Description ServiceRegister
 **/
public class ServiceRegister {
    private  static  final String BASE_SERVICES = "/services";
    private  static  final String SERVICE_NAME = "/user";

    public static void  register(String address, int port) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
        String path = BASE_SERVICES + SERVICE_NAME;
       // ZooKeeper zooKeeper = new ZooKeeper("106.13.125.253:2181",8000,(watchedEvent)->{});
        //ZkClient zkClient = new ZkClient("106.13.125.253:2181",8000);
        ZooKeeper zk = new ZooKeeper("106.13.125.253:2181", 3000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        countDownLatch.countDown();
                    }
                    System.out.println("Watch =>" + event.getType());
                }
            });
            countDownLatch.await();

            System.out.println(zk.getState());
            Thread.sleep(5000);
            String node = "/app1";
            Stat state = zk.exists(node, false);
            if (state == null) {
                System.out.println("创建节点");
                String createResult = zk.create(node, "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                System.out.println(createResult);
            }


            byte[] b = zk.getData(node, false, state);
            System.out.println("获取data值 =》" + new String(b));
            Stat state1 = zk.exists(node, false);
            state = zk.setData(node, "2".getBytes(),state1.getVersion());
            System.out.println("after update, version changed to =>" + state.getVersion());
            //zk.delete(node,state.getVersion());
            System.out.println("节点添加成功");


            zk.close();


          /*  //boolean exists = zkClient.exists(BASE_SERVICES + SERVICE_NAME);
            if (exists) {
                zkClient.create(path,"".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        String server_path = address+ ":"+port;
            zkClient.create(path+"child",server_path.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("用户注册成功");*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
