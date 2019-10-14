package com.example.twophasecommit.config;

import com.example.twophasecommit.listener.TestMessageListener;
import com.google.gson.Gson;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQXAConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosConnectionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;

@Configuration
@EnableConfigurationProperties(JMSConfig.MQProperties.class)
@EnableJms
public class JMSConfig {
    @Autowired
    JMSConfig.MQProperties mqProperties;
    @Autowired
    Environment environment;
    @Bean
    public JmsMessagingTemplate jmsMessagingTemplate(JmsTemplate jmsTemplate, MessageConverter messageConverter) {
        JmsMessagingTemplate jmsMessagingTemplate = new JmsMessagingTemplate(jmsTemplate);
        jmsMessagingTemplate.setJmsMessageConverter(messageConverter);
        jmsMessagingTemplate.getJmsTemplate().setSessionTransacted(true);
        return jmsMessagingTemplate;
    }
    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(atomikosConnectionFactoryBean());
        jmsTemplate.setPubSubDomain(false); //Queue
        return jmsTemplate;
    }
    @Bean
    public AtomikosConnectionFactoryBean atomikosConnectionFactoryBean(){
        AtomikosConnectionFactoryBean autoAtomikosConnectionFactoryBean=new AtomikosConnectionFactoryBean();
        autoAtomikosConnectionFactoryBean.setXaConnectionFactory(activeMQXAConnectionFactory());
        autoAtomikosConnectionFactoryBean.setLocalTransactionMode(false);
        return  autoAtomikosConnectionFactoryBean;
    }
    @Bean
    MappingJackson2MessageConverter payloadConverter() {
        MappingJackson2MessageConverter payloadConverter = new MappingJackson2MessageConverter();
        payloadConverter.setTargetType(MessageType.TEXT);
        payloadConverter.setTypeIdPropertyName("__type");
        //payloadConverter.setObjectMapper(objectMapper);
        return payloadConverter;
    }
    @Bean
    public ActiveMQXAConnectionFactory activeMQXAConnectionFactory(){
        ActiveMQXAConnectionFactory activeMQXAConnectionFactory=new ActiveMQXAConnectionFactory();
        activeMQXAConnectionFactory.setBrokerURL("tcp://localhost:61616");
        activeMQXAConnectionFactory.setUserName(ActiveMQConnectionFactory.DEFAULT_USER);
        activeMQXAConnectionFactory.setPassword(ActiveMQConnectionFactory.DEFAULT_PASSWORD);
        return activeMQXAConnectionFactory;
    }
    @Bean(initMethod = "init")
    public ConnectionFactory connectionFactory() {
        AtomikosConnectionFactoryBean atomikosConnectionFactoryBean = new AtomikosConnectionFactoryBean();
        atomikosConnectionFactoryBean.setLocalTransactionMode(false);
        Integer maxPoolSize = environment.getProperty(
                "spring.jta.atomikos.connectionfactory.max-pool-size", Integer.class, 100);
        atomikosConnectionFactoryBean.setMaxPoolSize(maxPoolSize);
        Integer minPoolSize = environment.getProperty(
                "spring.jta.atomikos.connectionfactory.min-pool-size", Integer.class, 20);
        atomikosConnectionFactoryBean.setMinPoolSize(minPoolSize);
        String uniqueResourceName = environment.getProperty(
                "spring.jta.atomikos.connectionfactory.unique-resource-name");
        atomikosConnectionFactoryBean.setUniqueResourceName(uniqueResourceName);
        atomikosConnectionFactoryBean.setXaConnectionFactory(activeMQXAConnectionFactory());
        return atomikosConnectionFactoryBean;
    }



    @Bean
    public ActiveMQQueue activeMQQueue(){
        ActiveMQQueue activeMQQueue=new ActiveMQQueue("scores");
        return activeMQQueue;
    }
    @Bean
    public Gson gson()
    {
        return new Gson();
    }
    @Bean
    public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
                                                    DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        // This provides all boot's default to this factory, including the message converter
        configurer.configure(factory, connectionFactory);
        // You could still override some of Boot's default if necessary.
        return factory;
    }



@ConfigurationProperties(prefix = "jms")
    public static class MQProperties {
        private String queueManager;
        private String queue;
        private String notificationQueue;
        private String host;
        private int port;
        private String channel;

        public String getQueueManager() {
            return queueManager;
        }

        public MQProperties setQueueManager(String queueManager) {
            this.queueManager = queueManager;
            return this;
        }

        public String getQueue() {
            return queue;
        }

        public String getNotificationQueue() {
            return notificationQueue;
        }

        public MQProperties setQueue(String queue) {
            this.queue = queue;
            return this;
        }



        public MQProperties setNotificationQueue(String notificationQueue) {
            this.notificationQueue = notificationQueue;
            return this;
        }

        public String getHost() {
            return host;
        }

        public MQProperties setHost(String host) {
            this.host = host;
            return this;
        }

        public int getPort() {
            return port;
        }

        public MQProperties setPort(int port) {
            this.port = port;
            return this;
        }

        public String getChannel() {
            return channel;
        }

        public MQProperties setChannel(String channel) {
            this.channel = channel;
            return this;
        }
    }
}
