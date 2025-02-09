package com.example.demo.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisConfig {

    /**
     * Redis bağlantısını yönetmek için LettuceConnectionFactory kullanıyoruz.
     * Host ve port bilgilerini application.properties üzerinden de alabilirsiniz.
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // Lokal redis için varsayılan: host=localhost, port=6379
        return new LettuceConnectionFactory("localhost", 6379);
    }

    /**
     * RedisTemplate, Redis üzerinde veri okumak/yazmak için kullanılır.
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        // Key ve Value serileştirme ayarları yapılabilir.
        template.afterPropertiesSet();
        return template;
    }

    /**
     * Mesaj dinleyicimizi RedisMessageListenerContainer’a eklememiz gerekiyor.
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory,
                                                                       MessageListenerAdapter listenerAdapter,
                                                                       ChannelTopic topic) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // Belirlediğimiz topic kanalını ve ona ait dinleyiciyi ekliyoruz.
        container.addMessageListener(listenerAdapter, topic);
        return container;
    }

    /**
     * RedisMessageSubscriber'ı adapter olarak sarıyoruz.
     */
    @Bean
    public MessageListenerAdapter listenerAdapter(RedisSubService subscriber) {
        // handleMessage metodu çağrılacak
        return new MessageListenerAdapter(subscriber, "handleMessage");
    }

    /**
     * Publish ve Subscribe işlemleri için kullanılacak kanal.
     */
    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("testChannel");
    }
}
