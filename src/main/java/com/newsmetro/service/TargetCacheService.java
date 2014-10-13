package com.newsmetro.service;

import com.newsmetro.dao.TargetCacheMapper;
import com.newsmetro.dao.TargetPointDao;
import com.newsmetro.po.TargetCache;
import com.newsmetro.pojo.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by finetu on 14-10-12.
 */

@Service
public class TargetCacheService {
    @Autowired
    private TargetCacheMapper targetCacheMapper;

    public List<Link> getLinkLIstByTargetId(Integer targetId){
        return null;
    }
}
