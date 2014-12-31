package com.newsmetro.action;

import com.newsmetro.enumeration.TargetStatus;
import com.newsmetro.po.TargetPoint;
import com.newsmetro.po.User;
import com.newsmetro.service.TargetPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class NewsAction {
	@Autowired
	private TargetPointService service;
	
	@RequestMapping(value="/toNewsCenter.html")
	public String toNewsCenter(HttpServletRequest request){
		User user = (User)request.getSession().getAttribute("user");
		List<TargetPoint> targetList = service.getTargetListByUserId(user.getId());
		request.setAttribute("targetList", targetList);
		return "newsCenter";
	}
	
	@RequestMapping(value="/addResource.html")
	public String addResource(HttpServletRequest request,TargetPoint target){
		User user = (User)request.getSession().getAttribute("user");
		target.setUser(user);
		target.setStatus(TargetStatus.REGULAR);
        target.setUserId(user.getId());
		service.addTargetPoint(target);
		return "redirect:/toNewsCenter.html";
	}
	
	@RequestMapping(value="/manage/addResource.html")
	public String manageAddResource(HttpServletRequest request,TargetPoint target){
		User user = (User)request.getSession().getAttribute("user");
		target.setUser(user);
		service.addTargetPoint(target);
		return "redirect:/myTargetList.html";
	}
	
	@RequestMapping(value="/myTargetList.html")
	public String getTargetListByUserId(HttpServletRequest request){
		User user = (User)request.getSession().getAttribute("user");
		List<TargetPoint> targetList = service.getTargetListByUserId(user.getId());
		request.setAttribute("targetList", targetList);
		return "myTargetList";
	}
	
}
