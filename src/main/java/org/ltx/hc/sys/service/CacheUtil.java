package org.ltx.hc.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * @author ltxlouis
 * @since 4/7/2018
 */
@Service
public class CacheUtil {

    private final CacheManager cacheManager;

    @Autowired
    public CacheUtil(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void set(Object key, Object value) {
        cacheManager.getCache("HcCache").put(key, value);
    }

    public Object get(Object key) {
        return cacheManager.getCache("HcCache").get(key);
    }

    public <T> T get(Object key, Class<T> type) {
        return cacheManager.getCache("HcCache").get(key, type);
    }

    public void remove(Object key) {
        cacheManager.getCache("HcCache").evict(key);
    }
}
