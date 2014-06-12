package com.newsmetro.service;

import com.newsmetro.enumeration.TargetStatus;
import com.newsmetro.po.TargetPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class TargetPointService {
	@Autowired
	private com.newsmetro.dao.TargetPointDao dao;
	
	public void addTargetPoint(TargetPoint target){
		dao.save(target);
	}
	
	public List<TargetPoint> getTargetListByUserId(int userId){
		
		List<TargetPoint> list = dao.find("from TargetPoint t where t.user.id=?", new Object[]{userId});
		return list;
	}
	
	public List<TargetPoint> getRegularTargetListByUserId(int userId){
		List<TargetPoint> list = dao.find("from TargetPoint t where t.user.id=? and t.status=?", new Object[]{userId, TargetStatus.REGULAR});
		return list;
	}
	public TargetPoint getTargetById(Serializable id){
		return dao.get(id);
	}
	
	public void deleteTarget(TargetPoint target){
		dao.delete(target);
	}
	
	public void persist(TargetPoint target){
		dao.persist(target);
	}
	
	public void updateTarget(TargetPoint target){
		dao.saveOrUpdate(target);
	}
	
	public void flush(){
		dao.flush();
	}
}
