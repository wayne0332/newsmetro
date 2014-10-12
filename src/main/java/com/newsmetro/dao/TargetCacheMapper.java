package com.newsmetro.dao;

import com.newsmetro.po.TargetCache;
import org.apache.ibatis.annotations.Param;

/**
 * Created by finetu on 14-10-12.
 */
public interface TargetCacheMapper {
    TargetCache getTargetCacheByTargetId(@Param("targetId") Integer targetId);

}
