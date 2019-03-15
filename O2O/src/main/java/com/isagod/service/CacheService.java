package com.isagod.service;

import org.springframework.stereotype.Service;

@Service
public interface CacheService {
    void removeFromCache(String keyPrefix);
}
