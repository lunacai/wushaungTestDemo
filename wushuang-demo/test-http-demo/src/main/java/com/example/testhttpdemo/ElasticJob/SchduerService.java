package com.example.testhttpdemo.ElasticJob;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class SchduerService implements SimpleJob {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void execute(ShardingContext shardingContext) {
        String num= String.valueOf(shardingContext.getJobName().length());
        redisTemplate.opsForValue().set(num,shardingContext.getJobName());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=df.format(System.currentTimeMillis());
        switch (shardingContext.getShardingItem()) {
            case 0:
                System.out.println("1打印的消息.....>>>"+shardingContext.getJobName()+" >>>"+time);
                break;
            case 1:
                System.out.println("2打印的消息.....>>>"+shardingContext.getJobName()+" >>>"+time);
                break;
            case 2:
                System.out.println("3打印的消息.....>>>"+shardingContext.getJobName()+" >>>"+time);
                break;
            // case n: .
        }
    }
}
