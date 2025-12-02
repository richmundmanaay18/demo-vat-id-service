package com.example.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.util.logging.Logger;

@Configuration
@Slf4j
public class RedisLoggingConfig {

    @Autowired
    private Environment env;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        String host = env.getProperty("SPRING_DATA_REDIS_HOST");
        String port = env.getProperty("SPRING_DATA_REDIS_PORT");
        String password = env.getProperty("REDIS_PASSWORD");
        boolean ssl = Boolean.parseBoolean(env.getProperty("SPRING_DATA_REDIS_SSL_ENABLED"));

        System.out.println("🚀 Connecting to Redis: {}:{} (SSL: {})" +
                host + ":" + port + ":" + ssl);

        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(host);
        config.setPort(Integer.parseInt(port));
        config.setPassword(password);

        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(config);
        connectionFactory.setUseSsl(ssl);

        return connectionFactory;
    }
}
