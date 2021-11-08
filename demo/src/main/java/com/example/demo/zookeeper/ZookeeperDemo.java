package com.example.demo.zookeeper;


import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tiger on 2018/6/30.
 */
public class ZookeeperDemo {
 
    //连接的zk服务
    private static final String CONNECTION_HOSTS = "192.168.34.128:2181,192.168.34.128:2181,192.168.34.128:2181";
    //超时连接的时间
    private static final int SESSION_TIMEOUT = 2000;
 
    private static ZooKeeper zooKeeper = null;
    static {
        try {
            zooKeeper = new ZooKeeper(CONNECTION_HOSTS, SESSION_TIMEOUT, new Watcher() {
                public void process(WatchedEvent watchedEvent) {
                    System.out.println(watchedEvent.getPath()+";"+watchedEvent.getState());
                    try {
                        //获取对根目录节点数据的监控
                        zooKeeper.getChildren("/",true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
     判断当前节点是否存在
     */
    public Stat isExistZnode(String path) throws Exception{
        Stat stat = zooKeeper.exists(path,true);
        return stat;
    }
    /*
      创建节点
     */
    @Test
    public String createZnode(String path, Object object, ArrayList<ACL> zooDefs, CreateMode createMode) throws Exception {
        return  zooKeeper.create(path,object.toString().getBytes(),zooDefs,createMode);
    }
    /*
      修改节点
     */
    @Test
    public Stat updateZnode(String path, Object object, int version) throws Exception {
        return zooKeeper.setData(path,object.toString().getBytes(),version);
    }
    /*
      删除节点
     */
    @Test
    public void deleteZnode(String path,int version) throws Exception {
        zooKeeper.delete(path,version);
    }
    @Test
    public static void main(String[] args) throws Exception{
        ZookeeperDemo zookeeperDemo = new ZookeeperDemo();
        //创建节点
        if(zookeeperDemo.isExistZnode("/Tiger") == null){
            zookeeperDemo.createZnode("/Tiger","oh,my sky", ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        }else {
            System.out.println("znode exist！！！");
        }
        //更新节点内容
        Stat stat = zookeeperDemo.isExistZnode("/Tiger");
        if(stat != null){
            System.out.println(stat.getVersion());
            zookeeperDemo.updateZnode("/Tiger","oh,my sky",stat.getVersion());
        }else{
            System.out.println("znode not exist !!!");
        }
        //删除节点
        Stat deleteStat = zookeeperDemo.isExistZnode("/Tiger1");
        if(deleteStat != null){
            zookeeperDemo.deleteZnode("/Tiger1",deleteStat.getVersion());
        }
        //获取根节点下所有子节点,遍历获取对应节点的数据
        List<String> znodes = zooKeeper.getChildren("/",true);
        for(String path : znodes){
            System.out.println(path+">>>"+new String(zooKeeper.getData("/"+path,true,new Stat())));
        }
    }
}