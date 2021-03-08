package com.example.onsrocketmq;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMessageListener implements MessageListener {

    private OjdbcConnOracle connOracle = new OjdbcConnOracle();

    @Override
    public Action consume(Message message, ConsumeContext consumeContext) {
        System.out.println("Receive @" + new Date() + ": " + message);
        //此处可以写具体业务逻辑，body是具体发送的内容
        String body = new String(message.getBody());
        System.out.println("msgBody is : " + body);
        int num = body.length();
        UserModel use = new UserModel();
        use.setID(num);
        use.setUSERID(body);
        use.setLOGINPASSWORD("123456");
        use.setISDISABLE(1);
        if (connOracle.selectById(num)) {
            connOracle.updateUser(use);
        } else {
            connOracle.addUser(use);
        }


        return Action.CommitMessage;
    }
}
