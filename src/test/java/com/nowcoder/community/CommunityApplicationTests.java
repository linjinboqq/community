package com.nowcoder.community;

import com.nowcoder.community.dao.AlphaDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
//@ContextConfiguration(classes = CommunityApplication.class)
public class CommunityApplicationTests  implements ApplicationContextAware {
ApplicationContext applicationContext;
@Autowired
SimpleDateFormat simpleDateFormat;
    @Test
    public void contextLoads() {
        System.out.println(applicationContext);
        Object aldao = applicationContext.getBean(AlphaDao.class);
        System.out.println(aldao);
        System.out.println(simpleDateFormat.format(new Date()));

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
