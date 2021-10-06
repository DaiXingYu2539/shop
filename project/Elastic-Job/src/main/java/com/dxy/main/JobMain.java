package com.dxy.main;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.dxy.server.FileBackupJob;

import java.io.IOException;

public class JobMain {

    // zookeeper端口
    private static final int ZOOKEEPER_PORT = 2181;
    // zookeeper 链接字符串 localhost：2181
    private static final String ZOOKEEPER_CONNECTION_STRING = "localhost:" + ZOOKEEPER_PORT;
    // 定时任务命名空间
    private static final String JOB_NAMESPACE = "elastic-job-example-java";

    // 启动任务
    public static void main(String[] args) throws InterruptedException, IOException {

        // 制造一些测试数据

        // 配置注册中心
        CoordinatorRegistryCenter registryCenter = setUpRegistCenter();
        //启动任务
        startJob(registryCenter);

    }
    //创建注册中心
   private static CoordinatorRegistryCenter setUpRegistCenter(){
        //zk配置
       ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(ZOOKEEPER_CONNECTION_STRING,JOB_NAMESPACE);

       //减少zk超时时间
       zookeeperConfiguration.setSessionTimeoutMilliseconds(100);

       //创建注册中心
       ZookeeperRegistryCenter zookcenter = new ZookeeperRegistryCenter(zookeeperConfiguration);
       zookcenter.init();
        return zookcenter;
   }
    //任务的配置和启动
    private  static void startJob(CoordinatorRegistryCenter registerCenter){
    //String jobName任务名称, String cron调度表达式, int shardingTotalCount作业分片数量
        JobCoreConfiguration builder = JobCoreConfiguration.newBuilder( "files-job", "0/1 * * * * ?", 2).build();

        // 创建SimpleJobConfiguration
        SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(builder, FileBackupJob.class.getCanonicalName());
        //创建new JobScheduler
        new JobScheduler(registerCenter, LiteJobConfiguration.newBuilder(simpleJobConfiguration).overwrite(true).build()).init();

    }
}
