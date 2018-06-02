package org.ltx.hc.business.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;

/**
 * @author ltxlouis
 */
@Configuration
@EnableCaching
@Slf4j
public class CacheConfiguration extends CachingConfigurerSupport {

    @Override
    public KeyGenerator keyGenerator() {
        return (o, method, objects) -> {
            StringBuilder sb = new StringBuilder();
            sb.append("hc");
            sb.append("_");
            sb.append(o.getClass().getName());
            sb.append("_");
            sb.append(method.getName());
            for (Object obj : objects) {
                sb.append(obj.toString());
            }
            log.info("调用缓存Key : " + sb.toString());
            return sb.toString();
        };
    }
}