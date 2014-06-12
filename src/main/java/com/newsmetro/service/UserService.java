package com.newsmetro.service;

import com.newsmetro.enumeration.UserStatus;
import com.newsmetro.po.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {
	static Logger logger = LoggerFactory.getLogger(UserService.class.getName());
	@Autowired
	private com.newsmetro.dao.UserDao dao;
	
	public User signIn(String email,String password){
		return dao.signIn(email, password);
	}
	
	public User signUp(User user){
		Long count = (Long) dao.find("select count(*) from User u where u.email=?", new Object[]{user.getEmail()}).get(0);
		if(count==0){
			dao.save(user);
			return user;
		}else{
			return null;
		}
	}
	
	public User findUserByEmail(String email){
		List list = dao.find("from User u where u.email=?", new Object[]{email});
		if(list.size()==1){
			return (User) list.get(0);
		}else if(list.size()>0){
			logger.error("!!!!!!!!!!!!!!!!出现email重名!!!!!!!!!!!!!!!:"+email);
		}
		return null;
	}
	
	public boolean activeUser(String email,String code){
		List<User> userList = (List<User>) dao.find("from User u where u.email=? and u.code=?",new Object[]{email,code});
		if(userList.size()!=0){
			User user = userList.get(0);
			user.setStatus(UserStatus.REGULAR);
			dao.saveOrUpdate(user);
			return true;
		}else{
			if(email!=null&&!email.equals("")){
				List<User> newUserList = (List<User>) dao.find("from User u where u.email=? and u.status=?",new Object[]{email, UserStatus.NEW});
				if(newUserList.size()!=0){
					User newUser = userList.get(0);
					dao.delete(newUser);
				}
			}
			return false;
		}
	}
	
	public int clearNewUser(){
		long msPerDay = 1000*24*60*60;//一天的毫秒数   
		Date timeLine = new Date(new Date().getTime()-2*msPerDay);
		List<Integer> list = dao.find("select u.id from User u where u.status=? and u.registerDate<=?",new Object[]{UserStatus.NEW,timeLine});
		Integer[] array = new Integer[list.size()];
		for(int i=0;i<list.size();i++){
			array[i]=list.get(i);
		}
		return dao.deleteByIdArray(array);
	}
	
	public void update(User user){
		dao.update(user);
	}
}
