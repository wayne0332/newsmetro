package com.newsmetro.pojo;

import com.newsmetro.po.TargetGroup;
import com.newsmetro.po.TargetPoint;

import java.util.List;

/**
 * Created by finetu on 5/29/15.
 */
public class TargetGroupBean {
    private TargetGroup targetGroup;
    private List<TargetPoint> targetPointList;

    public TargetGroup getTargetGroup() {
        return targetGroup;
    }

    public void setTargetGroup(TargetGroup targetGroup) {
        this.targetGroup = targetGroup;
    }

    public List<TargetPoint> getTargetPointList() {
        return targetPointList;
    }

    public void setTargetPointList(List<TargetPoint> targetPointList) {
        this.targetPointList = targetPointList;
    }
}
