package com.newsmetro.service;

import com.newsmetro.constant.SystemConfig;
import com.newsmetro.util.GsonUtil;
import com.newsmetro.util.RuntimeUtil;
import com.newsmetro.util.handler.RunScriptHandler;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lifeng.gao on 2014/12/26.
 */
@Service
public class ScriptService {

    public String tryTarget(String url,String xpath){
        String command = SystemConfig.getWebSingleCrawlerPath() + " " + url + " "+xpath;
//        String items = RuntimeUtil.execute(command,new RunScriptHandler(){
//
//            @Override
//            protected void handleLine(String line, String outStr) {
//                if(line.startsWith("[")){
//                    List list = GsonUtil.fromJson(line, java.util.List.class);
//                    if(list.size()>0){
//                        super.outStr = line;
//                    }
//                }
//            }
//        });
//        return items;
        System.out.println("------------>"+command);
        return null;
    }

    public String tryRss(String url){
        String command = SystemConfig.getRssSingleCrawlerPath() + " " + url;
        String items = RuntimeUtil.execute(command,new RunScriptHandler(){

            @Override
            protected void handleLine(String line, String outStr) {
                if(line.startsWith("{")){
                    List list = GsonUtil.fromJson(line, java.util.List.class);
                    if(list.size()>0){
                        super.outStr = line;
                    }
                }
            }
        });
        return items;
    }
}
