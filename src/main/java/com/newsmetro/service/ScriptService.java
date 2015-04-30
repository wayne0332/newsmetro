package com.newsmetro.service;


import com.newsmetro.constant.SystemConfig;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * Created by lifeng.gao on 2014/12/26.
 */
@Service
public class ScriptService {

    public String tryTarget(String url,String xpath) {
        String res = "";
        String command = SystemConfig.getWebSingleCrawlerPath() + " " + url + " " + xpath;
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            InputStreamReader ir = new InputStreamReader(process.getInputStream());

            LineNumberReader input = new LineNumberReader(ir);
            String line;

            while((line = input.readLine()) != null){
                res += line;
            }
            input.close();
            ir.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res;
    }

    public String tryRss(String url){
        String command = SystemConfig.getRssSingleCrawlerPath() + url;
        String items = "";
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            InputStreamReader ir = new InputStreamReader(process.getInputStream());

            LineNumberReader input = new LineNumberReader(ir);
            String line;
            while((line = input.readLine()) != null)
                items += line;
            input.close();
            ir.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return items;
    }
}
