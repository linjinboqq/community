package com.nowcoder.community;

import com.nowcoder.community.util.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class SensitiveTests {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void testSensitiveFilter() {
        String text = "������ԶĲ�,�������,��������,���Կ�Ʊ,������!";
        text = sensitiveFilter.filter(text);
        System.out.println(text);

        text = "������ԡ�ġ��,���ԡ��Ρ�潡�,���ԡ������,���ԡ��Ʊ��,������!";
        text = sensitiveFilter.filter(text);
        System.out.println(text);
    }

}
