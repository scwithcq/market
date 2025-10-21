package com.fmcd.market.Config;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class MqttConfig {

    @Value("${mqtt.url}")
    private String url;

    @Value("${mqtt.username}")
    private String username;

    @Value("${mqtt.password}")
    private String password;

    @Value("${mqtt.client-id}")
    private String clientId;

    @Value("${mqtt.sub-topic}")
    private String subTopic;

    /* --------- 工厂（带连接监听） --------- */
    @Bean
    public MqttPahoClientFactory factory() {
        DefaultMqttPahoClientFactory f = new DefaultMqttPahoClientFactory();
        MqttConnectOptions opt = new MqttConnectOptions();
        opt.setServerURIs(new String[]{url});
        opt.setUserName(username);
        opt.setPassword(password.toCharArray());
        opt.setCleanSession(true);
        opt.setKeepAliveInterval(60);
        opt.setAutomaticReconnect(true);

        /* ===== 连接成功/失败 日志 ===== */
        opt.setConnectionTimeout(5);
        try (IMqttClient tmp = new MqttClient(url, clientId + "_check", new MemoryPersistence())) {
            tmp.connect(opt);
            System.out.println("✅ MQTT 连接成功 " + url);
            tmp.disconnect();
        } catch (MqttException e) {
            System.err.println("❌ MQTT 连接失败 " + url + "  " + e.getMessage());
        }

        f.setConnectionOptions(opt);
        return f;
    }

    /* --------- 订阅通道 --------- */
    @Bean
    public MessageProducer inbound(MqttPahoClientFactory factory) {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(
                        clientId + "_in", factory, subTopic);
        adapter.setCompletionTimeout(5_000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(inboundChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "inboundChannel")
    public MessageHandler handler() {
        return message -> {
            // ① 必现日志
            System.out.println(">>> 原始消息到达");
            System.out.println("    topic=" + message.getHeaders().get("mqtt_receivedTopic"));
            System.out.println("    payload=" + message.getPayload());
            // ② 原有逻辑
            System.out.println("<<< 收到： " + message.getPayload());
        };
    }

    @Bean
    public MessageChannel inboundChannel() {
        return new DirectChannel();
    }

    /* --------- 发布通道（可选） --------- */
    @Bean
    @ServiceActivator(inputChannel = "outboundChannel")
    public MessageHandler outbound(MqttPahoClientFactory factory) {
        MqttPahoMessageHandler handler =
                new MqttPahoMessageHandler(clientId + "_out", factory);
        handler.setAsync(true);
        handler.setDefaultTopic("app/ctrl");
        handler.setDefaultQos(1);
        return handler;
    }

    @Bean
    public MessageChannel outboundChannel() {
        return new DirectChannel();
    }
}