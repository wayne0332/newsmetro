package com.newsmetro.dao;

import com.newsmetro.enumeration.TargetStatus;
import com.newsmetro.po.TargetPoint;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lifeng.gao on 2014/10/11.
 */
public interface TargetPointMapper {
    void save(@Param("target") TargetPoint target);

    List<TargetPoint> findTargetByUserIdAndStatus(@Param("userId") Integer userId,@Param("status") TargetStatus status);

    TargetPoint getTargetById(@Param("id") Integer id);

    void deleteById(@Param("id") Integer id);

    void update(@Param("target")TargetPoint target);
}
