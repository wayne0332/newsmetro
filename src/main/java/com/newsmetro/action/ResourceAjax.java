package com.newsmetro.action;

import com.newsmetro.enumeration.TargetStatus;
import com.newsmetro.po.TargetPoint;
import com.newsmetro.po.User;
import com.newsmetro.pojo.Link;
import com.newsmetro.pojo.Rss;
import com.newsmetro.pojo.RssItem;
import com.newsmetro.service.TargetPointService;
import com.newsmetro.service.UserService;
import com.newsmetro.util.DESUtil;
import com.newsmetro.util.HttpGetter;
import com.newsmetro.util.MD5Util;
import com.newsmetro.util.XpathUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.client.ClientProtocolException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.htmlcleaner.XPatherException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;


@Controller
@Scope("prototype")
public class ResourceAjax {
	static Logger logger = LoggerFactory.getLogger(ResourceAjax.class.getName());
	static String key = "37d5aed075525d4fa0fe635231cba447";
	@Autowired
	private TargetPointService tpService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/getResource.html",params = "isRss=1")
	public void getRss(HttpServletRequest request,HttpServletResponse response,TargetPoint target) {
		
		HttpGetter getter = new HttpGetter();
		Document doc = null;
		try {
			String docStr = getter.getDocument(target.getUrl());
			String md5 = MD5Util.md5(docStr, MD5Util._32_BIT);
			target.setMd5(md5);
			tpService.updateTarget(target);
			doc = getter.toDom4jDoc(docStr);
		} catch (ClientProtocolException e) {
			doc = null;
			e.printStackTrace();
		} catch (DocumentException e) {
			doc = null;
			e.printStackTrace();
		} catch (IOException e) {
			doc = null;
			e.printStackTrace();
		} catch (Exception e) {
			doc = null;
			e.printStackTrace();
		}
		if(doc==null){
			return;
		}else{
			Rss rss = new Rss(doc);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("title",rss.getTitle());
			jsonObject.put("link",rss.getLink());
			jsonObject.put("description",rss.getRssDescription());
			JSONArray jsonArray = new JSONArray();
			List<RssItem> itemList = rss.getItemList();
			for(int i=0;i<itemList.size();i++){
				RssItem item = itemList.get(i);
				JSONObject jItem = new JSONObject();
				jItem.put("title", item.getTitle());
				jItem.put("link", item.getLinkUrl());
				jItem.put("description", item.getDescription());
				jItem.put("pubDate", item.getPubDate());
				jsonArray.add(i,jItem);
			}
			jsonObject.put("itemList", itemList);
			
			response.setContentType("application/json;charset=UTF-8");  
	        response.setHeader("Pragma", "No-cache");  
	        response.setHeader("Cache-Control", "no-cache");  
	        response.setDateHeader("Expires", 0);
	        PrintWriter out = null;
	        try {  
	            out = response.getWriter();  
	            out.write(jsonObject.toString());  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }
		}
	}
	
	@RequestMapping(value="/getResource.html",params = "isRss=0")
	public void getWeb(HttpServletRequest request,HttpServletResponse response,TargetPoint target){
		XpathUtil xpathUtil = new XpathUtil();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title",target.getName());
		jsonObject.put("link",target.getUrl());
		jsonObject.put("description","");
		JSONArray jsonArray = new JSONArray();
		
		List<Link> linkList = null;
		try {
			linkList = xpathUtil.getLinkListByTarget(target);
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (XPatherException e1) {
			e1.printStackTrace();
		}
		for(int i=0;i<linkList.size();i++){
			Link item = linkList.get(i);
			JSONObject jItem = new JSONObject();
			jItem.put("title", item.getText());
			jItem.put("link", item.getHref());
			jItem.put("description", "");
			jItem.put("pubDate", "");
			jsonArray.add(i,jItem);
		}
		jsonObject.put("itemList", linkList);
		
		response.setContentType("application/json;charset=UTF-8");  
        response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);
        PrintWriter out = null;
        try {  
            out = response.getWriter();  
            out.write(jsonObject.toString());  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
	}
	
	@RequestMapping("/getTargetPointList")
	public void getTargetPointList(HttpServletRequest request,HttpServletResponse response){
		User user = (User) request.getSession().getAttribute("user");
		List<TargetPoint> list = tpService.getRegularTargetListByUserId(user.getId());
		JSONArray targetArray = new JSONArray();
		for(TargetPoint t:list){
			JSONObject item = new JSONObject();
			item.put("id", t.getId());
			item.put("name", t.getName());
			item.put("url", t.getUrl());
			item.put("isRss", t.getIsRss()?1:0);
			item.put("absXpath", t.getAbsXpath());
			item.put("relXpath", t.getRelXpath());
			item.put("md5", t.getMd5());
			targetArray.add(item);
		}
		response.setContentType("application/json;charset=UTF-8");  
        response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);
        PrintWriter out = null;
        try {  
            out = response.getWriter();  
            out.write(targetArray.toString());  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
	
	@RequestMapping(value="/deleteTarget.html")
	public void deleteTarget(HttpServletRequest request,HttpServletResponse response){
		boolean flag = true;
		
		Integer id = Integer.parseInt(request.getParameter("id"));
		if(id==null){
			flag = false;
		}
		
		TargetPoint target = tpService.getTargetById(id);
		if(target==null){
			flag = false;
		}
		
		User user = (User)request.getSession().getAttribute("user");
		if(target.getUser().getId()!=user.getId()){
			flag = false;
		}
		
		tpService.deleteTarget(target.getId());
		
		JSONObject res = new JSONObject();
		res.put("res", flag?true:false);
		response.setContentType("application/json;charset=UTF-8");  
        response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);
        PrintWriter out = null;
        try {  
            out = response.getWriter();  
            out.write(res.toString());  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
	
	@RequestMapping(value="/hideTarget.html")
	public void hideTarget(HttpServletRequest request,HttpServletResponse response){
		boolean flag = true;
		
		Integer id = Integer.parseInt(request.getParameter("id"));
		if(id==null){
			flag = false;
		}
		
		TargetPoint target = tpService.getTargetById(id);
		if(target==null){
			flag = false;
		}
		
		User user = (User)request.getSession().getAttribute("user");
		if(target.getUser().getId()!=user.getId()){
			flag = false;
		}
		
		target.setStatus(TargetStatus.HIDE);
		tpService.updateTarget(target);
		
		JSONObject res = new JSONObject();
		res.put("res", flag?true:false);
		response.setContentType("application/json;charset=UTF-8");  
        response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);
        PrintWriter out = null;
        try {  
            out = response.getWriter();  
            out.write(res.toString());  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
	
	@RequestMapping(value="/showUpTarget.html")
	public void showUpTarget(HttpServletRequest request,HttpServletResponse response){
		boolean flag = true;
		
		Integer id = Integer.parseInt(request.getParameter("id"));
		if(id==null){
			flag = false;
		}
		
		TargetPoint target = tpService.getTargetById(id);
		if(target==null){
			flag = false;
		}
		
		User user = (User)request.getSession().getAttribute("user");
		if(target.getUser().getId()!=user.getId()){
			flag = false;
		}
		
		target.setStatus(TargetStatus.REGULAR);
		tpService.updateTarget(target);
		
		JSONObject res = new JSONObject();
		res.put("res", flag?true:false);
		response.setContentType("application/json;charset=UTF-8");  
        response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);
        PrintWriter out = null;
        try {  
            out = response.getWriter();  
            out.write(res.toString());  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
	
	@RequestMapping(value="/addTarget.html")
	public void addTarget(HttpServletRequest request,HttpServletResponse response){
		DESUtil desUtil = new DESUtil();
		boolean flag = true;
		String url = request.getParameter("url");
		String absXpath = request.getParameter("absXpath");
		String relXpath = request.getParameter("relXpath");
		String regex = request.getParameter("regex");
		String signInKey = request.getParameter("signInKey");
		String targetName = null;
		try {
			targetName = new String(request.getParameter("targetName").getBytes("ISO-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		String decodeKey = null;;
		try {
			decodeKey = desUtil.getDecryptString(signInKey,key);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			flag = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		String array[] = null;
		String email = null;
		String password = null;
		if(decodeKey!=null){
			array = decodeKey.split("\\|\\|");
		}else{
			flag = false;
		}
		if(array.length==2){
			email = array[0];
			password = array[1];
		}else{
			flag = false;
		}
		
		//验证signInKey的有效性
		User user = userService.signIn(email, password);
		if(user==null){
			flag = false;
		}
		
		if(url==null){
			flag = false;
		}
		TargetPoint target = new TargetPoint();
		target.setUser(user);
		target.setAbsXpath(absXpath);
		target.setIsRss(false);
		target.setName(targetName);
		target.setRelXpath(relXpath);
		target.setStatus(TargetStatus.REGULAR);
		target.setUrl(url);
		tpService.addTargetPoint(target);
		System.out.println(
			"---------------------------------"+
			"\nurl:"+url+
			"\nabsXpath:"+absXpath+
			"\nrelXpath:"+relXpath+
			"\ntargetName:"+targetName+
			"\nregex:"+regex+
			"\ndecode:"+decodeKey);
		
		JSONObject res = new JSONObject();
		res.put("res", flag?true:false);
		response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        PrintWriter out = null;
        try {  
            out = response.getWriter();  
            out.write(res.toString());  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
	}
}
