package com.newsmetro.action;

import com.newsmetro.po.TargetGroup;
import com.newsmetro.po.TargetPoint;
import com.newsmetro.po.User;
import com.newsmetro.pojo.Rss;
import com.newsmetro.pojo.RssItem;
import com.newsmetro.service.TargetGroupService;
import com.newsmetro.util.HttpGetter;
import com.newsmetro.util.MD5Util;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Created by finetu on 5/29/15.
 */
@Controller
public class TargetGroupAjax extends BaseAction {



}
