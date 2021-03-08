package com.example.activemqconsumer;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

@Configuration
@EnableJms
public class ActiveMqConf {

    @Value(value = "${spring.activemq.broker-url}")
    private String activeHost;

    @Bean
    public javax.jms.Connection activeConnection() throws JMSException {
        ConnectionFactory connectionFactory = this.connectionFactory();
        return connectionFactory.createConnection();
    }

    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(activeHost);
        connectionFactory.setUserName("admin");
        connectionFactory.setPassword("admin");
        return connectionFactory;

    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsTopicListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        //true为topic，false为queue
        factory.setPubSubDomain(true);
        factory.setRecoveryInterval(1000L);
        return factory;
    }

}
