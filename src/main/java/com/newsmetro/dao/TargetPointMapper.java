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

    List<TargetPoint> findTargetByUserIdAndStatus(@Param("userId") Long userId,@Param("status") TargetStatus status);

    TargetPoint getTargetById(@Param("id") Long id);

    void deleteById(@Param("id") Long id);

    void update(@Param("target")TargetPoint target);
}
