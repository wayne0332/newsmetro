package com.newsmetro.service;

import com.newsmetro.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lifeng.gao on 2014/12/26.
 */
public class ScriptServiceTest extends BaseTest {

    @Autowired
    private ScriptService scriptService;

    @Test
    public void tryTargetTest(){
        scriptService.tryTarget("http://news.sina.com.cn/","//*[@id=\"syncad_1\"]");

    }
}
