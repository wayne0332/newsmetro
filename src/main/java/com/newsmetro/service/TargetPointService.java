package com.newsmetro.service;

import com.newsmetro.dao.TargetPointMapper;
import com.newsmetro.enumeration.TargetStatus;
import com.newsmetro.po.TargetPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class TargetPointService {

	@Autowired
	private TargetPointMapper targetPointMapper;
	
	public void addTargetPoint(TargetPoint target){
        targetPointMapper.save(target);
	}

	public List<TargetPoint> getTargetListByUserId(int userId){

		List<TargetPoint> list = targetPointMapper.findTargetByUserIdAndStatus(userId,null);
		return list;
	}

	public List<TargetPoint> getRegularTargetListByUserId(int userId) {
        List<TargetPoint> list = targetPointMapper.findTargetByUserIdAndStatus(userId, TargetStatus.REGULAR);
        return list;
    }

    public TargetPoint getTargetById(Integer id){
        return targetPointMapper.getTargetById(id);
    }

	public void deleteTarget(Integer id){
        targetPointMapper.deleteById(id);
	}

	public void updateTarget(TargetPoint target){
        targetPointMapper.update(target);
	}

}
