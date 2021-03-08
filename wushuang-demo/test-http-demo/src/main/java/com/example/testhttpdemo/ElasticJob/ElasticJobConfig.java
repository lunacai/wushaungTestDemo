package com.example.testhttpdemo.ElasticJob;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticJobConfig {

    @Autowired
    SchduerService schduerService;

    public static CoordinatorRegistryCenter createRegistryCenter() {

        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration("{IP}:2181", "elastic-job");
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(zookeeperConfiguration);
        regCenter.init();
        return regCenter;
    }

    public static LiteJobConfiguration createJobConfiguration() {

        JobCoreConfiguration simpleCoreConfig = JobCoreConfiguration.newBuilder("jobdemo", "0/30 * * * * ?", 3)
                .shardingItemParameters("0=A,1=A,2=B").failover(true).misfire(true).build();
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(simpleCoreConfig,
                SchduerService.class.getCanonicalName());
        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfig).overwrite(true)
                .build();
        return simpleJobRootConfig;
    }
}
