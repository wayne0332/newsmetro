package com.newsmetro.action;

import com.newsmetro.enumeration.TargetStatus;
import com.newsmetro.po.TargetCache;
import com.newsmetro.po.TargetGroup;
import com.newsmetro.po.TargetPoint;
import com.newsmetro.po.User;
import com.newsmetro.pojo.TargetGroupBean;
import com.newsmetro.service.TargetCacheService;
import com.newsmetro.service.TargetGroupService;
import com.newsmetro.service.TargetPointService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class NewsAction {
	@Autowired
	private TargetPointService targetPointService;
	@Autowired
	private TargetCacheService targetCacheService;
	@Autowired
	private TargetGroupService targetGroupService;

	@RequestMapping(value="/toNewsCenter.html")
	public String toNewsCenter(HttpServletRequest request){
		User user = (User)request.getSession().getAttribute("user");
		List<TargetPoint> targetList = targetPointService.getTargetListByUserId(user.getId());
		request.setAttribute("targetList", targetList);
		return "newsCenter";
	}
	
	@RequestMapping(value="/addResource.html")
	public String addResource(HttpServletRequest request,TargetPoint target){
		User user = (User)request.getSession().getAttribute("user");
		target.setUser(user);
		target.setStatus(TargetStatus.REGULAR);
        target.setUserId(user.getId());
		targetPointService.addTargetPoint(target);

		String cacheStr = request.getParameter("targetCache");
		if(StringUtils.isNotBlank(cacheStr)) {
			TargetCache targetCache = new TargetCache();
			targetCache.setTargetId(target.getId());
			targetCache.setItems(cacheStr);
			targetCache.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			targetCacheService.saveTargetCache(targetCache);
		}

		return "redirect:/toNewsCenter.html";
	}
	
	@RequestMapping(value="/manage/addResource.html")
	public String manageAddResource(HttpServletRequest request,TargetPoint target){
		User user = (User)request.getSession().getAttribute("user");
		target.setUser(user);
		targetPointService.addTargetPoint(target);

		String cacheStr = request.getParameter("targetCache");
		if(StringUtils.isNotBlank(cacheStr)) {
			TargetCache targetCache = new TargetCache();
			targetCache.setTargetId(target.getId());
			targetCache.setItems(cacheStr);
			targetCache.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			targetCacheService.saveTargetCache(targetCache);
		}
		return "redirect:/myTargetList.html";
	}
	
	@RequestMapping(value="/myTargetList.html")
	public String getTargetListByUserId(HttpServletRequest request){
		User user = (User)request.getSession().getAttribute("user");
		List<TargetPoint> targetList = targetPointService.getTargetListByUserId(user.getId());

		List<TargetGroup> targetGroupList = new ArrayList<TargetGroup>();
		targetGroupList = targetGroupService.findByUserId(user.getId());
		List<TargetGroupBean> beanList = new ArrayList<TargetGroupBean>();
		for (TargetGroup tg : targetGroupList) {
			TargetGroupBean bean = new TargetGroupBean();
			bean.setTargetGroup(tg);
			List<TargetPoint> tList = targetGroupService.findTargetByGroupId(tg.getId());
			if(tList==null){
				tList = new ArrayList<TargetPoint>();
			}
			bean.setTargetPointList(tList);
			beanList.add(bean);
		}

		TargetGroupBean noGroupBean = findNoGroupedTargetGroupBean(targetList,beanList);
		beanList.add(0,noGroupBean);

		request.setAttribute("targetGroupBeanList", beanList);
		request.setAttribute("targetGroupList", targetGroupList);
		return "myTargetList";
	}

	private TargetGroupBean findNoGroupedTargetGroupBean(
			List<TargetPoint> targetPointList,List<TargetGroupBean> targetGroupBeanList){
		Set<Long> idSet = new HashSet<Long>();
		for(TargetGroupBean b:targetGroupBeanList){
			if(b.getTargetPointList()==null){
				continue;
			}
			for(TargetPoint p:b.getTargetPointList()){
				idSet.add(p.getId());
			}
		}
		List<TargetPoint> resList = new ArrayList<TargetPoint>();
		for(TargetPoint t:targetPointList){
			if(!idSet.contains(t.getId())) {
				resList.add(t);
			}
		}
		TargetGroupBean resBean = new TargetGroupBean();
		TargetGroup tg = new TargetGroup();
		tg.setId(0l);
		tg.setName("未分组");
		tg.setPosition(0);
		tg.setStatus(1);
		resBean.setTargetGroup(tg);
		resBean.setTargetPointList(resList);
		return resBean;
	}
	
}
