package com.newsmetro.service;

import com.newsmetro.constant.RedisConstants;
import com.newsmetro.dao.TargetCacheMapper;
import com.newsmetro.dao.TargetPointDao;
import com.newsmetro.dao.TargetPointMapper;
import com.newsmetro.po.TargetCache;
import com.newsmetro.pojo.Link;
import com.newsmetro.util.GsonUtil;
import com.newsmetro.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by finetu on 14-10-12.
 */

@Service
public class TargetCacheService {
    @Autowired
    private TargetCacheMapper targetCacheMapper;

    public TargetCache getTargetCacheByTargetId(Integer targetId) {
        return targetCacheMapper.getTargetCacheByTargetId(targetId);
    }
}
