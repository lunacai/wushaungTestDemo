package com.example.testhttpdemo.controller;

import com.example.clientdemo.userMedol;
import com.example.testhttpdemo.HttpURLConnectionFactory;
import com.example.testhttpdemo.KfProducer;
import com.example.testhttpdemo.toDB.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class DemoController {

    @Autowired
    private KfProducer kafkaproducer;

    @Value("${kafka_topic}")
    private String kafkaTopic;

    @Value(value = "${kafka_ip}")
    private String KAFKA_IP;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private HttpURLConnectionFactory jdkhttp = new HttpURLConnectionFactory();
    public static dbcp2DataSource dbcpDb2 = new dbcp2DataSource();
    public static dbcpDataSource dbcpDb = new dbcpDataSource();
    public static HikariCPDataSourceMysql hikariDb = new HikariCPDataSourceMysql();
    private OjdbcConnOracle connOracle = new OjdbcConnOracle();
    public druidDataSource dataSource=new druidDataSource();

    @GetMapping("/sendToKafka")
    public String sendToKafka(String username) {
        System.out.println("kafka>>>>>>>>" + username);
        kafkaproducer.sendSyn(KAFKA_IP, kafkaTopic, username);
        userMedol user=new userMedol();
        user.setUserid("uid");
        user.setUsername(username);
        user.setContent("content");
        dbcpDb.install(user);
        redisTemplate.opsForValue().set(username.substring(0,username.length()-1),username);
        return "Send to Kafka successfully";
    }

//    @PostMapping("/addOjdbc")
//    public String addOjdbc(String username){
//        int num = username.length();
//        UserModel use = new UserModel();
//        use.setID(num);
//        use.setUSERID(username);
//        use.setLOGINPASSWORD("123456");
//        use.setISDISABLE(1);
//        if (connOracle.selectById(num)) {
//            connOracle.updateUser(use);
//        } else {
//            connOracle.addUser(use);
//        }
//
//        return "successful";
//    }

    @GetMapping("/jdkhttp/get")
    public String jdkhttp(String url) {
        HttpURLConnection con = jdkhttp.getConn(url);
        return jdkhttp.sendGet(con);
    }

    @PostMapping("/jdkhttp/post")
    public String jdkhttpPost(@RequestParam("url") String url, @RequestParam("body") String body) {
        HttpURLConnection con = jdkhttp.getConn(url);
        return jdkhttp.sendPost(con, body);
    }

    @GetMapping("/httpDemo")
    public String sentToHttpDemo(String targetUrl) {
        // 1.建立HttpClient对象
        CloseableHttpClient client = HttpClients.createDefault();
        // 2.建立Get请求
        HttpGet get = new HttpGet(targetUrl);
        try {
            // 3.发送Get请求
            CloseableHttpResponse res = client.execute(get);
            // 4.处理请求结果
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                ContentType contentType = ContentType.getOrDefault(entity);
                Charset charset = contentType.getCharset();
                // 获取字节数组
                byte[] content = new byte[0];
                content = EntityUtils.toByteArray(entity);
                if (charset == null) {
                    // 默认编码转成字符串
                    String temp = new String(content);
                    String regEx = "(?=<meta).*?(?<=charset=[\\'|\\\"]?)([[a-z]|[A-Z]|[0-9]|-]*)";
                    Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(temp);
                    if (m.find() && m.groupCount() == 1) {
                        charset = Charset.forName(m.group(1));
                    } else {
                        charset = Charset.forName("UTF-8");
                    }
                }
                System.out.println(new String(content, charset));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "http client success";
    }

}
