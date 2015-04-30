package com.newsmetro.service;

import com.newsmetro.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * Created by lifeng.gao on 2014/12/26.
 */
public class ScriptServiceTest extends BaseTest {

    @Autowired
    private ScriptService scriptService;

    @Test
    public void tryTargetTest(){
        String docStr = scriptService.tryTarget("http://news.sina.com.cn/", "//*[@id=\"syncad_1\"]");
        System.out.println(docStr);
    }

    @Test
    public void tryRssTest(){
        String res = scriptService.tryRss("http://jandan.net/feed");
        System.out.println(res);
    }

    @Test
    public void runtimeScriptTest(){
        try {
            Process process = Runtime.getRuntime().exec(
                    "python /home/finetu/projects/target_crawler/rss_single_crawler.py --url=http://jandan.net/feed");
            //"/home/finetu/projects/phantomjs/bin/phantomjs /home/finetu/projects/phan_scripts/hello_world.js http://news.sina.com.cn/ //*[@id=\"syncad_1\"]"
            process.waitFor();
            InputStreamReader ir = new InputStreamReader(process.getInputStream());

            LineNumberReader input = new LineNumberReader(ir);
            String line;
            while((line = input.readLine()) != null)
                System.out.println(line);
            input.close();
            ir.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
