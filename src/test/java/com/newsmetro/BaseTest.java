package com.newsmetro;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lifeng.gao on 2014/12/26.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations={"/applicationContext.xml",
        "/application-redis.xml",
        "/application-mapper.xml",})
public class BaseTest {
}
